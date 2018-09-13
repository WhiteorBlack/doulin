package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.BankCardAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.MyBankModel
import com.lixin.amuseadjacent.app.ui.mine.request.Bank_155156157158164
import kotlinx.android.synthetic.main.activity_bank_my.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2018/9/3
 */
class BankCardActivity : BaseActivity() {

    private var bankAdapter: BankCardAdapter? = null
    private val bankList = ArrayList<MyBankModel.detailsModel>()

    private var flag=0//1选择银行卡

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_my)
        EventBus.getDefault().register(this)
        init()
    }

    override fun onStart() {
        super.onStart()
        ProgressDialog.showDialog(this)
        Bank_155156157158164.myBankList()
    }

    private fun init() {
        inittitle("银行卡")
        StatusBarWhiteColor()

        flag=intent.getIntExtra("flag",0)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_bank.layoutManager = linearLayoutManager

        bankAdapter = BankCardAdapter(this, bankList,flag)
        rv_bank.adapter = bankAdapter

        tv_bottom.setOnClickListener { v ->
            //添加
            val bundle = Bundle()
            bundle.putInt("flag", 0)
            MyApplication.openActivity(this, BankCardAddActivity::class.java, bundle)
        }
    }


    @Subscribe
    fun onEvent(model: MyBankModel) {
        bankList.clear()
        bankList.addAll(model.dataList)
        bankAdapter!!.notifyDataSetChanged()

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_bank.layoutAnimation = controller
        bankAdapter!!.notifyDataSetChanged()
        rv_bank.scheduleLayoutAnimation()
    }





    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}