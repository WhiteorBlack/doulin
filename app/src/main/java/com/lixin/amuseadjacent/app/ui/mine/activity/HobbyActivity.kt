package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity

/**
 * 兴趣爱好
 * Created by Slingge on 2018/9/1
 */
class HobbyActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hobby)
        init()
    }


    private fun init() {
        inittitle("兴趣爱好")
        StatusBarWhiteColor()

    }


}