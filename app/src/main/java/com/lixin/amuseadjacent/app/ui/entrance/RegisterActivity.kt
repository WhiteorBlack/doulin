package com.lixin.amuseadjacent.app.ui.entrance

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.entrance.request.Register_11
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.SMSVerificationCode
import com.lixin.amuseadjacent.app.util.TimerUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_register.*

/**
 * 注册
 * Created by Slingge on 2018/8/15
 */
class RegisterActivity : BaseActivity(), View.OnClickListener {

    private var VCode: String? = null
    private var timerUtil: TimerUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        timerUtil = TimerUtil(tv_code)
        iv_sgin.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        tv_code.setOnClickListener(this)
        tv_agreement.setOnClickListener(this)
        tv_clause.setOnClickListener(this)

        if (intent != null) {
            et_phone.setText(intent.getStringExtra("phone"))
        }
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_agreement -> {//用户协议
                val bundle = Bundle()
                bundle.putInt("flag", 3)
                MyApplication.openActivity(this, WebViewActivity::class.java, bundle)
            }
            R.id.tv_clause -> {//隐私协议
                val bundle = Bundle()
                bundle.putInt("flag", 4)
                MyApplication.openActivity(this, WebViewActivity::class.java, bundle)
            }
            R.id.tv_code -> {
                var phone = AbStrUtil.etTostr(et_phone)
                if (TextUtils.isEmpty(phone) || phone.length != 11) {
                    ToastUtil.showToast("请输入11位手机号")
                    return
                }

                VCode = timerUtil!!.num
                SMSVerificationCode.sendSMS( this,phone, VCode!!)
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

                if (code != VCode) {
                    ToastUtil.showToast("验证码错误")
                    return
                }

                val pass1 = AbStrUtil.etTostr(et_pass)
                if (pass1.length < 6 || TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast("请输入6位以上密码")
                    return
                }
                val invitation = AbStrUtil.etTostr(et_invitation)


                Register_11.register(this, phone, pass1, invitation)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}