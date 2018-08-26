package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentReplyAdapter
import kotlinx.android.synthetic.main.activity_dynamic_detailsreply.*

/**
 * 动态评论回复
 * Created by Slingge on 2018/8/25.
 */
class DynamicDetailsReplyActivity : BaseActivity() {

    private var replyAdapter: DynamicCommentReplyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_detailsreply)
        init()
    }


    private fun init() {
        inittitle("条回复")
        StatusBarWhiteColor()


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_reply.layoutManager = linearLayoutManager

        val headerView = LayoutInflater.from(this).inflate(R.layout.item_dynamic_comment, null, false)//头布局
        headerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        rv_reply.addHeaderView(headerView)



        replyAdapter = DynamicCommentReplyAdapter(this)
        rv_reply.adapter = replyAdapter


        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_reply.layoutAnimation = controller
        replyAdapter!!.notifyDataSetChanged()
        rv_reply.scheduleLayoutAnimation()
    }


}