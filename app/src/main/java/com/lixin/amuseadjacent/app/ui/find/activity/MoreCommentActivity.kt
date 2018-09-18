package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity

/**
 * 更多评论
 * Created by Slingge on 2018/9/15
 */
class MoreCommentActivity : BaseActivity() {

    private var activityI=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }

    private fun init() {
        inittitle("全部评论")
        StatusBarWhiteColor()


    }


}