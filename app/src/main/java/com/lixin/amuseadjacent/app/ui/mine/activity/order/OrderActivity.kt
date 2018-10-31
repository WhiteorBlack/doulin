package com.lixin.amuseadjacent.app.ui.mine.activity.order

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.OrderAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.MyOrderModel
import com.lixin.amuseadjacent.app.ui.mine.request.MyOrder_144155
import com.lixin.amuseadjacent.app.util.StaticUtil
import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 收藏
 * Created by Slingge on 2018/9/3
 */
class OrderActivity : BaseActivity() {

    private var orderAdapter: OrderAdapter? = null
    private var orderList = ArrayList<MyOrderModel.dataModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        EventBus.getDefault().register(this)
        init()
    }


    @Subscribe
    fun onEvent(model: MyOrderModel) {
        orderList.addAll(model.dataList)

        totalPage = model.totalPage

        if (totalPage <= 1) {
            if (orderList.isEmpty()) {
                xrecyclerview.setNullData(this)
            }
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }

        if (nowPage == 1) {
            val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
            xrecyclerview.layoutAnimation = controller
            orderAdapter!!.notifyDataSetChanged()
            xrecyclerview.scheduleLayoutAnimation()
        } else {
            orderAdapter!!.notifyDataSetChanged()
        }
    }


    private fun init() {
        inittitle("订单")
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        orderAdapter = OrderAdapter(this, orderList)
        xrecyclerview.adapter = orderAdapter

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                if (orderList.isNotEmpty()) {
                    orderList.clear()
                    orderAdapter!!.notifyDataSetChanged()
                }
                onRefresh = 1
                MyOrder_144155.MyOrderList(nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage > totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                MyOrder_144155.MyOrderList(nowPage)
            }
        })

        ProgressDialog.showDialog(this)
        MyOrder_144155.MyOrderList(nowPage)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == StaticUtil.RefreshOrder) {
            val oldNum: String = data.getStringExtra("old")
            val newNum: String = data.getStringExtra("new")
            if (!orderList.isEmpty()) {
                for (i in 0 until orderList.size) {
                    if (TextUtils.equals(orderList[i].orderNum, oldNum)) {
                        orderList[i].orderNum = newNum
                        orderAdapter!!.notifyItemChanged(i+1)
                        break
                    }
                }
            }
        }
        val position = data.getIntExtra("position", -1)
        if (position == -1) {
            return
        }
        if (requestCode == StaticUtil.RefundResult) {//退款成功
            orderList[position].orderState = "8"
            orderAdapter!!.notifyDataSetChanged()
        } else if (requestCode == StaticUtil.RefundResult) {//评价成功
            orderList[position].orderState = "11"
            orderAdapter!!.notifyDataSetChanged()
        } else if (requestCode == StaticUtil.OrderDetailsResult) { //订单详情中的操作
            if (data.getStringExtra("type") == "del") {
                orderList.removeAt(position)
                orderAdapter!!.notifyDataSetChanged()
            }
        } else {
            if (orderList.isNotEmpty()) {
                orderList.clear()
                orderAdapter!!.notifyDataSetChanged()
            }
            onRefresh = 1
            MyOrder_144155.MyOrderList(nowPage)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}