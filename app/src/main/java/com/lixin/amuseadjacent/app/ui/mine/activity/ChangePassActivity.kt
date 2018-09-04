package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity

/**
 * 更改密码
 * Created by Slingge on 2018/9/4
 */
class ChangePassActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        init()
    }


    private fun init() {
        inittitle("更改密码")
        StatusBarWhiteColor()
    }

}