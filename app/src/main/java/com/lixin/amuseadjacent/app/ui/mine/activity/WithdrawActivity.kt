package com.lixin.amuseadjacent.app.ui.mine.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.WithdrawDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.RuleAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.MyBankModel
import com.lixin.amuseadjacent.app.ui.mine.request.Wallet_119121
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.CashierInputFilter
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_withdraw.*

/**
 * 提现
 * Created by Slingge on 2018/9/2.
 */
class WithdrawActivity : BaseActivity(), View.OnClickListener {

    private var ruleAdapter: RuleAdapter? = null

    private var cardId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw)
        init()
    }

    override fun onStart() {
        super.onStart()
        tv_balance.text = "可提现金额" + StaticUtil.balance
    }

    private fun init() {
        inittitle("提现")
        StatusBarWhiteColor()

        val linelayout = LinearLayoutManager(this)
        linelayout.orientation = LinearLayoutManager.VERTICAL

        rv_rule.layoutManager = linelayout
        ruleAdapter = RuleAdapter(this)
        rv_rule.adapter = ruleAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_rule.layoutAnimation = controller
        ruleAdapter!!.notifyDataSetChanged()
        rv_rule.scheduleLayoutAnimation()

        tv_all.setOnClickListener(this)

        val filter = arrayOf<InputFilter>(CashierInputFilter())
        et_money!!.filters = filter

        tv_recharge.setOnClickListener(this)
        tv_bank.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_recharge -> {
                if (TextUtils.isEmpty(AbStrUtil.etTostr(et_money))) {
                    ToastUtil.showToast("请输入提现金额")
                    return
                }
                val money = AbStrUtil.etTostr(et_money).toDouble()
                if (money <= 0) {
                    ToastUtil.showToast("请输入正确提金额")
                    return
                }
                if (TextUtils.isEmpty(cardId)) {
                    ToastUtil.showToast("请选择银行卡")
                    return
                }
                Wallet_119121.withdraw(cardId, money.toString(), object : Wallet_119121.WithdrawCallBack {
                    override fun withdraw() {
                        WithdrawDialog.communityDialog(this@WithdrawActivity)
                    }
                })

            }
            R.id.tv_all -> {
                et_money.setText(StaticUtil.balance)
            }
            R.id.tv_bank -> {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivityForResult(this, BankCardActivity::class.java, bundle, 0)
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
            val num = model.cardNum
            tv_bank.text = model.cardName + "（" + num.substring(num.length - 4, num.length) + "）"
            cardId = model.cardId
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WithdrawDialog.dismiss()
    }

}