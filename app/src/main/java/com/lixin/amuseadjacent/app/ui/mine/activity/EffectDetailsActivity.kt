package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.EffectDetailsAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.EffectDetailsModel
import com.lixin.amuseadjacent.app.ui.mine.model.EffectModel
import com.lixin.amuseadjacent.app.ui.mine.request.Effect_312166
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 影响力
 * Created by Slingge on 2018/9/2.
 */
class EffectDetailsActivity : BaseActivity() {

    private var effectAdapter: EffectDetailsAdapter? = null
    private var effList = ArrayList<EffectDetailsModel.dataModel>()

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
        inittitle("影响力")
        StatusBarWhiteColor()

        val linelayout = LinearLayoutManager(this)
        linelayout.orientation = LinearLayoutManager.VERTICAL

        xrecyclerview.layoutManager = linelayout
        effectAdapter = EffectDetailsAdapter(this, effList)
        xrecyclerview.adapter = effectAdapter

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh=1
                if (effList.isNotEmpty()) {
                    effList.clear()
                    effectAdapter!!.notifyDataSetChanged()
                }
                Effect_312166.effectDetails(nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage > totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh=2
                Effect_312166.effectDetails(nowPage)
            }
        })
        ProgressDialog.showDialog(this)
        Effect_312166.effectDetails(nowPage)
    }


    @Subscribe
    fun onEvent(model: EffectDetailsModel) {
        totalPage = model.totalPage

        effList.addAll(model.dataList)

        if (totalPage == 1) {
            if (effList.isEmpty()) {
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
        effectAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}