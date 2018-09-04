package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.RuleAdapter
import kotlinx.android.synthetic.main.activity_nviting_friends.*

/**
 * 邀请好友
 * Created by Slingge on 2018/9/4
 */
class InvitingFriendsAcivity : BaseActivity() {

    private var ruleAdapter: RuleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nviting_friends)
        init()
    }


    private fun init() {
        inittitle("邀请好友")
        StatusBarWhiteColor()

        val linelayout = LinearLayoutManager(this)
        linelayout.orientation = LinearLayoutManager.VERTICAL

        rv_rule.layoutManager = linelayout
        ruleAdapter = RuleAdapter(this)
        rv_rule.adapter = ruleAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_rule.layoutAnimation = controller
        ruleAdapter!!.notifyDataSetChanged()
        rv_rule.scheduleLayoutAnimation()
    }


}