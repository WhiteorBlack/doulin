package com.lixin.amuseadjacent.app.ui.mine.fragment

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
import com.lixin.amuseadjacent.app.ui.mine.adapter.InteractionAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.InteractionModel
import com.lixin.amuseadjacent.app.ui.mine.request.Myinteraction_161162
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 个人主页 互动
 * Created by Slingge on 2018/8/18
 */
class InteractionFragment : BaseFragment() {

    private var linearLayoutManager: LinearLayoutManager? = null
    private var interactionList = ArrayList<InteractionModel.dataModel>()
    private var interactionAdapter: InteractionAdapter? = null


    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    private var auid = ""

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
        interactionAdapter = InteractionAdapter(activity!!, auid, interactionList)
        xrecyclerview.adapter = interactionAdapter

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                if (interactionList.isNotEmpty()) {
                    interactionList.clear()
                    interactionAdapter!!.notifyDataSetChanged()
                }
                nowPage = 1
                onRefresh = 1
                Myinteraction_161162.Interaction(nowPage, auid)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                Myinteraction_161162.Interaction(nowPage, auid)
            }
        })

    }

    private fun init() {
        auid = arguments!!.getString("auid")
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL

    }

    @Subscribe
    fun onEvent(model: InteractionModel) {
        interactionList.addAll(model.dataList)
        totalPage = model.totalPage
        if (totalPage <= 1) {
            if (interactionList.isEmpty()) {
                xrecyclerview.setNullDataFragment(activity!!)
            } else {
                xrecyclerview.noMoreLoading()
            }
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }

        if (nowPage == 1) {
            val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
            xrecyclerview.layoutAnimation = controller
            interactionAdapter!!.notifyDataSetChanged()
            xrecyclerview.scheduleLayoutAnimation()
        } else {
            interactionAdapter!!.notifyDataSetChanged()
        }
    }

    override fun loadData() {
        ProgressDialog.showDialog(activity!!)
        Myinteraction_161162.Interaction(nowPage, auid)
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