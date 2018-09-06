package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.entrance.request.FindUserPassword_1415
import com.lixin.amuseadjacent.app.util.*
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_change_pass.*

/**
 * 更改密码
 * Created by Slingge on 2018/9/4
 */
class ChangePassActivity : BaseActivity(), View.OnClickListener {

    private var VCode: String? = null
    private var timerUtil: TimerUtil? = null

    private var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        init()
    }


    private fun init() {
        inittitle("更改密码")
        StatusBarWhiteColor()

        timerUtil = TimerUtil(tv_send)
        phone = SharedPreferencesUtil.getSharePreStr(this, SharedPreferencesUtil.Phone)
        tv_phone.text = "+86  " + phone.substring(0, 7) + "****"

        tv_send.setOnClickListener(this)
        tv_enter.setOnClickListener(this)

    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_send -> {
                VCode = timerUtil!!.num
                SMSVerificationCode.sendSMS(this, phone, VCode!!)
                timerUtil!!.timersStart()
            }
            R.id.tv_enter -> {
                val vode = AbStrUtil.etTostr(et_verification)
                if (TextUtils.isEmpty(vode)) {
                    ToastUtil.showToast("请输入验证码")
                    return
                }
                if (vode != VCode) {
                    ToastUtil.showToast("验证码错误")
                    return
                }

                val oldPass = AbStrUtil.etTostr(et_pass1)
                if (TextUtils.isEmpty(oldPass)) {
                    ToastUtil.showToast("请输入原密码")
                    return
                }
                val newPass = AbStrUtil.etTostr(et_pass2)
                if (TextUtils.isEmpty(newPass)) {
                    ToastUtil.showToast("请输入新密码")
                    return
                }
                FindUserPassword_1415.ModifyPassword(this, oldPass, newPass)
            }
        }
    }


}