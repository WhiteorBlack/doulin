package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_common_problem.*

/**
 * 常见问题
 * Created by Slingge on 2018/9/13
 */
class CommonProblemActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_problem)
        init()
    }


    private fun init() {
        inittitle(intent.getStringExtra("title"))
        StatusBarWhiteColor()

        tv_content.text = intent.getStringExtra("content")
    }


}