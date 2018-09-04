package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.AddressAdapter
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 我的收货地址
 * Created by Slingge on 2018/9/4
 */
class AddressActivity : BaseActivity() {

    private var addressAdapter: AddressAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        inittitle("我的收货地址")
        StatusBarWhiteColor()

        tv_bottom2.visibility = View.VISIBLE
        tv_bottom2.setOnClickListener { v ->
            //新增收货地址
            val bundle = Bundle()
            bundle.putInt("flag", 0)
            MyApplication.openActivity(this, EditAddressActivity::class.java, bundle)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        addressAdapter = AddressAdapter(this)
        xrecyclerview.adapter = addressAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        addressAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()

    }

}