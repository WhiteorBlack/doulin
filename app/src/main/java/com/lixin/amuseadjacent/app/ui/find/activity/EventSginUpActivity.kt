package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity

/**
 * Created by Slingge on 2018/8/26.
 */
class EventSginUpActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_sginup)
        init()
    }


    private fun init(){
        StatusBarWhiteColor()
        inittitle("活动报名")
    }

}