package com.lixin.amuseadjacent.app.ui.message.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.message.adapter.OfficialNewsAdapter
import com.lixin.amuseadjacent.app.ui.message.model.OfficialNewModel
import com.lixin.amuseadjacent.app.ui.message.request.MsgList_21
import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 官方消息
 * Created by Slingge on 2018/8/16
 */
class OfficialNewsActivity : BaseActivity() {

    private var offAdapter: OfficialNewsAdapter? = null
    private var offList = ArrayList<OfficialNewModel.msgModel>()

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
        inittitle("官方消息")
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        xrecyclerview.layoutManager = linearLayoutManager

        offAdapter = OfficialNewsAdapter(this,offList)
        xrecyclerview.adapter = offAdapter
        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (offList.isNotEmpty()) {
                    offList.clear()
                    offAdapter!!.notifyDataSetChanged()
                }
                MsgList_21.OfficialNews(nowPage)
            }

            override fun onLoadMore() {
                nowPage ++
                if(nowPage>=totalPage){
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                MsgList_21.OfficialNews(nowPage)
            }
        })

        ProgressDialog.showDialog(this)
        MsgList_21.OfficialNews(nowPage)
    }


    @Subscribe
    fun onEven(model: OfficialNewModel) {
        offList.addAll(model.dataList)
        totalPage = model.totalPage

        if (totalPage <= 1) {
            if (offList.isEmpty()) {
                xrecyclerview.setNullData(this)
            }
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        offAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}