package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_submission_order.*

/**
 * 提交订单
 * Created by Slingge on 2018/9/1
 */
class SubmissionOrderActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission_order)
        init()
    }


    private fun init() {
        inittitle("提交订单")
        StatusBarWhiteColor()

        tv_submission.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_submission -> {
                MyApplication.openActivity(this, PaymentActivity::class.java)
            }
        }
    }


}
 