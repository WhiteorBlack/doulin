package com.lixin.amuseadjacent.app.ui.mine.activity

import android.content.Intent
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
    private val bankList= ArrayList<MyBankModel.detailsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_my)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("银行卡")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_bank.layoutManager = linearLayoutManager

        bankAdapter = BankCardAdapter(this,bankList)
        rv_bank.adapter = bankAdapter

        ProgressDialog.showDialog(this)
        Bank_155156157158164.myBankList()

        tv_bottom.setOnClickListener { v ->
            //添加
            val bundle = Bundle()
            bundle.putInt("flag", 0)
            MyApplication.openActivityForResult(this, BankCardAddActivity::class.java, bundle, 0)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }

        val model = data.getSerializableExtra("model") as MyBankModel.detailsModel
        bankList.add(0,model)
        bankAdapter!!.notifyItemInserted(0)
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}