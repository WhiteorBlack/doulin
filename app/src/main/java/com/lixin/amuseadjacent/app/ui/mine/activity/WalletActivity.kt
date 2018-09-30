package com.lixin.amuseadjacent.app.ui.mine.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.beecloud.BCPay
import cn.beecloud.BeeCloud
import cn.beecloud.async.BCCallback
import cn.beecloud.entity.BCPayResult
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.RechargeDialog
import com.lixin.amuseadjacent.app.ui.mine.model.MyBankModel
import com.lixin.amuseadjacent.app.ui.mine.request.UserInfo_19
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2018/9/2.
 */
class WalletActivity : BaseActivity(), View.OnClickListener {

    private var toastMsg = ""

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
            toastMsg = "支付成功"
            UserInfo_19.userInfo(this)

        } else if (result == BCPayResult.RESULT_CANCEL) {
            toastMsg = "取消支付"
        } else if (result == BCPayResult.RESULT_FAIL) {
            toastMsg = "支付失败, 原因: " + bcPayResult.errCode +
                    " # " + bcPayResult.errMsg +
                    " # " + bcPayResult.detailInfo
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
        Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show()
        RechargeDialog.dismiss()
        when (msg.what) {
            1 -> {
                UserInfo_19.userInfo(this)
            }
        }
        true
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        EventBus.getDefault().register(this)
        BeeCloud.setAppIdAndSecret(StaticUtil.Beecloud_Appid,
                StaticUtil.Beecloud_AppSecret)
        // 如果用到微信支付，在用到微信支付的Activity的onCreate函数里调用以下函数.
        // 第二个参数需要换成你自己的微信AppID.
        val initInfo = BCPay.initWechatPay(this, StaticUtil.Weixin_Appid)
        if (initInfo != null) {
//            ToastUtil.showToast("微信初始化失败")
        }

        init()
    }


    private fun init() {
        inittitle("逗邻钱包")
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorTheme))
        }

        tv_right.visibility = View.VISIBLE
        tv_right.text = "交易明细"
        tv_right.setOnClickListener(this)
        tv_right.setBackgroundColor(resources.getColor(R.color.colorTheme))
        tv_right.setTextColor(resources.getColor(R.color.white))

        include.setBackgroundColor(resources.getColor(R.color.colorTheme))
        line.visibility = View.GONE

        tv_title.setTextColor(resources.getColor(R.color.white))
        iv_back.setImageResource(R.drawable.ic_back_w)
        iv_back.setBackgroundColor(resources.getColor(R.color.colorTheme))


        tv_recharge.setOnClickListener(this)
        tv_forward.setOnClickListener(this)
        tv_bankcard.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        tv_balance.text = StaticUtil.balance
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_right -> {//明细
                MyApplication.openActivity(this, TransactionDetailsActivity::class.java)
            }
            R.id.tv_forward -> {//提现
                MyApplication.openActivity(this, WithdrawActivity::class.java)
            }
            R.id.tv_bankcard -> {//银行卡
                MyApplication.openActivity(this, BankCardActivity::class.java)
            }
            R.id.tv_recharge -> {//充值
                RechargeDialog.communityDialog(this, null, bcCallback)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (resultCode == 0) {
            val model = data.getSerializableExtra("DynamiclDetailsModel") as MyBankModel.detailsModel
            RechargeDialog.communityDialog(this, model, bcCallback)
        }
    }

    @Subscribe
    fun onEvent(balance: String) {
        tv_balance.text = balance
    }


    override fun onDestroy() {
        super.onDestroy()
        RechargeDialog.dismiss()
        EventBus.getDefault().unregister(this)
    }


}