package com.lixin.amuseadjacent.app.ui.mine.activity.order

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/9/4
 */
class EvaluateActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("")
        tv_title.visibility = View.INVISIBLE
        tv_right.visibility=View.VISIBLE
        tv_right.text="发表"
        tv_right.setOnClickListener{v->

        }


    }


}