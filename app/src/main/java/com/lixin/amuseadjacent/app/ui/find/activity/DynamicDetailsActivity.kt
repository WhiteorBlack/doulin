package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentAdapter
import kotlinx.android.synthetic.main.activity_dynamic_details.*

/**
 * Created by Slingge on 2018/8/25.
 */
class DynamicDetailsActivity : BaseActivity() {

    private var commentAdapter: DynamicCommentAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_details)
        init()
    }


    private fun init() {
        inittitle("动态详情")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_comment.layoutManager = linearLayoutManager

        commentAdapter = DynamicCommentAdapter(this)
        rv_comment.adapter = commentAdapter

    }


}