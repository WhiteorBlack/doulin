package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.request.Feedback_131
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.nio.file.StandardWatchEventKinds

/**
 * 意见反馈
 * Created by Slingge on 2018/9/4
 */
class FeedbackActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        init()
    }


    private fun init() {
        inittitle("APP意见反馈")
        StatusBarWhiteColor()

        tv_right.visibility = View.VISIBLE
        tv_right.text = "提交"
        tv_right.setTextColor(resources.getColor(R.color.colorTheme))
        tv_right.setOnClickListener { v ->
            val content = AbStrUtil.etTostr(et_feed)
            if (TextUtils.isEmpty(content)) {
                ToastUtil.showToast("请输入反馈内容")
                return@setOnClickListener
            }
            ProgressDialog.showDialog(this)
            Feedback_131.feed(this, content)
        }
    }


}