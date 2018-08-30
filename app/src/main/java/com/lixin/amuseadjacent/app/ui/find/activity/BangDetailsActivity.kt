package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentAdapter
import kotlinx.android.synthetic.main.activity_bang_details.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 帮帮详情
 * Created by Slingge on 2018/8/30
 */
class BangDetailsActivity : BaseActivity(), View.OnClickListener {


    private var commentAdapter: DynamicCommentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bang_details)
        init()
    }


    private fun init() {
        inittitle("帮帮详情")
        StatusBarWhiteColor()

        tv_right.visibility = View.VISIBLE
        tv_right.text = "收藏"
        tv_right.setOnClickListener(this)

        val ll_image = findViewById<LinearLayout>(R.id.ll_image)
        ll_image.visibility = View.VISIBLE

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_comment.layoutManager = linearLayoutManager
        rv_comment.isFocusable = false
        rv_comment.setPullRefreshEnabled(false)

        commentAdapter = DynamicCommentAdapter(this)
        rv_comment.adapter = commentAdapter
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_right -> {//收藏

            }
        }
    }


}