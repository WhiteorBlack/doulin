package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity

/**
 * Created by Slingge on 2018/9/3
 */
class WebViewActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        val flag = intent.getIntExtra("flag", -1)
        if (flag == 0) {//优惠券说名
            inittitle("优惠券说明")
        } else if (flag == 1) {//常见问题
            inittitle("常见问题")
        } else if (flag == 2) {//关于逗邻
            inittitle("关于逗邻")
        }
    }

}