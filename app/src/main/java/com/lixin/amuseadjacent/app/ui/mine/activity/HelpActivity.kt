package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.ProblemAdapter
import kotlinx.android.synthetic.main.activity_help.*

/**
 * Created by Slingge on 2018/9/3
 */
class HelpActivity : BaseActivity() {

    private var problemAdapter: ProblemAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        init()
    }


    private fun init() {
        inittitle("帮助")
        StatusBarWhiteColor()

        val linelayout = LinearLayoutManager(this)
        linelayout.orientation = LinearLayoutManager.VERTICAL

        rv_problem.layoutManager = linelayout
        problemAdapter = ProblemAdapter(this)
        rv_problem.adapter = problemAdapter


    }


}