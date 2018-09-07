package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.BankCardAdapter
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * Created by Slingge on 2018/9/3
 */
class BankCardActivity : BaseActivity() {

    private var bankAdapter: BankCardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        inittitle("银行卡")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager



        bankAdapter = BankCardAdapter(this)
        xrecyclerview.adapter = bankAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        bankAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()

    }


}