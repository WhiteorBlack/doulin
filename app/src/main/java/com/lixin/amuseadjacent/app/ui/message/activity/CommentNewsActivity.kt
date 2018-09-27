package com.lixin.amuseadjacent.app.ui.message.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.message.adapter.CommentNewsAdapter
import com.lixin.amuseadjacent.app.ui.message.model.CommentNewModel
import com.lixin.amuseadjacent.app.ui.message.request.MsgList_21
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 评论消息
 * Created by Slingge on 2018/8/16
 */
class CommentNewsActivity : BaseActivity() {

    private var commAdapter: CommentNewsAdapter? = null
    private var commentList = ArrayList<CommentNewModel.msgModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("收到的消息")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        xrecyclerview.layoutManager = linearLayoutManager

        commAdapter = CommentNewsAdapter(this, commentList)
        xrecyclerview.adapter = commAdapter

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (commentList.isNotEmpty()) {
                    commentList.clear()
                    commAdapter!!.notifyDataSetChanged()
                }
                MsgList_21.commentNew(nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                MsgList_21.commentNew(nowPage)
            }
        })

        ProgressDialog.showDialog(this)
        MsgList_21.commentNew(nowPage)
    }

    @Subscribe
    fun onEven(model: CommentNewModel) {
        commentList.addAll(model.dataList)
        totalPage = model.totalPage

        if (totalPage <= 1) {
            if (commentList.isEmpty()) {
                xrecyclerview.setNullData(this)
            } else {
                xrecyclerview.noMoreLoading()
            }
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        commAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}