package com.lixin.amuseadjacent.app.ui.find.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicAdapter
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 达人列表
 * Created by Slingge on 2018/8/21
 */
class DynamicFragment : BaseFragment() {

    private var flag = -1

    private var linearLayoutManager: LinearLayoutManager? = null

    private var dynamicAdapter: DynamicAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.xrecyclerview, container, false)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        include.visibility = View.GONE

        xrecyclerview.layoutManager = linearLayoutManager

        dynamicAdapter = DynamicAdapter(activity!!,flag)
        xrecyclerview.adapter = dynamicAdapter

        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        dynamicAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()

    }

    private fun init() {
        flag = arguments!!.getInt("flag", -1)

        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
    }

    override fun loadData() {
        ToastUtil.showToast(flag.toString())
    }


}