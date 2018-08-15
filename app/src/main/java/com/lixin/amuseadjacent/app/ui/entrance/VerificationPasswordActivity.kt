package com.lixin.amuseadjacent.app.ui.entrance

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.MainActivity
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.AppManager
import kotlinx.android.synthetic.main.activity_passverification_sgin.*

/**
 * 验证码、密码登录
 * Created by Slingge on 2018/8/15
 */
class VerificationPasswordActivity : BaseActivity(), View.OnClickListener {

    private var flag = 0//默认0验证码登录

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passverification_sgin)
        init()
    }


    private fun init() {
        tv_verification.setOnClickListener(this)
        tv_pass.setOnClickListener(this)
        tv_forgetpass.setOnClickListener(this)
        tv_register.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        iv_sgin.setOnClickListener(this)
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
            R.id.tv_register -> {//注册
                MyApplication.openActivity(this, RegisterActivity::class.java)
            }
            R.id.iv_back -> {
                finish()
            }
            R.id.iv_sgin -> {
                MyApplication.openActivity(this, MainActivity::class.java)
                AppManager.finishAllActivity()
            }
        }
    }


    private fun verification() {
        et_verifi.visibility = View.VISIBLE
        tv_code.visibility = View.VISIBLE
        view_verifi.visibility = View.VISIBLE
        et_verifi.visibility = View.VISIBLE
        tv_verification.setTextColor(resources.getColor(R.color.white))
        view_left.setBackgroundColor(resources.getColor(R.color.white))

        tv_pass.setTextColor(resources.getColor(R.color.colorItemBackground))
        view_right.setBackgroundColor(resources.getColor(R.color.colorItemBackground))
        et_pass.visibility = View.GONE
        tv_forgetpass.visibility = View.GONE
    }

    private fun pass() {
        et_verifi.visibility = View.GONE
        tv_code.visibility = View.GONE
        view_verifi.visibility = View.GONE
        et_verifi.visibility = View.GONE
        tv_verification.setTextColor(resources.getColor(R.color.colorItemBackground))
        view_left.setBackgroundColor(resources.getColor(R.color.colorItemBackground))

        tv_pass.setTextColor(resources.getColor(R.color.white))
        view_right.setBackgroundColor(resources.getColor(R.color.white))
        et_pass.visibility = View.VISIBLE
        tv_forgetpass.visibility = View.VISIBLE
    }


}