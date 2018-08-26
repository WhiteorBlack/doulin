package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 动态发布选择地址
 * Created by Slingge on 2018/8/25.
 */
class SelectionAddressActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_address)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("选择位置")

        iv_right.visibility = View.VISIBLE
        iv_right.setImageResource(R.drawable.ic_search1)
        iv_right.setOnClickListener { v ->

        }
    }

}