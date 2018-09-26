package com.lixin.amuseadjacent.app.ui.dialog

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputFilter
import android.text.TextUtils
import android.view.LayoutInflater
import com.lixin.amuseadjacent.R
import android.view.Gravity
import android.view.WindowManager
import android.widget.*
import cn.beecloud.BCPay
import cn.beecloud.async.BCCallback
import cn.beecloud.entity.BCReqParams
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.mine.activity.BankCardActivity
import com.lixin.amuseadjacent.app.ui.mine.model.MyBankModel
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.CashierInputFilter
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_withdraw.*


/**
 * 充值
 * Created by Slingge on 2018/8/15
 */
object RechargeDialog {

    var builder: AlertDialog? = null

    private var money = ""
    private var CardId = ""
    private var type = ""

    private var listener: ToPayOnClickListener? = null

    fun communityDialog(context: Activity, model: MyBankModel.detailsModel?, bcCallback: BCCallback) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_recharge, null)
        if (builder == null) {
            builder = AlertDialog.Builder(context, R.style.Dialog).create() // 先得到构造器
        }
        builder!!.show()
        builder!!.window.setContentView(view)

        val et_money = view.findViewById<EditText>(R.id.et_money)
        val filter = arrayOf<InputFilter>(CashierInputFilter())
        et_money!!.filters = filter

        val radio = view.findViewById<RadioGroup>(R.id.radio)
        radio.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb_weixin -> {
                    ToastUtil.showToast("微信支付")
                    type = "weixin"
                }
                R.id.rb_alipay-> {
                    ToastUtil.showToast("支付宝支付")
                    type = "ailpay"
                }
                R.id.rb_bank-> {
                    ToastUtil.showToast("银行卡支付")
                    type = "bank"
                }
            }

        }

        val tv_bank = view.findViewById<TextView>(R.id.tv_bank)
        if (model != null) {
            val num = model.cardNum
            tv_bank.text = model.cardName + "（" + num.substring(num.length - 4, num.length) + "）"
            CardId = model.cardId

            et_money.setText(money)
        }

        val tv_cancel = view.findViewById<TextView>(R.id.tv_cancel)
        tv_cancel.setOnClickListener { v ->
            builder!!.dismiss()
        }

        tv_bank.setOnClickListener { v ->
            money = AbStrUtil.etTostr(et_money)
            val bundle = Bundle()
            bundle.putInt("flag", 1)
            MyApplication.openActivityForResult(context, BankCardActivity::class.java, bundle, 0)
        }

        val tv_enter = view.findViewById<TextView>(R.id.tv_enter)
        tv_enter.setOnClickListener { v ->
            if (TextUtils.isEmpty(AbStrUtil.etTostr(et_money))) {
                ToastUtil.showToast("请输入充值金额")
                return@setOnClickListener
            }
            val money = AbStrUtil.etTostr(et_money).toDouble()
            if (money <= 0) {
                ToastUtil.showToast("请输入正确的提现金额")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(type)) {
                ToastUtil.showToast("请选择支付方式")
                return@setOnClickListener
            }

            if (type == "bank") {
                if (TextUtils.isEmpty(CardId)) {
                    ToastUtil.showToast("请选择银行卡")
                    return@setOnClickListener
                }
            }

            if (type == "weixin"){
                //对于微信支付, 手机内存太小会有OutOfResourcesException造成的卡顿, 以致无法完成支付
                //这个是微信自身存在的问题
                if (BCPay.isWXAppInstalledAndSupported() && BCPay.isWXPaySupported()) {
                    val payParams = BCPay.PayParams()
                    payParams.channelType = BCReqParams.BCChannelTypes.WX_APP
                    payParams.billTitle = "支付"   //订单标题
                    payParams.billTotalFee = 1    //订单金额(分)
                    payParams.billNum = "20125481515644"  //订单流水号
                    BCPay.getInstance(context).reqPaymentAsync(
                            payParams,
                            bcCallback)            //支付完成后回调入口
                } else {
                    ToastUtil.showToast("您尚未安装微信或者安装的微信版本不支持")
                }
            }

            if (type == "ailpay"){
                val aliParam = BCPay.PayParams()
                aliParam.channelType = BCReqParams.BCChannelTypes.ALI_APP
                aliParam.billTitle = "支付"
                aliParam.billTotalFee = 1  //订单金额(分)
                aliParam.billNum = "20125481515644"
                BCPay.getInstance(context).reqPaymentAsync(
                        aliParam, bcCallback)
            }



        }


        val dialogWindow = builder!!.window
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.925).toInt() // 宽度设置为屏幕宽
        dialogWindow.attributes = p

        builder!!.window.clearFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }

    interface ToPayOnClickListener {
        fun onPayClick(money: String, type: String, CardId: String)
    }

    fun setToPayOnClickListener(listener: ToPayOnClickListener) {
        this.listener = listener
    }

    fun dismiss() {
        if (builder != null) {
            builder!!.dismiss()
            builder = null
        }
    }

}