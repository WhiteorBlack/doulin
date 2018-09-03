package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/9/1
 */
class PaymentActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        init()
    }


    private fun init() {
        inittitle("支付")
        include.setBackgroundColor(resources.getColor(R.color.colorTheme))
        tv_title.setTextColor(resources.getColor(R.color.white))
        iv_back.setImageResource(R.drawable.ic_back_w)
        iv_back.setBackgroundColor(resources.getColor(R.color.colorTheme))
        line.visibility = View.GONE


        tv_pay.setOnClickListener { v ->
            MyApplication.openActivity(this, PaymentSuccessActivity::class.java)
        }
    }


}