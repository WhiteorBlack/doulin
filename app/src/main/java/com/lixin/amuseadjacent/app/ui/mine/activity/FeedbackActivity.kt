package com.lixin.amuseadjacent.app.ui.mine.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.request.ActivityComment_272829210
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

    private var id = ""//不为空。评论活动

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        init()
    }


    private fun init() {
        if (intent != null) {
            id = intent.getStringExtra("id")
        }

        if (TextUtils.isEmpty(id)) {
            inittitle("APP意见反馈")
        } else {
            inittitle("评论活动")
            et_feed.hint="评论内容..."
        }

        StatusBarWhiteColor()

        tv_right.visibility = View.VISIBLE
        tv_right.text = "提交"
        tv_right.setTextColor(resources.getColor(R.color.colorTheme))
        tv_right.setOnClickListener { v ->
            val content = AbStrUtil.etTostr(et_feed)
            if (TextUtils.isEmpty(content)) {
                if (TextUtils.isEmpty(id)) {
                    ToastUtil.showToast("请输入反馈内容")
                } else {
                    ToastUtil.showToast("请输入评论内容")
                }
                return@setOnClickListener
            }
            ProgressDialog.showDialog(this)
            if (TextUtils.isEmpty(content)) {
                Feedback_131.feed(this, content)
            } else {
                ActivityComment_272829210.comment("0", id, "", content, object : ActivityComment_272829210.CommentCallBack {
                    override fun commemt() {
                        val intent = Intent()
                        intent.putExtra("content", content)
                        setResult(1, intent)
                        finish()
                    }
                })
            }
        }
    }


}