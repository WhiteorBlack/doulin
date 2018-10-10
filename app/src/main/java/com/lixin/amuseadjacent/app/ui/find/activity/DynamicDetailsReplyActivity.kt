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
import com.lixin.amuseadjacent.app.util.AndroidBug5497Workaround
import com.lixin.amuseadjacent.app.util.GetDateTimeUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_dynamic_detailsreply.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

/**
 * 动态评论回复
 * Created by Slingge on 2018/8/25.
 */
class DynamicDetailsReplyActivity : BaseActivity() {

    private var replyAdapter: DynamicCommentReplyAdapter? = null
    private var commentList = ArrayList<ActivityCommentModel1.commModel>()
    private var commModel: ActivityCommentModel1.commModel? = null//一级评论

    private var headerView: View? = null
    private var tv_zan: TextView? = null

    private var dynaId = ""
    private var commentId = ""//动态id

    private var commNum = 0//回复数量

    private var chushi = -2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_detailsreply)
        AndroidBug5497Workaround.assistActivity(this)//底部EditText不能被软键盘顶起
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        iv_back.setOnClickListener{v->
            finish()
        }
        dynaId = intent.getStringExtra("id")

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
            commModel = intent.getSerializableExtra("model") as ActivityCommentModel1.commModel
            setDetails(commModel!!)
        } else {
            commentId=intent.getStringExtra("commentId")
            ProgressDialog.showDialog(this)
            DynaComment_133134.commentFirst(commentId)
        }

        tv_send.setOnClickListener { v ->
            val content = AbStrUtil.etTostr(et_comment)
            if (TextUtils.isEmpty(content)) {
                return@setOnClickListener
            }
            ProgressDialog.showDialog(this)
            DynaComment_133134.comment(dynaId, commModel!!.commentId, content, object : ActivityComment_272829210.CommentCallBack {
                override fun commemt(commentId: String) {
                    commNum++
                    tv_title.text=commNum.toString() + "条回复"
                    headerView!!.findViewById<TextView>(R.id.tv_commentNum).text = commNum.toString() + "回复"
                    commModel!!.secondNum = commNum.toString()

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

                    chushi = 2
                }
            })
        }
    }


    private fun setDetails(model: ActivityCommentModel1.commModel) {
        commModel = model
        commentId=commModel!!.commentId
        headerView!!.findViewById<TextView>(R.id.tv_name).text = commModel!!.commentName
        ImageLoader.getInstance().displayImage(commModel!!.commentIcon, headerView!!.findViewById<ImageView>(R.id.iv_header))
        tv_zan = headerView!!.findViewById(R.id.tv_zan)
        tv_zan!!.text = commModel!!.zanNum
        if (commModel!!.isZan == "1") {//0未赞过 1已赞过
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_zan_hl, tv_zan, 5)
        }
        tv_zan!!.setOnClickListener { v ->
            ProgressDialog.showDialog(this)
            DynaComment_133134.zan(dynaId, commModel!!.commentId, object : Find_26.ZanCallback {
                override fun zan() {
                    if (commModel!!.isZan == "1") {
                        commModel!!.zanNum = (commModel!!.zanNum.toInt() - 1).toString()
                        commModel!!.isZan = "0"
                    }else{
                        commModel!!.zanNum = (commModel!!.zanNum.toInt() + 1).toString()
                        commModel!!.isZan = "1"
                    }
                    tv_zan!!.text = commModel!!.zanNum
                    AbStrUtil.setDrawableLeft(this@DynamicDetailsReplyActivity, R.drawable.ic_zan_hl, tv_zan, 5)
                    chushi = 1
                }
            })
        }

        headerView!!.findViewById<TextView>(R.id.tv_comment).text = commModel!!.commentContent
        headerView!!.findViewById<TextView>(R.id.tv_time).text = commModel!!.commentTime
        headerView!!.findViewById<TextView>(R.id.tv_commentNum).text = commModel!!.secondNum + "回复"
        headerView!!.findViewById<TextView>(R.id.tv_commentNum).setOnClickListener{v->
            if (timer == null) {
                timer = Timer()
            }
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    val inputManager = this@DynamicDetailsReplyActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.showSoftInput(et_comment, 0)
                }
            }, 10)
        }
        commNum = commModel!!.secondNum.toInt()

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

        tv_title.text=commNum.toString() + "条回复"

        ProgressDialog.showDialog(this)
        DynaComment_133134.commentSecond(dynaId, commModel!!.commentId)
    }

    private var timer: Timer? = null
    @Subscribe
    fun onEvent(model: ActivityCommentModel1.commModel) {
        setDetails(model)
    }

    @Subscribe
    fun onEvent(model: ActivityCommentModel1) {
        commentList.addAll(model.dataList)
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_reply.layoutAnimation = controller
        replyAdapter!!.notifyDataSetChanged()
        rv_reply.scheduleLayoutAnimation()
    }

    fun Destroy() {
        if(chushi!=-2){
            intent.putExtra("position", intent.getIntExtra("position", -1))
            if (chushi == -1) {
                intent.putExtra("type", "del")
            } else {
                intent.putExtra("type", "")
            }
            intent.putExtra("model", commModel)
            setResult(0, intent)
        }
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> Destroy()
            else -> {
            }
        }
        return super.onKeyDown(keyCode, event)
    }
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}