package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_bankcard_add.*

/**
 * 添加银行卡
 * Created by Slingge on 2018/9/3
 */
class BankCardAddActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bankcard_add)
        init()
    }


    private fun init() {
        inittitle("添加银行卡")
        StatusBarWhiteColor()

        val spinnerItems = arrayOf("交通银行", "工商银行", "建设银行","农业银行")
        val spinnerAdapter = ArrayAdapter<String>(this,
                R.layout.item_spinner_text, spinnerItems)

        sp_bank.adapter = spinnerAdapter

        sp_bank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                ToastUtil.showToast(p2.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    };


}

