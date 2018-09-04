package com.lixin.amuseadjacent.app.ui.mine.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_code_my.*

/**
 * 我的二维码
 * Created by Slingge on 2018/9/1
 */
class MyQRActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_my)
        init()
    }


    private fun init() {
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.setColorNoTranslucent(this, Color.parseColor("#1A1A1A"))
        }

        iv_back.setOnClickListener { v ->
            finish()
        }
    }

}