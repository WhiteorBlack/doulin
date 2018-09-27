package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentReplyAdapter
import com.lixin.amuseadjacent.app.ui.find.model.ActivityCommentModel1
import com.lixin.amuseadjacent.app.ui.find.model.CommentModel1
import com.lixin.amuseadjacent.app.ui.find.request.ActivityComment_272829210
import com.lixin.amuseadjacent.app.ui.find.request.DynaComment_133134
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.AndroidBug5497Workaround
import com.lixin.amuseadjacent.app.util.GetDateTimeUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_dynamic_detailsreply.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 动态评论回复
 * Created by Slingge on 2018/8/25.
 */
class DynamicDetailsReplyActivity : BaseActivity() {

    private var replyAdapter: DynamicCommentReplyAdapter? = null
    private var commentList = ArrayList<ActivityCommentModel1.commModel>()

    private var headerView: View? = null

    private var model = ActivityCommentModel1.commModel()
    private var dynaId = ""
    private var commentId = ""//一级评论id

    private var commNum = 0//回复数量


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_detailsreply)
        AndroidBug5497Workaround.assistActivity(this)//底部EditText不能被软键盘顶起
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        dynaId = intent.getStringExtra("id")
        commentId=intent.getStringExtra("commentId")

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_reply.layoutManager = linearLayoutManager
        rv_reply.setPullRefreshEnabled(false)
        rv_reply.setLoadingMoreEnabled(false)

        headerView = LayoutInflater.from(this).inflate(R.layout.item_dynamic_comment, null, false)//头布局
        headerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        rv_reply.addHeaderView(headerView)

        replyAdapter = DynamicCommentReplyAdapter(this, commentList)
        rv_reply.adapter = replyAdapter

        if (intent.getSerializableExtra("model") != null) {
            model = intent.getSerializableExtra("model") as ActivityCommentModel1.commModel
            setDetails(model)
        } else {
            ProgressDialog.showDialog(this)
            DynaComment_133134.commentFirst(commentId)
        }

        tv_send.setOnClickListener { v ->
            val content = AbStrUtil.etTostr(et_comment)
            if (TextUtils.isEmpty(content)) {
                return@setOnClickListener
            }
            ProgressDialog.showDialog(this)
            DynaComment_133134.comment(dynaId, model.commentId, content, object : ActivityComment_272829210.CommentCallBack {
                override fun commemt(commentId: String) {
                    commNum++
                    inittitle(commNum.toString() + "条回复")
                    headerView!!.findViewById<TextView>(R.id.tv_commentNum).text = commNum.toString() + "回复"

                    et_comment.setText("")
                    val model = ActivityCommentModel1.commModel()
                    model.commentContent = content
                    model.commentIcon = StaticUtil.headerUrl
                    model.commentTime = GetDateTimeUtil.getYMDHMS()
                    model.secondNum = "0"
                    model.commentName = StaticUtil.nickName
                    model.zanNum = "0"
                    model.commentUid = StaticUtil.uid
                    model.commentId = commentId
                    commentList.add(0, model)
                    replyAdapter!!.notifyDataSetChanged()
                }

            })

        }
    }


    private fun setDetails(model: ActivityCommentModel1.commModel) {
        headerView!!.findViewById<TextView>(R.id.tv_name).text = model.commentName
        ImageLoader.getInstance().displayImage(model.commentIcon, headerView!!.findViewById<ImageView>(R.id.iv_header))
        headerView!!.findViewById<TextView>(R.id.tv_zan).text = model.zanNum
        headerView!!.findViewById<TextView>(R.id.tv_comment).text = model.commentContent
        headerView!!.findViewById<TextView>(R.id.tv_time).text = model.commentTime
        headerView!!.findViewById<TextView>(R.id.tv_commentNum).text = model.secondNum + "回复"
        commNum = model.secondNum.toInt()

        inittitle(model.secondNum + "条回复")

        ProgressDialog.showDialog(this)
        DynaComment_133134.commentSecond(dynaId, model.commentId)
    }

    @Subscribe
    fun onEvent(model: ActivityCommentModel1.commModel) {
        this.model = model
        setDetails(this.model)
    }

    @Subscribe
    fun onEvent(model: ActivityCommentModel1) {
        commentList.addAll(model.dataList)
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_reply.layoutAnimation = controller
        replyAdapter!!.notifyDataSetChanged()
        rv_reply.scheduleLayoutAnimation()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}