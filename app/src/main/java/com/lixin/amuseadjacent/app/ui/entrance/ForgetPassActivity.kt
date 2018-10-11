package com.lixin.amuseadjacent.app.ui.entrance

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.entrance.request.FindUserPassword_1415
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.SMSVerificationCode
import com.lixin.amuseadjacent.app.util.TimerUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_forgetpass.*

/**
 * Created by Slingge on 2018/8/15
 */
class ForgetPassActivity : BaseActivity(), View.OnClickListener {

    private var VCode: String? = null
    private var timerUtil: TimerUtil? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetpass)
        init()
    }


    private fun init() {
        timerUtil = TimerUtil(tv_code)

        iv_back.setOnClickListener(this)
        iv_sgin.setOnClickListener(this)
        tv_code.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_code -> {
                var phone = AbStrUtil.etTostr(et_phone)
                if (TextUtils.isEmpty(phone) || phone.length != 11) {
                    ToastUtil.showToast("请输入11位手机号")
                    return
                }

                VCode = timerUtil!!.num
                SMSVerificationCode.sendSMS(this,phone, VCode!!)
                timerUtil!!.timersStart()
            }
            R.id.iv_sgin -> {
                val phone = AbStrUtil.etTostr(et_phone)
                if (TextUtils.isEmpty(phone) || phone.length != 11) {
                    ToastUtil.showToast("请输入11位手机号")
                    return
                }

                val code = AbStrUtil.etTostr(et_verifi)
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showToast("请输入验证码")
                    return
                }
                if (VCode != code) {
                    ToastUtil.showToast("验证码错误")
                    return
                }

                val pass = AbStrUtil.etTostr(et_pass)
                if (TextUtils.isEmpty(pass)) {
                    ToastUtil.showToast("请输入密码")
                    return
                }

                FindUserPassword_1415.FindUserPassword(this, phone, pass)
            }
        }
    }


}