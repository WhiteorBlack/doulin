package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentAdapter
import kotlinx.android.synthetic.main.activity_official_shop_details.*

/**
 * 官方店铺详情
 * Created by Slingge on 2018/8/31
 */
class OfficialShopDetailsActivity : BaseActivity() {

    private var commentAdapter:DynamicCommentAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_official_shop_details)
        init()
    }


    private fun init() {
        inittitle("店铺名称")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_comment.layoutManager = linearLayoutManager

        rv_comment.isFocusable = false

        commentAdapter= DynamicCommentAdapter(this)
        rv_comment.adapter=commentAdapter

    }


}