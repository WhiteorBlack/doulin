package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.service.adapter.ShopCarAdapter
import kotlinx.android.synthetic.main.activity_shop_car.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 购物车
 * Created by Slingge on 2018/8/31
 */
class ShopCarActivity : BaseActivity() {

    private var shopAdapter: ShopCarAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_car)
        init()
    }

    private fun init() {
        StatusBarWhiteColor()
        inittitle("购物车")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "编辑"
        tv_right.setOnClickListener { v ->
            if (tv_right.text == "编辑") {
                shopAdapter!!.setEdite(true)
                tv_right.text = "完成"
                tv_del.visibility = View.VISIBLE
            } else {
                tv_right.text = "编辑"
                shopAdapter!!.setEdite(false)
                tv_del.visibility = View.GONE
            }
        }
        tv_del.setOnClickListener { v ->
            //删除

        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_car.layoutManager = linearLayoutManager
        shopAdapter = ShopCarAdapter(this)
        rv_car.adapter = shopAdapter

    }


}