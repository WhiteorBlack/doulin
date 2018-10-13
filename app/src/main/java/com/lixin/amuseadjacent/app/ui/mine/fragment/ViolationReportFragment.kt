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
import com.lixin.amuseadjacent.app.ui.mine.adapter.ViolationReportAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.IrregularitiesModel
import com.lixin.amuseadjacent.app.ui.mine.model.ReportModel
import com.lixin.amuseadjacent.app.ui.mine.request.ViolationReport_129130
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 违规举报
 * Created by Slingge on 2018/9/4
 */
class ViolationReportFragment : BaseFragment() {

    private var linearLayoutManager: LinearLayoutManager? = null
    private var violationReportAdapter: ViolationReportAdapter? = null

    private var violationList = ArrayList<IrregularitiesModel.irreguModel>()
    private var reportList = ArrayList<ReportModel.irreguModel>()

    private var flag = -1//0违规，1举报

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
        if (violationList.isNotEmpty()) {
            violationList.clear()
        }
        if (reportList.isNotEmpty()) {
            reportList.clear()
        }
        include.visibility = View.GONE

        xrecyclerview.layoutManager = linearLayoutManager

        violationReportAdapter = ViolationReportAdapter(activity!!, flag, violationList, reportList)
        xrecyclerview.adapter = violationReportAdapter

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (violationList.isNotEmpty()) {
                    violationList.clear()
                }
                if (reportList.isNotEmpty()) {
                    reportList.clear()
                }
                violationReportAdapter!!.notifyDataSetChanged()
                if (flag == 0) {
                    ViolationReport_129130.irregularities(nowPage)
                } else {
                    ViolationReport_129130.report(nowPage)
                }
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage > totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                if (flag == 0) {
                    ViolationReport_129130.irregularities(nowPage)
                } else {
                    ViolationReport_129130.report(nowPage)
                }
            }
        })


    }

    private fun init() {
        flag = arguments!!.getInt("flag")
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
    }


    override fun loadData() {
        ProgressDialog.showDialog(activity!!)
        ToastUtil.showToast(flag.toString())
        if (flag == 0) {
            ViolationReport_129130.irregularities(nowPage)
        } else {
            ViolationReport_129130.report(nowPage)
        }
    }

    //违规
    @Subscribe
    fun onEvent(model: IrregularitiesModel) {
        totalPage = model.totalPage

        violationList.addAll(model.dataList)
        if (totalPage <= 1) {
            xrecyclerview.noMoreLoading()
        }
        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }
        if (nowPage == 1) {
            val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
            xrecyclerview.layoutAnimation = controller
            violationReportAdapter!!.notifyDataSetChanged()
            xrecyclerview.scheduleLayoutAnimation()
        } else {
            violationReportAdapter!!.notifyDataSetChanged()
        }

    }

    //举报
    @Subscribe
    fun onEvent(model: ReportModel) {
        totalPage = model.totalPage

        reportList.addAll(model.dataList)

        if (totalPage <= 1) {
            xrecyclerview.noMoreLoading()
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }
        if (nowPage == 1) {
            val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
            xrecyclerview.layoutAnimation = controller
            violationReportAdapter!!.notifyDataSetChanged()
            xrecyclerview.scheduleLayoutAnimation()
        } else {
            violationReportAdapter!!.notifyDataSetChanged()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}