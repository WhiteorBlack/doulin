package com.lixin.amuseadjacent.app.ui.entrance

import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
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
            MyApplication.openActivity(this, VerificationPasswordActivity::class.java)
            finish()
        }
        iv_back.setOnClickListener { v ->
           finish()
        }
    }


}