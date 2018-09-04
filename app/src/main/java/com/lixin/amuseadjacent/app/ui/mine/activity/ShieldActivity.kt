package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.ShieldAdapter
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 我的屏蔽
 * Created by Slingge on 2018/9/4
 */
class ShieldActivity : BaseActivity() {

    private var shieldAdapter: ShieldAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        inittitle("我的屏蔽")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        shieldAdapter = ShieldAdapter(this)
        xrecyclerview.adapter = shieldAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        shieldAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }


}