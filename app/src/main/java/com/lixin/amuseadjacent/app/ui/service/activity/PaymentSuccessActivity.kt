package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 支付成功
 * Created by Slingge on 2018/9/1
 */
class PaymentSuccessActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)
        init()
    }


    private fun init() {
      StatusBarWhiteColor()
    }


}