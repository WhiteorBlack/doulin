package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicReleaseActivity
import com.lixin.amuseadjacent.app.ui.service.adapter.ShopLeftAdapter
import com.lixin.amuseadjacent.app.ui.service.adapter.ShopRightAdapter
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 店铺
 * Created by Slingge on 2018/8/30
 */
class ShopActivity : BaseActivity() {

    private var shopLeftAdapter: ShopLeftAdapter? = null

    private var rightAdapter: ShopRightAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        init()
    }


    private fun init() {
        inittitle("店铺")
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE

        tv_right.visibility = View.VISIBLE
        tv_right.text = "详情"
        tv_right.setOnClickListener { v ->
//            MyApplication.openActivity(this, DynamicReleaseActivity::class.java)
        }

        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_left.layoutManager = linearLayoutManager

        shopLeftAdapter = ShopLeftAdapter(this)
        rv_left.adapter = shopLeftAdapter

        var controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_left.layoutAnimation = controller
        shopLeftAdapter!!.notifyDataSetChanged()
        rv_left.scheduleLayoutAnimation()

        rv_left.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_left) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                ToastUtil.showToast(i.toString())
                if (i < 0) {
                    return
                }
                shopLeftAdapter!!.setSelect(i)
            }
        })

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_right.layoutManager = linearLayoutManager

        rightAdapter = ShopRightAdapter(this)
        rv_right.adapter = rightAdapter

        controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_right.layoutAnimation = controller
        rightAdapter!!.notifyDataSetChanged()
        rv_right.scheduleLayoutAnimation()
    }


}