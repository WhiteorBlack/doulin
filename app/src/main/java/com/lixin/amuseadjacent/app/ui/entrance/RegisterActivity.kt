package com.lixin.amuseadjacent.app.ui.entrance

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*

/**
 * 注册
 * Created by Slingge on 2018/8/15
 */
class RegisterActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        iv_sgin.setOnClickListener(this)
        iv_back.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_back -> {
              finish()
            }
            R.id.iv_sgin -> {
                MyApplication.openActivity(this, PersonalImageActivity::class.java)
            }

        }
    }


}