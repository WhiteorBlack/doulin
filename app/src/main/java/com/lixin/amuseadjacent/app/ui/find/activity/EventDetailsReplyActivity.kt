package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
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
import java.util.*

/**
 * 活动、话题评论回复
 * Created by Slingge on 2018/8/25.
 */
class EventDetailsReplyActivity : BaseActivity(), DynamicCommentReplyAdapter.DelCommentCallBack {

    private var replyAdapter: DynamicCommentReplyAdapter? = null
    private var commentList = ArrayList<ActivityCommentModel1.commModel>()

    private var model = ActivityCommentModel1.commModel()
    private var dyeventId = ""//活动id
    private var commentId = ""//评论id

    private var headerView: View? = null
    private var timer: Timer? = null
    private var tv_zan: TextView? = null

    private var type=""//0活动,1话题

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
        replyAdapter!!.setDelCommentCallBack(this)

        dyeventId = intent.getStringExtra("id")
        type= intent.getStringExtra("type")
        if(type=="1"){
            type="0"
        }else if(type=="2"){
            type="1"
        }


        if (intent.getSerializableExtra("model") != null) {
            model = intent.getSerializableExtra("model") as ActivityCommentModel1.commModel
            setDetails()
        } else {//从评论消息跳转，用借口获取一级评论数据
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
        commentId = model.commentId
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
            ProgressDialog.showDialog(this)
            ActivityComment_272829210.zan(type, dyeventId, model.commentId, object : Find_26.ZanCallback {
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

        headerView!!.findViewById<TextView>(R.id.tv_commentNum).setOnClickListener { v ->
            if (timer == null) {
                timer = Timer()
            }
            et_comment.requestFocus()//获取焦点 光标出现
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    val inputManager = this@EventDetailsReplyActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.showSoftInput(et_comment, 0)
                }
            }, 10)
        }

        tv_send.setOnClickListener { v ->
            val content = AbStrUtil.etTostr(et_comment)
            if (TextUtils.isEmpty(content)) {
                return@setOnClickListener
            }
            ProgressDialog.showDialog(this)
            ActivityComment_272829210.comment(type, dyeventId, model.commentId, content, object : ActivityComment_272829210.CommentCallBack {
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
                    commentList.add( sercmodel)
                    model.secondNum = (model.secondNum.toInt() + 1).toString()
                    replyAdapter!!.notifyDataSetChanged()
                    tv_title.text = model.secondNum + "条回复"
                    headerView!!.findViewById<TextView>(R.id.tv_commentNum).text = model.secondNum + "回复"

                    chushi = 2

                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)//输入法在窗口上已经显示，则隐藏，反之则显示
                }
            })
        }

        ProgressDialog.showDialog(this)
        ActivityComment_272829210.getComment1Second(type, dyeventId, model.commentId)
    }

    override fun del() {
        chushi = -3
        model.secondNum = (model.secondNum.toInt() - 1).toString()
        headerView!!.findViewById<TextView>(R.id.tv_commentNum).text = model.secondNum + "回复"
        tv_title.text = model.secondNum + "条回复"
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