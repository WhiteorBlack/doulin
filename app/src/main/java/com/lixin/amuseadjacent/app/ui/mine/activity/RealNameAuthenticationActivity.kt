package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity

/**
 * Created by Slingge on 2018/9/3
 */
class RealNameAuthenticationActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realname_authentication)
        init()
    }


    private fun init() {
        inittitle("实名认证")
        StatusBarWhiteColor()

    }


}