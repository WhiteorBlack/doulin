package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_payment_success.*

/**
 * 支付成功
 * Created by Slingge on 2018/9/1
 */
class PaymentSuccessActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()

        back.setOnClickListener(this)
        tv_orderDetails.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.back -> {
                finish()
            }
        }
    }

}