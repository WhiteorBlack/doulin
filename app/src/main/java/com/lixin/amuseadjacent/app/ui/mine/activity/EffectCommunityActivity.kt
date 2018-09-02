package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.EffectAdapter
import kotlinx.android.synthetic.main.activity_community_effect.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 社区影响力
 * Created by Slingge on 2018/9/2.
 */
class EffectCommunityActivity : BaseActivity() {

    private var effectAdapter: EffectAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_effect)
        init()
    }


    private fun init() {
        inittitle("社区影响力")
        StatusBarWhiteColor()

        tv_right.visibility = View.VISIBLE
        tv_right.text = "影响力规则"
        tv_right.setOnClickListener { v ->
            MyApplication.openActivity(this, EffectDetailsActivity::class.java)
        }

        val linelayout = LinearLayoutManager(this)
        linelayout.orientation = LinearLayoutManager.VERTICAL

        rv_effect.layoutManager = linelayout
        effectAdapter = EffectAdapter(this)
        rv_effect.adapter = effectAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_effect.layoutAnimation = controller
        effectAdapter!!.notifyDataSetChanged()
        rv_effect.scheduleLayoutAnimation()
    }


}