package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.model.BankModel
import com.lixin.amuseadjacent.app.ui.mine.model.MyBankModel
import com.lixin.amuseadjacent.app.ui.mine.request.Bank_155156157158164
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_bankcard_add.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 添加银行卡
 * Created by Slingge on 2018/9/3
 */
class BankCardAddActivity : BaseActivity() {

    private var bankList = ArrayList<BankModel.bankModel>()
    private var spinnerItems = ArrayList<String>()
    private var spinnerAdapter: ArrayAdapter<String>? = null

    private var bankId = ""
    private var bankName = ""

    private var flag = 0//0添加，1编辑
    private var BankCardId = ""//要编辑的银行卡id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bankcard_add)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("添加银行卡")
        StatusBarWhiteColor()

        flag = intent.getIntExtra("flag", 0)

        spinnerAdapter = ArrayAdapter(this@BankCardAddActivity,
                R.layout.item_spinner_text, spinnerItems)
        sp_bank.adapter = spinnerAdapter
        sp_bank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                bankId = bankList[p2].bankId
                bankName = bankList[p2].bankName
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        tv_enter.setOnClickListener { v ->
            val name = AbStrUtil.etTostr(et_name)
            if (TextUtils.isEmpty(name)) {
                ToastUtil.showToast("请输入开户人姓名")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(bankId)) {
                ToastUtil.showToast("请选择开户银行")
                return@setOnClickListener
            }
            val num = AbStrUtil.etTostr(et_num)
            if (TextUtils.isEmpty(num)) {
                ToastUtil.showToast("请输入银行卡卡号")
                return@setOnClickListener
            }
            ProgressDialog.showDialog(this)
            Bank_155156157158164.addBank(this, flag, bankName, num, name, BankCardId)
        }

        ProgressDialog.showDialog(this)
        Bank_155156157158164.getBank()
    }

    @Subscribe
    fun onEvent(model: BankModel) {
        bankList = model.dataList
        for (i in 0 until bankList.size) {
            spinnerItems.add(bankList[i].bankName)
        }
        bankId = bankList[0].bankId
        bankName = bankList[0].bankName
        spinnerAdapter!!.notifyDataSetChanged()

        if (flag == 1) {
            val model = intent.getSerializableExtra("DynamiclDetailsModel") as MyBankModel.detailsModel
            et_name.setText(model.cardUsername)
            et_num.setText(model.cardNum)
            BankCardId = model.cardId

            /* for (i in 0 until bankList.size) {
                 if (bankId==DynamiclDetailsModel.cardId) {
                     sp_bank.setPromptId(i)
                     break
                 }
             }*/

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}

