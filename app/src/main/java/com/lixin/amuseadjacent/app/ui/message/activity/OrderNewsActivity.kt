package com.lixin.amuseadjacent.app.ui.message.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.message.adapter.OrderNewsAdapter
import com.lixin.amuseadjacent.app.ui.message.model.OrderlNewModel
import com.lixin.amuseadjacent.app.ui.message.request.MsgList_21
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 订单消息
 * Created by Slingge on 2018/8/16
 */
class OrderNewsActivity : BaseActivity() {

    private var orderAdapter: OrderNewsAdapter? = null
    private var orderList = ArrayList<OrderlNewModel.msgModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("订单提醒")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        xrecyclerview.layoutManager = linearLayoutManager

        orderAdapter = OrderNewsAdapter(this,orderList)
        xrecyclerview.adapter = orderAdapter

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (orderList.isNotEmpty()) {
                    orderList.clear()
                    orderAdapter!!.notifyDataSetChanged()
                }
                MsgList_21.orderNew(nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                MsgList_21.orderNew(nowPage)
            }
        })
        ProgressDialog.showDialog(this)
        MsgList_21.orderNew(nowPage)
    }


    @Subscribe
    fun onEven(model: OrderlNewModel) {
        orderList.addAll(model.dataList)
        totalPage = model.totalPage

        if (totalPage <= 1) {
            if (orderList.isEmpty()) {
                xrecyclerview.setNullData(this)
            } else {
                xrecyclerview.noMoreLoading()
            }
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        orderAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}