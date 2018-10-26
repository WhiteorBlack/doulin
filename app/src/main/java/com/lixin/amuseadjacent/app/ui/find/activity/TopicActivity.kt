package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentAdapter
import com.lixin.amuseadjacent.app.ui.find.model.ActivityCommentModel1
import com.lixin.amuseadjacent.app.ui.find.model.TopicModel
import com.lixin.amuseadjacent.app.ui.find.request.ActivityComment_272829210
import com.lixin.amuseadjacent.app.ui.find.request.Find_26
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.GetDateTimeUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.view.MyWebView
import com.tencent.smtt.sdk.WebView
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.header_topic.*

import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

/**
 * 话题
 * Created by Slingge on 2018/10/12
 */
class TopicActivity : BaseActivity(), View.OnClickListener ,DynamicCommentAdapter.DelCommentCallBack{

    private var topicId = ""//话题id

    private var commentAdapter: DynamicCommentAdapter? = null
    private var commentList = ArrayList<ActivityCommentModel1.commModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    private var tv_comment: TextView? = null
    private var tv_zan: TextView? = null
    private var webview: WebView? = null

    private var timer: Timer? = null

    private var model: TopicModel.objectModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE
        iv_back.setOnClickListener(this)
        topicId = intent.getStringExtra("id")


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        val headerView = LayoutInflater.from(this).inflate(R.layout.header_topic, null, false)//头布局
        headerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(intent.getStringExtra("imageUrl"),headerView.findViewById<ImageView
                >(R.id.iv_topic))
        val myWebView = headerView.findViewById<MyWebView>(R.id.webview)//功能去掉了，隐藏
        webview = myWebView.webView

        tv_comment = headerView.findViewById(R.id.tv_comment)
        tv_comment!!.setOnClickListener(this)
        tv_zan = headerView.findViewById(R.id.tv_zan)
        tv_zan!!.setOnClickListener(this)
        tv_send.setOnClickListener(this)

        xrecyclerview.addHeaderView(headerView)
        commentAdapter = DynamicCommentAdapter(this, commentList)
        commentAdapter!!.setDelCommentCallBack(this)
        xrecyclerview.adapter = commentAdapter
        commentAdapter!!.setId(topicId, "2")

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (commentList.isNotEmpty()) {
                    commentList.clear()
                    commentAdapter!!.notifyDataSetChanged()
                }
                ActivityComment_272829210.getComment1("1", topicId, nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage > totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                ActivityComment_272829210.getComment1("1", topicId, nowPage)
            }
        })

        rl_3.visibility = View.VISIBLE

        ProgressDialog.showDialog(this)
        ActivityComment_272829210.getComment1("1", topicId, nowPage)
        ActivityComment_272829210.getthemedetail(this, topicId)
    }

    //详情
    @Subscribe
    fun onEvent(model: TopicModel.objectModel) {
        this.model = model
        setDetails()
    }

    @Subscribe
    fun onEvent(model: ActivityCommentModel1) {
        totalPage = model.totalPage

        commentList.addAll(model.dataList)

        if (totalPage == 1) {
            xrecyclerview.noMoreLoading()
        } else {
            if (nowPage > totalPage) {
                xrecyclerview.noMoreLoading()
            }
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }
        commentAdapter!!.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_zan -> {
                ActivityComment_272829210.zan("1", topicId, "", object : Find_26.ZanCallback {
                    override fun zan() {
                        if (model!!.isZan == "1") {
                            model!!.isZan = "0"
                            model!!.zanNum = (model!!.zanNum.toInt() - 1).toString()
                            AbStrUtil.setDrawableLeft(this@TopicActivity, R.drawable.ic_zan, tv_zan, 5)
                        } else {
                            AbStrUtil.setDrawableLeft(this@TopicActivity, R.drawable.ic_zan_hl, tv_zan, 5)
                            model!!.isZan = "1"
                            model!!.zanNum = (model!!.zanNum.toInt() + 1).toString()
                        }
                        tv_zan!!.text = (model!!.zanNum)
                    }
                })
            }
            R.id.tv_comment -> {
                if (timer == null) {
                    timer = Timer()
                }
                et_comment.requestFocus()//获取焦点 光标出现
                timer!!.schedule(object : TimerTask() {
                    override fun run() {
                        val inputManager = this@TopicActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputManager.showSoftInput(et_comment, 0)
                    }
                }, 100)
            }
            R.id.tv_send -> {
                val content = AbStrUtil.etTostr(et_comment)
                if (TextUtils.isEmpty(content)) {
                    return
                }
                ProgressDialog.showDialog(this)
                ActivityComment_272829210.comment("1", topicId, "", content, object : ActivityComment_272829210.CommentCallBack {
                    override fun commemt(commentId: String) {
                        et_comment.setText("")
                        val models = ActivityCommentModel1.commModel()
                        models.commentContent = content
                        models.commentIcon = StaticUtil.headerUrl
                        models.commentTime = GetDateTimeUtil.getYMDHMS()
                        models.secondNum = "0"
                        models.commentName = StaticUtil.nickName
                        models.zanNum = "0"
                        models.commentUid = StaticUtil.uid
                        models.commentId = commentId
                        commentList.add(models)
                        commentAdapter!!.notifyDataSetChanged()

                        model!!.commentNum = ((model!!.commentNum).toInt() + 1).toString()
                        tv_comment!!.text = model!!.commentNum
                    }
                })
            }
            R.id.iv_back->{
                BackPressed()
            }
        }
    }


    private fun setDetails() {
        tv_comment!!.text = model!!.commentNum
        if (model!!.isZan == "0") {// 0未赞过 1已赞过
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_zan, tv_zan, 5)
        } else {
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_zan_hl, tv_zan, 5)
        }
        tv_zan!!.text = model!!.zanNum
        tv_participate.text=model!!.themeTitle
        tv_title.text="话题详情"
        webview!!.loadUrl(model!!.themeDetailUrl)
    }


    override fun delComment() {
        model!!.commentNum = ((model!!.commentNum).toInt() - 1).toString()
        tv_comment!!.text = model!!.commentNum
    }
    override fun delComment(position: Int) {
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 303) {//二级回复
            if (data.getStringExtra("type") == "del") {
                commentList.removeAt(data.getIntExtra("position", 0))
            } else {
                commentList[data.getIntExtra("position", 0)] = data.getSerializableExtra("model") as ActivityCommentModel1.commModel
            }
            commentAdapter!!.notifyDataSetChanged()
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> BackPressed()
            else -> {
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun BackPressed() {
        val intent = Intent()
        intent.putExtra("model", model)
        setResult(0, intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}