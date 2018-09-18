package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.text.TextUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.request.Event_221222223224
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_event_sginup.*

/**
 * Created by Slingge on 2018/8/26.
 */
class EventSginUpActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_sginup)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("活动报名")

        tv_enter.setOnClickListener { v ->
            val name = AbStrUtil.etTostr(et_name)
            if (TextUtils.isEmpty(name)) {
                ToastUtil.showToast("请输入姓名")
                return@setOnClickListener
            }
            val phone = AbStrUtil.etTostr(et_phone)
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast("请输入手机号")
                return@setOnClickListener
            }

            val num = AbStrUtil.etTostr(et_num)
            if (TextUtils.isEmpty(num)) {
                ToastUtil.showToast("请输入报名人数")
                return@setOnClickListener
            }

            ProgressDialog.showDialog(this)
            Event_221222223224.EventSgin(this, intent.getStringExtra("id"), name
                    , phone, num)
        }

    }

}