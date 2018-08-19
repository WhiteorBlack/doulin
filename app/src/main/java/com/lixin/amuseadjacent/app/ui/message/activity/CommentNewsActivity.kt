package com.lixin.amuseadjacent.app.ui.message.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.message.adapter.CommentNewsAdapter
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 评论消息
 * Created by Slingge on 2018/8/16
 */
class CommentNewsActivity:BaseActivity(){

    private var commAdapter: CommentNewsAdapter?=null

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

        commAdapter= CommentNewsAdapter(this)
        xrecyclerview.adapter=commAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        commAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }


}