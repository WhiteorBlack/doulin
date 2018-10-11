package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.KeyEvent
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
import com.lixin.amuseadjacent.app.ui.find.request.ActivityComment_272829210
import com.lixin.amuseadjacent.app.ui.find.request.DeleteComment_227
import com.lixin.amuseadjacent.app.ui.find.request.DynaComment_133134
import com.lixin.amuseadjacent.app.ui.find.request.Find_26
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.GetDateTimeUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_dynamic_detailsreply.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 活动评论回复
 * Created by Slingge on 2018/8/25.
 */
class EventDetailsReplyActivity : BaseActivity() {

    private var replyAdapter: DynamicCommentReplyAdapter? = null
    private var commentList = ArrayList<ActivityCommentModel1.commModel>()

    private var model = ActivityCommentModel1.commModel()
    private var dyeventId = ""//活动id
    private var commentId = ""//评论id

    private var headerView: View? = null

    private var tv_zan: TextView? = null

    private var chushi = -2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_detailsreply)
        EventBus.getDefault().register(this)
        init()
    }

    private fun init() {

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


        dyeventId = intent.getStringExtra("id")

        if(intent.getSerializableExtra("model")!=null){
            model=intent.getSerializableExtra("model")as ActivityCommentModel1.commModel
            setDetails()
        }else{//从评论消息跳转，用借口获取一级评论数据
            commentId = intent.getStringExtra("commentId")
            ProgressDialog.showDialog(this)
            DynaComment_133134.commentFirst(commentId)
        }

        iv_back.setOnClickListener { v ->
            Destroy()
        }
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE
    }


    @Subscribe
    fun onEvent(model: ActivityCommentModel1) {
        commentList.addAll(model.dataList)
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_reply.layoutAnimation = controller
        replyAdapter!!.notifyDataSetChanged()
        rv_reply.scheduleLayoutAnimation()
    }

    //从评论消息跳转，接口获取一级评论内容
    @Subscribe
    fun onEvent(model: ActivityCommentModel1.commModel) {
        this.model = model
        setDetails()
    }

    fun setDetails() {
        commentId=model.commentId
        tv_title.text = model.secondNum + "条回复"

        headerView!!.findViewById<TextView>(R.id.tv_name).text = model.commentName
        ImageLoader.getInstance().displayImage(model.commentIcon, headerView!!.findViewById<ImageView>(R.id.iv_header))
        tv_zan = headerView!!.findViewById(R.id.tv_zan)
        tv_zan!!.text = model.zanNum
        if (model.isZan == "1") {
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_zan_hl, tv_zan, 5)
        }

        headerView!!.findViewById<TextView>(R.id.tv_comment).text = model.commentContent
        headerView!!.findViewById<TextView>(R.id.tv_time).text = model.commentTime
        headerView!!.findViewById<TextView>(R.id.tv_commentNum).text = model.secondNum + "回复"

        val tv_del = headerView!!.findViewById<TextView>(R.id.tv_del)
        if (StaticUtil.uid == model.commentUid) {
            tv_del.visibility = View.VISIBLE
        } else {
            tv_del.visibility = View.GONE
        }

        tv_del.setOnClickListener { v ->
            ProgressDialog.showDialog(this)
            DeleteComment_227.del(model.commentId, object : DeleteComment_227.DelCommentCallBack {
                override fun delComment() {
                    chushi = -1
                    Destroy()
                }
            })
        }

        tv_zan!!.setOnClickListener { v ->
            if (model.isZan == "1") {
                return@setOnClickListener
            }
            ProgressDialog.showDialog(this)
            ActivityComment_272829210.zan("0", dyeventId, model.commentId, object : Find_26.ZanCallback {
                override fun zan() {
                    if (model.isZan == "1") {
                        model.isZan = "0"
                        model.zanNum = (model.zanNum.toInt() - 1).toString()
                        AbStrUtil.setDrawableLeft(this@EventDetailsReplyActivity, R.drawable.ic_zan, tv_zan, 5)
                    } else {
                        model.isZan = "1"
                        model.zanNum = (model.zanNum.toInt() + 1).toString()
                        AbStrUtil.setDrawableLeft(this@EventDetailsReplyActivity, R.drawable.ic_zan_hl, tv_zan, 5)
                    }
                    chushi = 1
                    tv_zan!!.text = model.zanNum
                }
            })
        }

        tv_send.setOnClickListener { v ->
            val content = AbStrUtil.etTostr(et_comment)
            if (TextUtils.isEmpty(content)) {
                return@setOnClickListener
            }
            ProgressDialog.showDialog(this)
            ActivityComment_272829210.comment("0", dyeventId, model.commentId, content, object : ActivityComment_272829210.CommentCallBack {
                override fun commemt(commentId: String) {
                    et_comment.setText("")
                    val sercmodel = ActivityCommentModel1.commModel()
                    sercmodel.commentContent = content
                    sercmodel.commentIcon = StaticUtil.headerUrl
                    sercmodel.commentTime = GetDateTimeUtil.getYMDHMS()
                    sercmodel.secondNum = "0"
                    sercmodel.commentId = commentId
                    sercmodel.commentUid = StaticUtil.uid
                    sercmodel.commentName = StaticUtil.nickName
                    sercmodel.zanNum = "0"
                    commentList.add(0, sercmodel)
                    model.secondNum = (model.secondNum.toInt() + 1).toString()
                    replyAdapter!!.notifyDataSetChanged()
                    tv_title.text = model.secondNum + "条回复"
                    headerView!!.findViewById<TextView>(R.id.tv_commentNum).text = model.secondNum + "回复"

                    chushi = 2
                }
            })
        }

        ProgressDialog.showDialog(this)
        ActivityComment_272829210.getComment1Second("0", dyeventId, model.commentId)
    }


    fun Destroy() {
        if (chushi != -2) {
            intent.putExtra("position", intent.getIntExtra("position", -1))
            if (chushi == -1) {
                intent.putExtra("type", "del")
            } else {
                intent.putExtra("type", "")
            }
            intent.putExtra("model", model)
            setResult(0, intent)
        }
        finish()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                Destroy()
            }
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}