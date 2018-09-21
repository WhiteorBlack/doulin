package com.lixin.amuseadjacent.app.ui.entrance

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.entrance.request.SginIn_1213
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.SMSVerificationCode
import com.lixin.amuseadjacent.app.util.TimerUtil
import com.lxkj.linxintechnologylibrary.app.util.ProgressDialogUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_passverification_sgin.*

/**
 * 验证码、密码登录
 * Created by Slingge on 2018/8/15
 */
class VerificationPasswordActivity : BaseActivity(), View.OnClickListener {

    private var flag = 0//默认0验证码登录

    private var VCode: String? = null
    private var timerUtil: TimerUtil? = null

    private var phone = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passverification_sgin)
        init()
    }


    private fun init() {
        if (intent != null) {
            phone = intent.getStringExtra("phone")
            tv_phone.setText(phone)
        }

        tv_verification.setOnClickListener(this)
        tv_pass.setOnClickListener(this)
        tv_forgetpass.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        iv_sgin.setOnClickListener(this)

        tv_code.setOnClickListener(this)
        timerUtil = TimerUtil(tv_code)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_verification -> {//验证码登录
                if (flag == 0) {
                    return
                }
                flag = 0
                verification()
            }
            R.id.tv_pass -> {//密码登录
                if (flag == 1) {
                    return
                }
                flag = 1
                pass()
            }
            R.id.tv_forgetpass -> {//忘记密码
                MyApplication.openActivity(this, ForgetPassActivity::class.java)
            }
            R.id.tv_code -> {//获取验证码
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast("请输入手机号")
                    return
                }

                VCode = timerUtil!!.num
                ProgressDialog.showDialog(this)
                SMSVerificationCode.sendSMS(this, phone, VCode!!)
                timerUtil!!.timersStart()
            }
            R.id.iv_back -> {
                finish()
            }
            R.id.iv_sgin -> {
                if (flag == 0) {
                    val code = AbStrUtil.etTostr(et_verifi)
                    if (TextUtils.isEmpty(code)) {
                        ToastUtil.showToast("请输入验证码")
                        return
                    }
                    if (code != VCode) {
                        ToastUtil.showToast("验证码错误")
                        return
                    }
                    SginIn_1213.smsSgin(this, phone)
                } else {//密码登录
                    val pass = AbStrUtil.etTostr(et_pass)
                    if (TextUtils.isEmpty(pass)) {
                        ToastUtil.showToast("请输入密码")
                        return
                    }
                    ProgressDialog.showDialog(this)
                    SginIn_1213.passSgin(this, phone, pass)
                }
            }
        }
    }


    private fun verification() {
        et_verifi.visibility = View.VISIBLE
        tv_code.visibility = View.VISIBLE
        view_verifi.visibility = View.VISIBLE
        et_verifi.visibility = View.VISIBLE
        tv_verification.setTextColor(resources.getColor(R.color.white))
        view_left.visibility = View.VISIBLE

        tv_pass.setTextColor(Color.parseColor("#a0ffffff"))
        view_right.visibility = View.INVISIBLE
        et_pass.visibility = View.GONE
        tv_forgetpass.visibility = View.GONE
    }

    private fun pass() {
        et_verifi.visibility = View.GONE
        tv_code.visibility = View.GONE
        view_verifi.visibility = View.GONE
        et_verifi.visibility = View.GONE
        tv_verification.setTextColor(Color.parseColor("#a0ffffff"))
        view_left.visibility = View.INVISIBLE

        tv_pass.setTextColor(resources.getColor(R.color.white))
        view_right.visibility = View.VISIBLE
        et_pass.visibility = View.VISIBLE
        tv_forgetpass.visibility = View.VISIBLE
    }


}