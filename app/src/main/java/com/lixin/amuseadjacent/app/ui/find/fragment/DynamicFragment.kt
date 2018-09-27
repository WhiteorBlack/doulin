package com.lixin.amuseadjacent.app.ui.find.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicAdapter
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclDetailsModel
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclModel
import com.lixin.amuseadjacent.app.ui.find.model.FindModel
import com.lixin.amuseadjacent.app.ui.find.request.DynamicList_219
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 动态列表
 * Created by Slingge on 2018/8/21
 */
class DynamicFragment : BaseFragment() {

    private var flag = -1

    private var linearLayoutManager: LinearLayoutManager? = null

    private var dynamicAdapter: DynamicAdapter? = null
    private var dynaList = ArrayList<FindModel.dynamicModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.xrecyclerview, container, false)
        EventBus.getDefault().register(this)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        include.visibility = View.GONE

        xrecyclerview.layoutManager = linearLayoutManager
//        xrecyclerview.setPullRefreshEnabled(false)

        dynamicAdapter = DynamicAdapter(activity!!, "0", dynaList)
        xrecyclerview.adapter = dynamicAdapter

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                if (dynaList.isNotEmpty()) {
                    dynaList.clear()
                    dynamicAdapter!!.notifyDataSetChanged()
                }
                onRefresh = 1
                DynamicList_219.dynamic("0", flag, nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                DynamicList_219.dynamic("0", flag, nowPage)
            }
        })
    }

    private fun init() {
        flag = arguments!!.getInt("flag", -1)

        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
    }


    override fun loadData() {
        if (dynaList.isNotEmpty()) {
            dynaList.clear()
            dynamicAdapter!!.notifyDataSetChanged()
        }
        nowPage = 1
        ProgressDialog.showDialog(activity!!)
        DynamicList_219.dynamic("0", flag, nowPage)
    }

    @Subscribe
    fun onEvent(model: DynamiclModel) {
        dynaList.addAll(model.dataList)

        totalPage = model.totalPage

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }

        if (nowPage == 1) {
            val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
            xrecyclerview.layoutAnimation = controller
            dynamicAdapter!!.notifyDataSetChanged()
            xrecyclerview.scheduleLayoutAnimation()
        } else {
            dynamicAdapter!!.notifyDataSetChanged()
        }
    }

    fun Refresh(i: Int, model: DynamiclDetailsModel?, position: Int) {
        if (i == flag) {
            if (model == null) {
                if (dynaList.isNotEmpty()) {
                    dynaList.clear()
                    dynamicAdapter!!.notifyDataSetChanged()
                }
                nowPage = 1
                ProgressDialog.showDialog(activity!!)
                DynamicList_219.dynamic("0", flag, nowPage)
            }else{
                dynaList[position].commentNum = model.`object`.commentNum
                dynaList[position].isZan = model.`object`.isZan
                dynaList[position].isAttention = model.`object`.isAttention
                dynaList[position].zanNum = model.`object`.zanNum
                dynamicAdapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}