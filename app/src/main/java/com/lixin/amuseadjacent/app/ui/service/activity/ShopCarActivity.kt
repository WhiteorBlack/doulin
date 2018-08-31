package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.service.adapter.ShopCarAdapter
import kotlinx.android.synthetic.main.activity_shop_car.*

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

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_car.layoutManager = linearLayoutManager
        shopAdapter = ShopCarAdapter(this)
        rv_car.adapter = shopAdapter

    }


}