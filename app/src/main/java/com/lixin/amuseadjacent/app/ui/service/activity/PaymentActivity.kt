package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.beecloud.BCPay
import cn.beecloud.BeeCloud
import cn.beecloud.async.BCCallback
import cn.beecloud.async.BCResult
import cn.beecloud.entity.BCPayResult
import cn.beecloud.entity.BCReqParams
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.service.request.BalancePay_154
import com.lixin.amuseadjacent.app.util.DecimalUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/9/1
 */
class PaymentActivity : BaseActivity(), View.OnClickListener {

    private var payType = ""
    private var toastMsg = ""

    private var oderNum = ""
    private var balance = ""
    private var payMoney = ""//支付金额


    //支付结果返回入口
    internal var bcCallback: BCCallback = BCCallback { bcResult ->
        val bcPayResult = bcResult as BCPayResult
        //此处关闭loading界面
        //根据你自己的需求处理支付结果
        val result = bcPayResult.result
        /*
           注意！
           所有支付渠道建议以服务端的状态金额为准，此处返回的RESULT_SUCCESS仅仅代表手机端支付成功
         */
        val msg = mHandler.obtainMessage()
        //单纯的显示支付结果
        msg.what = 2
        if (result == BCPayResult.RESULT_SUCCESS) {
            msg.what = 1
            toastMsg = "用户支付成功"
        } else if (result == BCPayResult.RESULT_CANCEL) {
            toastMsg = "用户取消支付"
        } else if (result == BCPayResult.RESULT_FAIL) {
            if (bcPayResult.errCode == -12 && payType == "alipay") {
                toastMsg = "您尚未安装支付宝"
            } else {
                toastMsg = "支付失败, 原因: " + bcPayResult.errCode +
                        " # " + bcPayResult.errMsg +
                        " # " + bcPayResult.detailInfo
            }
            /*
              * 你发布的项目中不应该出现如下错误，此处由于支付宝政策原因，
              * 不再提供支付宝支付的测试功能，所以给出提示说明
              */
            if (bcPayResult.errMsg == "PAY_FACTOR_NOT_SET" && bcPayResult.detailInfo.startsWith("支付宝参数")) {
                toastMsg = "支付失败：由于支付宝政策原因，故不再提供支付宝支付的测试功能，给您带来的不便，敬请谅解"
            }

            /*
              * 以下是正常流程，请按需处理失败信息
              */
            Log.e("error", toastMsg)

        } else if (result == BCPayResult.RESULT_UNKNOWN) {
            //可能出现在支付宝8000返回状态
            toastMsg = "订单状态未知"
        } else {
            toastMsg = "invalid return"
        }

        mHandler.sendMessage(msg)
    }

    // Defines a Handler object that's attached to the UI thread.
    // 通过Handler.Callback()可消除内存泄漏警告
    private val mHandler = Handler(Handler.Callback { msg ->
        ProgressDialog.dissDialog()
        Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show()
        when (msg.what) {
            1 -> {
                val bundle=Bundle()
                bundle.putString("orderNum",oderNum)
                MyApplication.openActivity(this@PaymentActivity, PaymentSuccessActivity::class.java,bundle)
                finish()
            }
        }
        true
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        init()

        BeeCloud.setAppIdAndSecret(StaticUtil.Beecloud_Appid,
                StaticUtil.Beecloud_AppSecret)
        // 如果用到微信支付，在用到微信支付的Activity的onCreate函数里调用以下函数.
        // 第二个参数需要换成你自己的微信AppID.
        val initInfo = BCPay.initWechatPay(this, StaticUtil.Weixin_Appid)
        if (initInfo != null) {
//            ToastUtil.showToast("微信初始化失败")
        }

    }


    private fun init() {
        inittitle("支付")

        oderNum = intent.getStringExtra("oderNum")
        balance = intent.getStringExtra("balance")
        payMoney = intent.getStringExtra("payMoney")

        tv_money.text = payMoney
        tv_balance.text = balance

        include.setBackgroundColor(resources.getColor(R.color.colorTheme))
        tv_title.setTextColor(resources.getColor(R.color.white))
        iv_back.setImageResource(R.drawable.ic_back_w)
        iv_back.setBackgroundColor(resources.getColor(R.color.colorTheme))
        line.visibility = View.GONE

        cb_alipay.setOnClickListener(this)
        cb_weixin.setOnClickListener(this)
        cb_balance.setOnClickListener(this)

        tv_pay.setOnClickListener { v ->
            if (TextUtils.isEmpty(payType)) {
                ToastUtil.showToast("请选择支付方式")
                return@setOnClickListener
            }

            if (payType == "balance") {
                if (payMoney.toDouble() > balance.toDouble()) {
                    ToastUtil.showToast("余额不足")
                    return@setOnClickListener
                }
                ProgressDialog.showDialog(this)
                BalancePay_154.pay(oderNum, payMoney, object : BalancePay_154.BalancePayCallBack {
                    override fun pay() {
                        val bundle=Bundle()
                        bundle.putString("orderNum",oderNum)
                        MyApplication.openActivity(this@PaymentActivity, PaymentSuccessActivity::class.java,bundle)
                        finish()
                    }
                })
            }

            ProgressDialog.showDialog(this)
            if (payType == "alipay") {
                val aliParam = BCPay.PayParams()
                aliParam.channelType = BCReqParams.BCChannelTypes.ALI_APP
                aliParam.billTitle = "支付宝支付"
                aliParam.billTotalFee = DecimalUtil.ceilInt(payMoney.toDouble()* 100) //订单金额(分)
                aliParam.billNum = oderNum
                BCPay.getInstance(this).reqPaymentAsync(
                        aliParam, bcCallback)
            }
            if (payType == "weixin") {
                //对于微信支付, 手机内存太小会有OutOfResourcesException造成的卡顿, 以致无法完成支付
                //这个是微信自身存在的问题
                if (BCPay.isWXAppInstalledAndSupported() && BCPay.isWXPaySupported()) {
                    val payParams = BCPay.PayParams()
                    payParams.channelType = BCReqParams.BCChannelTypes.WX_APP
                    payParams.billTitle = "微信支付"   //订单标题
                    payParams.billTotalFee = DecimalUtil.ceilInt(payMoney.toDouble()* 100)    //订单金额(分)
                    payParams.billNum = oderNum  //订单流水号
                    BCPay.getInstance(this).reqPaymentAsync(
                            payParams,
                            bcCallback)            //支付完成后回调入口
                } else {
                    Toast.makeText(this,
                            "您尚未安装微信或者安装的微信版本不支持", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.cb_alipay -> {
                cb_weixin.isChecked = false
                cb_balance.isChecked = false

                payType = "alipay"
            }
            R.id.cb_weixin -> {
                cb_alipay.isChecked = false
                cb_balance.isChecked = false
                payType = "weixin"
            }
            R.id.cb_balance -> {
                cb_alipay.isChecked = false
                cb_weixin.isChecked = false
                payType = "balance"
            }
        }
    }
}