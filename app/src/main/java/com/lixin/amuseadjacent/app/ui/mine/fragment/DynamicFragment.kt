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
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclModel
import com.lixin.amuseadjacent.app.ui.find.model.FindModel
import com.lixin.amuseadjacent.app.ui.find.request.DynamicList_219
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.DynamicAdapter
import com.lixin.amuseadjacent.app.ui.mine.request.MyDynamic_132
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 个人主页动态
 * Created by Slingge on 2018/8/18
 */
class DynamicFragment : BaseFragment() {

    private var linearLayoutManager: LinearLayoutManager? = null
    private var dynamicAdapter: DynamicAdapter? = null
    private var dynaList = ArrayList<FindModel.dynamicModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    private var auid=""

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
        dynamicAdapter = DynamicAdapter(activity!!, dynaList)
        xrecyclerview.adapter = dynamicAdapter
        xrecyclerview.setPullRefreshEnabled(false)

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                if (dynaList.isNotEmpty()) {
                    dynaList.clear()
                    dynamicAdapter!!.notifyDataSetChanged()
                }
                onRefresh = 1
                MyDynamic_132.dynamic(nowPage, auid)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                MyDynamic_132.dynamic(nowPage,auid)
            }
        })
    }

    private fun init() {
        auid=arguments!!.getString("auid")
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
    }


    override fun loadData() {
        ProgressDialog.showDialog(activity!!)
        MyDynamic_132.dynamic(nowPage, auid)
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


    override fun onPause() {
        super.onPause()
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}