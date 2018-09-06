package com.lixin.amuseadjacent.app.ui.entrance

import android.os.Bundle
import android.text.TextUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.entrance.request.PhoneVerification_10
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_sginin2.*

/**
 * 登录
 * Created by Slingge on 2018/8/13
 */
class SginInActivity2 : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sginin2)
        init()
    }

    private fun init() {
        iv_sgin.setOnClickListener { v ->
            val phone = AbStrUtil.etTostr(et_phone)
            if (TextUtils.isEmpty(phone) || phone.length != 11) {
                ToastUtil.showToast("请输入11位手机号")
                return@setOnClickListener
            }
            PhoneVerification_10.verification(this, phone)
        }
        iv_back.setOnClickListener { v ->
            finish()
        }
    }


}