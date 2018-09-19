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
import com.lixin.amuseadjacent.app.ui.find.activity.TalentAuthenticationActivity
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.ui.service.adapter.LaundryMenuAdapter
import com.lixin.amuseadjacent.app.ui.service.fragment.LaundryFrament
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsListModel
import com.lixin.amuseadjacent.app.ui.service.request.OfficialShopGoodsList_35
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.youth.banner.BannerConfig
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
class LaundryActivity : BaseActivity(), TabLayout.OnTabSelectedListener {


    private var menuAdapter: LaundryMenuAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    private var firstList = ArrayList<ShopGoodsListModel.dataModel>()//一级分类

    private var menuList = ArrayList<ShopGoodsListModel.secondModel>()//二级分类

    private var bannerList = ArrayList<ShopGoodsListModel.bannerModel>()

    private var type = "1"//1 洗衣洗鞋，2超市便利

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

        tab.addOnTabSelectedListener(this)

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager!!.orientation = LinearLayoutManager.HORIZONTAL

        rv_menu.layoutManager = linearLayoutManager
        menuAdapter = LaundryMenuAdapter(this, menuList)
        rv_menu.adapter = menuAdapter

        rv_menu.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_menu) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                ToastUtil.showToast(i.toString())
                if (i < 0 || i >= menuList.size) {
                    return
                }
                menuAdapter!!.setSelect(i)
                EventBus.getDefault().post(menuList[i].secondCategoryId)
            }
        })

        ProgressDialog.showDialog(this)
        OfficialShopGoodsList_35.shop(type)
//        MyApplication.setRedNum(tv_msgNum, 21)
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

            val fragment = LaundryFrament()
            list.add(fragment)
        }
        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)

        menuList.clear()
        menuList.addAll(firstList[0].secondList)
        menuAdapter!!.notifyDataSetChanged()
        menuAdapter!!.setSelect(0)
        EventBus.getDefault().post(menuList[0].secondCategoryId)
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


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}