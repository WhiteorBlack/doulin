package com.lixin.amuseadjacent.app.ui.mine.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclDetailsModel
import com.lixin.amuseadjacent.app.ui.mine.adapter.CollectionAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.CollectModel
import com.lixin.amuseadjacent.app.ui.mine.request.CollectList_123
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 收藏
 * Created by Slingge on 2018/9/3
 */
class CollectionActivity : BaseActivity() {

    private var collectionAdapter: CollectionAdapter? = null
    private var collectList = ArrayList<CollectModel.collectModel>()

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
        inittitle("收藏")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        collectionAdapter = CollectionAdapter(this, collectList)
        xrecyclerview.adapter = collectionAdapter

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                onRefresh = 1
                nowPage = 1
                if (collectList.isNotEmpty()) {
                    collectList.clear()
                    collectionAdapter!!.notifyDataSetChanged()
                }
                CollectList_123.collect(nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                CollectList_123.collect(nowPage)
            }
        })

        ProgressDialog.showDialog(this)
        CollectList_123.collect(nowPage)
    }


    @Subscribe
    fun onEvent(model: CollectModel) {
        totalPage = model.totalPage

        collectList.addAll(model.dataList)

        if (totalPage <= 1) {
            if (collectList.isEmpty()) {
                xrecyclerview.setNullData(this)
            }
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }
        if(nowPage==1){
            val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
            xrecyclerview.layoutAnimation = controller
            collectionAdapter!!.notifyDataSetChanged()
            xrecyclerview.scheduleLayoutAnimation()
        }else{
            collectionAdapter!!.notifyDataSetChanged()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}