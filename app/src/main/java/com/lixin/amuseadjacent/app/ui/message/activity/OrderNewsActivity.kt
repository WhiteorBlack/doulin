package com.lixin.amuseadjacent.app.ui.message.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.message.adapter.CommentNewsAdapter
import com.lixin.amuseadjacent.app.ui.message.adapter.OrderNewsAdapter
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 订单消息
 * Created by Slingge on 2018/8/16
 */
class OrderNewsActivity:BaseActivity(){

    private var orderAdapter: OrderNewsAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init(){
        inittitle("收到的消息")
        StatusBarWhiteColor()

        val linearLayoutManager= LinearLayoutManager(this)
        linearLayoutManager.orientation= LinearLayoutManager.VERTICAL

        xrecyclerview.layoutManager=linearLayoutManager

        orderAdapter= OrderNewsAdapter(this)
        xrecyclerview.adapter=orderAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        orderAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }


}