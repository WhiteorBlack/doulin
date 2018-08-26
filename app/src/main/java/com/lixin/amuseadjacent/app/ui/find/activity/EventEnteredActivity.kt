package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.EnteredAdapter
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 报名列表
 * Created by Slingge on 2018/8/25.
 */
class EventEnteredActivity : BaseActivity() {

    private var enterAdapter: EnteredAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("报名列表")

        enterAdapter = EnteredAdapter(this)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        enterAdapter = EnteredAdapter(this)
        xrecyclerview.adapter = enterAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        enterAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()

    }


}
 