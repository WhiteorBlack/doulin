package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.google.gson.Gson
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentAdapter
import com.lixin.amuseadjacent.app.ui.find.model.ActivityCommentModel1
import com.lixin.amuseadjacent.app.ui.find.request.DynaComment_133134
import com.lixin.amuseadjacent.app.util.abLog
import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import java.util.ArrayList

/**
 * 全部动态、帮帮评论
 * Created by Slingge on 2018/10/13
 */
class DynamicAllCommentsActivity : BaseActivity(), DynaComment_133134.FirstCommentCallBack {


    private var commentAdapter: DynamicCommentAdapter? = null
    private var commentList = ArrayList<ActivityCommentModel1.commModel>()

    private var dynaId = ""
    private var flag: String? = null// 0动态，1帮帮,2话题
    var position = -1

    private var onRefresh = 0
    private var nowPage = 1
    private var totalPage = 1

    var isChu = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE
        inittitle("全部评论")

        dynaId = intent.getStringExtra("id")
        flag = intent.getStringExtra("flag")

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        commentAdapter = DynamicCommentAdapter(this, commentList)
        xrecyclerview.adapter = commentAdapter
        commentAdapter!!.setId(dynaId, "0")
        commentAdapter!!.setallComm(0)
        commentAdapter!!.setDelCommentCallBack(object : DynamicCommentAdapter.DelCommentCallBack {
            override fun delComment() {

            }

            override fun delComment(position: Int) {
                this@DynamicAllCommentsActivity.position = position
            }
        })

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (commentList.isNotEmpty()) {
                    commentList.clear()
                    commentAdapter!!.notifyDataSetChanged()
                }
                DynaComment_133134.firstComment(dynaId, nowPage, this@DynamicAllCommentsActivity)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage > totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                DynaComment_133134.firstComment(dynaId, nowPage, this@DynamicAllCommentsActivity)
            }
        })

        ProgressDialog.showDialog(this)
        DynaComment_133134.firstComment(dynaId, nowPage, this)
    }

    override fun firstComment(model: ActivityCommentModel1) {
        totalPage = model.totalPage
        commentList.addAll(model.dataList)

        if (totalPage == 1) {
            xrecyclerview.noMoreLoading()
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }

        if (nowPage == 1) {
            val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
            xrecyclerview.layoutAnimation = controller
            commentAdapter!!.notifyDataSetChanged()
            xrecyclerview.scheduleLayoutAnimation()
        } else {
            commentAdapter!!.notifyDataSetChanged()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 303) {//二级回复
            position = data.getIntExtra("position", 0)
            if (data.getStringExtra("type") == "del") {
                commentList.removeAt(position)
                isChu = 0
            } else {
                abLog.e("回复", Gson().toJson(commentList))
                commentList[position] = data.getSerializableExtra("model") as ActivityCommentModel1.commModel
                isChu = 1
            }
            abLog.e("回复", Gson().toJson(commentList))
            commentAdapter!!.notifyDataSetChanged()
        }
    }



}