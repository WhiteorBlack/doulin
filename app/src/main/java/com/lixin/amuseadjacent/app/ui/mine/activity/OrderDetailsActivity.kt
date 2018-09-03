package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity

/**
 * 订单详情
 * 默认为待付款
 * Created by Slingge on 2018/9/3
 */
class OrderDetailsActivity : BaseActivity() {

    private var flag=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        init()
    }


    private fun init() {
        inittitle("订单")
        StatusBarWhiteColor()

    }


    private fun PendingPayment(){


    }


}