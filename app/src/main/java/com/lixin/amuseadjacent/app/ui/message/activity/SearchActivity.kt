package com.lixin.amuseadjacent.app.ui.message.activity

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*
import android.view.inputmethod.EditorInfo.IME_ACTION_UNSPECIFIED
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import com.lixin.amuseadjacent.app.util.AbStrUtil


/**
 * Created by Slingge on 2018/8/16
 */
class SearchActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        for (i in 0 until 10) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_textview, flowLayout, false) as TextView
            tv.text = "洛克贝尔" + i.toString()
            tv.setTextColor(Color.parseColor("#666666"))
            tv.setOnClickListener {

            }
            flowLayout.addView(tv)
        }
        tv_cancel.setOnClickListener { v->
            finish()
        }


        et_search.setOnEditorActionListener(TextView.OnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH || p1 == EditorInfo.IME_ACTION_UNSPECIFIED) {
                val keytag = AbStrUtil.etTostr(et_search)

                return@OnEditorActionListener true
            }
            false
        })


    }

}