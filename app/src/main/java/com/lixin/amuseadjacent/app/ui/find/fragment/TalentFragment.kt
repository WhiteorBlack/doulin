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
import com.lixin.amuseadjacent.app.ui.find.adapter.TalentAdapter
import com.lixin.amuseadjacent.app.ui.find.model.TalentModel
import com.lixin.amuseadjacent.app.ui.find.request.Talent212_218225
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 达人列表
 * Created by Slingge on 2018/8/21
 */
class TalentFragment : BaseFragment() {

    private var flag = -1

    private var linearLayoutManager: LinearLayoutManager? = null

    private var talentAdapter: TalentAdapter? = null
    private var talentList = ArrayList<TalentModel.dataModel>()

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

        talentAdapter = TalentAdapter(activity!!, talentList)
        xrecyclerview.adapter = talentAdapter

        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        talentAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                if (talentList.isNotEmpty()) {
                    talentList.clear()
                    talentAdapter!!.notifyDataSetChanged()
                }
                onRefresh = 1
                Talent212_218225.talent(flag, nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                Talent212_218225.talent(flag, nowPage)
            }
        })

    }

    private fun init() {
        flag = arguments!!.getInt("flag", -1)

        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
    }

    @Subscribe
    fun onEvent(model: TalentModel) {
        talentList.addAll(model.dataList)

        totalPage = model.totalPage

        if (totalPage <= 1) {
            if (talentList.isNotEmpty()) {
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
            talentAdapter!!.notifyDataSetChanged()
            xrecyclerview.scheduleLayoutAnimation()
        } else {
            talentAdapter!!.notifyDataSetChanged()
        }
    }


    override fun loadData() {
        if (talentList.isNotEmpty()) {
            talentList.clear()
            talentAdapter!!.notifyDataSetChanged()
        }
        ProgressDialog.showDialog(activity!!)
        Talent212_218225.talent(flag, nowPage)
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}