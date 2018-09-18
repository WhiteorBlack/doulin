package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.dialog.ShopCartDialog
import com.lixin.amuseadjacent.app.ui.service.adapter.ShopLeftAdapter
import com.lixin.amuseadjacent.app.ui.service.adapter.ShopRightAdapter
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsListModel
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.ui.service.request.ShopGoodsList_35
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 店铺
 * Created by Slingge on 2018/8/30
 */
class OfficialShopActivity : BaseActivity(), View.OnClickListener, ShopRightAdapter.AddShopCar
        , ShopCartDialog.PlusCallBack, ShopCartDialog.ReduceCallBack, ShopCartDialog.DelCallBack {

    private var shopLeftAdapter: ShopLeftAdapter? = null
    private var leftList = ArrayList<ShopGoodsListModel.dataModel>()

    private var rightAdapter: ShopRightAdapter? = null
    private var rightList = ArrayList<ShopGoodsModel.dataModel>()
    private var carList = ArrayList<ShopGoodsModel.dataModel>()

    private var linearLayoutManager2: LinearLayoutManager? = null

    private var search = ""//搜索内容

    private var shopCartDialog: ShopCartDialog? = null//小购物车

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        this.window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("店铺")
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE

        tv_right.visibility = View.VISIBLE
        tv_right.text = "详情"
        tv_right.setOnClickListener(this)

        tv_settlement.setOnClickListener(this)
        iv_car.setOnClickListener(this)
        tv_msgNum.setOnClickListener(this)

        var linearLayoutManager1 = LinearLayoutManager(this)
        linearLayoutManager1.orientation = LinearLayoutManager.VERTICAL
        rv_left.layoutManager = linearLayoutManager1

        shopLeftAdapter = ShopLeftAdapter(this, leftList)
        rv_left.adapter = shopLeftAdapter

        rv_left.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_left) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                if (i < 0) {
                    return
                }
                title = leftList[i].firstCategoryName
//                smoothMoveToPosition(linearLayoutManager2!!, i)
                shopLeftAdapter!!.setSelect(i)
            }
        })

        linearLayoutManager2 = LinearLayoutManager(this)
        linearLayoutManager2!!.orientation = LinearLayoutManager.VERTICAL
        rv_right.layoutManager = linearLayoutManager2

        shopCartDialog = ShopCartDialog(this, this, this)

        ProgressDialog.showDialog(this)
        ShopGoodsList_35.shop("0")
    }


    //添加到小购物车
    override fun addCar(position: Int) {
        if (rightList[position].isSelect) {
            return
        }
        rightList[position].isSelect = true
        rightList[position].goodsNum = 1
        rightAdapter!!.notifyItemChanged(position)
        carList.add(rightList[position])

        carNum()
    }

    //从购物车中增加
    override fun plus(position: Int) {
        super.plus(position)
        rightList[position].goodsNum++
        rightAdapter!!.notifyItemChanged(position)
        carList[position].goodsNum++

        carNum()
    }

    //从购物车中减少
    override fun reduce(position: Int) {
        super.reduce(position)
        rightList[position].goodsNum--
        rightAdapter!!.notifyItemChanged(position)
        carList[position].goodsNum--

        carNum()
    }

    //从购物车中删除
    override fun del(position: Int) {
        super.del(position)
        rightList[position].isSelect = false
        carList.removeAt(position)

        if(carList.isEmpty()){
            shopCartDialog!!.dismiss()
        }

        carNum()
    }

    //购物车数量
    private fun carNum(){
        var num = 0
        for (i in 0 until carList.size) {
            num += carList[i].goodsNum
        }
        MyApplication.setRedNum(tv_msgNum, num)
    }




    private var title = ""
    @Subscribe
    fun onEvent(model: ShopGoodsListModel) {
        leftList.addAll(model.dataList)
        var controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_left.layoutAnimation = controller
        shopLeftAdapter!!.notifyDataSetChanged()
        rv_left.scheduleLayoutAnimation()
        if (leftList.isNotEmpty()) {
            title = leftList[0].firstCategoryName
            ProgressDialog.showDialog(this)
            ShopGoodsList_35.ShopGoods("0", leftList[0].firstCategoryId, search)
        }

    }

    @Subscribe
    fun onEvent(model: ShopGoodsModel) {
        rightList = model.dataList
        rightAdapter = ShopRightAdapter(this, title, model.dataList, this)
        rv_right.adapter = rightAdapter
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_car, R.id.tv_msgNum -> {
                if (carList.isEmpty()) {
                    ToastUtil.showToast("购物车还是空的")
                    return
                }
                shopCartDialog!!.shopCar(this, carList)
            }
            R.id.tv_right -> {
                MyApplication.openActivity(this, OfficialShopDetailsActivity::class.java)
            }
            R.id.tv_settlement -> {
                MyApplication.openActivity(this, SubmissionOrderActivity::class.java)
            }
        }
    }


    //平滑滚动到指定位置
    private fun smoothMoveToPosition(mLinearLayoutManager: LinearLayoutManager, n: Int) {

        val firstItem = mLinearLayoutManager.findFirstVisibleItemPosition()
        val lastItem = mLinearLayoutManager.findLastVisibleItemPosition()
        if (n <= firstItem) {
            rv_right.smoothScrollToPosition(n)
        } else if (n <= lastItem) {
            val top = rv_right.getChildAt(n - firstItem).top
            rv_right.smoothScrollBy(0, top)
        } else {
            mLinearLayoutManager.scrollToPositionWithOffset(n, 0)
            mLinearLayoutManager.stackFromEnd = true
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}