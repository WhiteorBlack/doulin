package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.EffectAdapter
import com.lixin.amuseadjacent.app.ui.mine.adapter.EffectDetailsAdapter
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 影响力
 * Created by Slingge on 2018/9/2.
 */
class EffectDetailsActivity : BaseActivity() {

    private var effectAdapter: EffectDetailsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        inittitle("影响力")
        StatusBarWhiteColor()

        val linelayout = LinearLayoutManager(this)
        linelayout.orientation = LinearLayoutManager.VERTICAL

        xrecyclerview.layoutManager = linelayout
        effectAdapter = EffectDetailsAdapter(this)
        xrecyclerview.adapter = effectAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        effectAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }

}