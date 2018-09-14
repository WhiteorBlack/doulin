package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.service.request.BalancePay_154
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/9/1
 */
class PaymentActivity : BaseActivity(), View.OnClickListener {

    private var payType=""

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
            if(TextUtils.isEmpty(payType)){
                ToastUtil.showToast("请选择支付方式")
                return@setOnClickListener
            }

            if(payType=="balance"){
                BalancePay_154.pay("","",object :BalancePay_154.BalancePayCallBack{
                    override fun pay() {
                        MyApplication.openActivity(this@PaymentActivity, PaymentSuccessActivity::class.java)
                    }
                })
            }


        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.cb_alipay->{
                cb_weixin.isChecked=false
                cb_balance.isChecked=false

                payType="alipay"
            }
            R.id.cb_weixin->{
                cb_alipay.isChecked=false
                cb_balance.isChecked=false
                payType="weixin"
            }
            R.id.cb_balance->{
                cb_alipay.isChecked=false
                cb_weixin.isChecked=false
                payType="balance"
            }
        }
    }
}