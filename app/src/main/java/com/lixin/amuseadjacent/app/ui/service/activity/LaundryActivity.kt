package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.dialog.ShopCartDialog
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.ui.service.adapter.LaundryMenuAdapter
import com.lixin.amuseadjacent.app.ui.service.fragment.LaundryFrament
import com.lixin.amuseadjacent.app.ui.service.model.CarModel
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsListModel
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.ui.service.request.OfficialShopGoodsList_35
import com.lixin.amuseadjacent.app.util.DoubleCalculationUtil
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_laundry.*
import kotlinx.android.synthetic.main.include_banner.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 洗衣洗鞋
 * Created by Slingge on 2018/8/31
 */
class LaundryActivity : BaseActivity(), TabLayout.OnTabSelectedListener, View.OnClickListener
        , ShopCartDialog.PlusCallBack, ShopCartDialog.ReduceCallBack, ShopCartDialog.DelCallBack
        , ShopCartDialog.SettlementCallBack {

    private var menuAdapter: LaundryMenuAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var fragment: LaundryFrament? = null

    private var firstList = ArrayList<ShopGoodsListModel.dataModel>()//一级分类
    private var menuList = ArrayList<ShopGoodsListModel.secondModel>()//二级分类
    private var bannerList = ArrayList<ShopGoodsListModel.bannerModel>()

    private var type = "1"//1 洗衣洗鞋，2超市便利

    private var carList = ArrayList<ShopGoodsModel.dataModel>()//小购物车商品
    private var shopCartDialog: ShopCartDialog? = null//小购物车

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laundry)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE

        inittitle("洗衣洗鞋")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "详情"
        tv_right.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putInt("flag", 1)
            MyApplication.openActivity(this, OfficialShopDetailsActivity::class.java, bundle)
        }

        banner.setOnBannerListener { i ->
            val bundle = Bundle()
            bundle.putString("title", "")
            bundle.putString("url", bannerList[i].topImgDetailUrl)
            MyApplication.openActivity(this, WebViewActivity::class.java, bundle)
        }

        tv_settlement0.setOnClickListener(this)
        iv_car.setOnClickListener(this)
        tab.addOnTabSelectedListener(this)

        shopCartDialog = ShopCartDialog(this, this, this, this)

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager!!.orientation = LinearLayoutManager.HORIZONTAL

        rv_menu.layoutManager = linearLayoutManager
        menuAdapter = LaundryMenuAdapter(this, menuList)
        rv_menu.adapter = menuAdapter

        rv_menu.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_menu) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                if (i < 0 || i >= menuList.size) {
                    return
                }
                menuAdapter!!.setSelect(i)
                EventBus.getDefault().post(menuList[i].secondCategoryId)
            }
        })

        ProgressDialog.showDialog(this)
        OfficialShopGoodsList_35.shop(type)
    }

    @Subscribe
    fun onEvent(model: ShopGoodsListModel) {
        if (bannerList.isNotEmpty()) {
            return
        }
        val imageList = ArrayList<String>()
        bannerList = model.bannerList
        for (i in 0 until bannerList.size) {
            imageList.add(bannerList[i].topImgUrl)
        }
        banner.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()

        firstList.clear()
        firstList = model.dataList
        val tabList = ArrayList<String>()

        val list = ArrayList<Fragment>()
        for (i in 0 until firstList.size) {
            tabList.add(firstList[i].firstCategoryName)
            fragment = LaundryFrament()
            list.add(fragment!!)
        }
        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit=list.size

        menuList.clear()
        menuList.addAll(firstList[0].secondList)
        menuAdapter!!.notifyDataSetChanged()
        menuAdapter!!.setSelect(0)
        EventBus.getDefault().post(menuList[0].secondCategoryId)
    }


    private var carNum = 0
    //添加到小购物车的商品数量
    @Subscribe
    fun onEvent(model: CarModel) {
        MyApplication.setRedNum(tv_msgNum0, model.carNum)
        tv_money0.text = "合计：￥ ${model.totalModey}"
        carNum = model.carNum
        if (model.carNum == 0) {
            shopCartDialog!!.dismiss()
        }
    }

    @Subscribe
    fun onEvent(model: CarModel.editModel) {
        if (model.flag == 0) {//添加
            carList.add(model.goodModel)
        } else {
            for (i in 0 until carList.size) {
                if (carList[i].goodsId == model.goodModel.goodsId) {
                    carList.removeAt(i)
                    break
                }
            }
        }
        carNum()
    }

    //从购物车中增加
    override fun plus(position: Int, num: Int, money: Double) {
        super.plus(position, num, money)
        carList[position].goodsNum = num
        carList[position].money = money
        carNum()
        fragment!!.plusCar(position, num, money)
    }

    //从购物车中减少
    override fun reduce(position: Int, num: Int, money: Double) {
        super.reduce(position, num, money)
        carList[position].goodsNum = num
        carList[position].money = money
        carNum()
        fragment!!.reduceCar(position, num, money)
    }

    //从购物车中删除
    override fun del(position: Int) {
        super.del(position)
        fragment!!.delCar(position)
    }

    private var totalMoney = 0.0
    //购物车数量
    private fun carNum() {
        var num = 0
        totalMoney = 0.0
        for (i in 0 until carList.size) {
            num += carList[i].goodsNum
            totalMoney = DoubleCalculationUtil.add(carList[i].money, totalMoney)
        }
        val model = CarModel()
        model.carNum = num
        model.totalModey = totalMoney
        EventBus.getDefault().post(model)
    }


    //结算
    override fun Settlement() {
        super.Settlement()
        GoSettlement()
    }

    private fun GoSettlement() {
        if (carList.isEmpty()) {
            ToastUtil.showToast("购物车还是空的")
            return
        }
        val bundle = Bundle()
        bundle.putString("type", "1")//洗衣洗鞋
        bundle.putSerializable("list", carList)
        MyApplication.openActivity(this, SubmissionOrderActivity::class.java, bundle)
    }


    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        menuList.clear()
        menuList.addAll(firstList[tab!!.position].secondList)
        menuAdapter!!.notifyDataSetChanged()
        menuAdapter!!.setSelect(0)
        EventBus.getDefault().post(menuList[0].secondCategoryId)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_car -> {
                if (carNum == 0) {
                    ToastUtil.showToast("购物车还是空的")
                    return
                }

                shopCartDialog!!.shopCar(this, carList)
            }
            R.id.tv_settlement0 -> {
                GoSettlement()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        shopCartDialog!!.destroy()
        EventBus.getDefault().unregister(this)
    }
}