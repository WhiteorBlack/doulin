package com.lixin.amuseadjacent.app.ui.mine.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.mine.adapter.ViolationReportAdapter
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 违规举报
 * Created by Slingge on 2018/9/4
 */
class ViolationReportFragment : BaseFragment() {

    private var linearLayoutManager: LinearLayoutManager? = null
    private var violationReportAdapter: ViolationReportAdapter? = null

    private var flag=-1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.xrecyclerview, container, false)
        init()
        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        include.visibility=View.GONE

        xrecyclerview.layoutManager = linearLayoutManager

        violationReportAdapter = ViolationReportAdapter(activity!!,flag)
        xrecyclerview.adapter = violationReportAdapter

        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        violationReportAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }

    private fun init() {
        flag=arguments!!.getInt("flag")
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
    }


    override fun loadData() {
        ToastUtil.showToast(flag.toString())
    }


}