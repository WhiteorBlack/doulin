package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicCommentAdapter
import com.lixin.amuseadjacent.app.ui.find.model.ActivityCommentModel1
import com.lixin.amuseadjacent.app.ui.find.request.ActivityComment_272829210
import com.lixin.amuseadjacent.app.ui.find.request.DynaComment_133134
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 全部活动评论
 * Created by Slingge on 2018/10/13
 */
class EventAllCommentsActivity : BaseActivity() , DynaComment_133134.FirstCommentCallBack{


    private var commentAdapter: DynamicCommentAdapter? = null
    private var commentList = ArrayList<ActivityCommentModel1.commModel>()

    private var dynaId = ""//活动id
    private var position = -1

    private var onRefresh = 0
    private var nowPage = 1
    private var totalPage = 1

    private var isChu = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        inittitle("全部评论")
        StatusBarWhiteColor()

        view_staus.visibility = View.GONE
        inittitle("全部评论")

        dynaId = intent.getStringExtra("id")

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        commentAdapter = DynamicCommentAdapter(this, commentList)
        xrecyclerview.adapter = commentAdapter
        commentAdapter!!.setId(dynaId, "0")

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (commentList.isNotEmpty()) {
                    commentList.clear()
                    commentAdapter!!.notifyDataSetChanged()
                }
                ActivityComment_272829210.getComment2("0", dynaId, nowPage,this@EventAllCommentsActivity)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage > totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                ActivityComment_272829210.getComment2("0", dynaId, nowPage,this@EventAllCommentsActivity)
            }
        })

        ProgressDialog.showDialog(this)
        ActivityComment_272829210.getComment2("0", dynaId, nowPage,this)
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
                commentList[position] = data.getSerializableExtra("model") as ActivityCommentModel1.commModel
                isChu = 1
            }
            commentAdapter!!.notifyDataSetChanged()
        }
    }


}