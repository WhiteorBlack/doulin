package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.RechargeDialog
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/9/2.
 */
class WalletActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        init()
    }


    private fun init() {
        inittitle("逗邻钱包")
        StatusBarWhiteColor()

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
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_right->{//明细
                MyApplication.openActivity(this,TransactionDetailsActivity::class.java)
            }
            R.id.tv_recharge->{//充值
                RechargeDialog.communityDialog(this)
            }
            R.id.tv_forward->{//提现
                MyApplication.openActivity(this,WithdrawActivity::class.java)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RechargeDialog.builder=null
    }


}