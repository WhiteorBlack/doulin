package com.lixin.amuseadjacent.app.ui.find.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.find.adapter.RedManAdapter
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_redman_list.*

/**
 * 红人榜
 * Created by Slingge on 2018/8/30
 */
class RedManFragment : BaseFragment() {

    private var flag = -1

    private var linearLayoutManager: LinearLayoutManager? = null
    private var redManAdapter: RedManAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_redman_list, container, false)
        init()
        return view
    }


    override fun loadData() {
        ToastUtil.showToast(flag.toString())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_radman.layoutManager = linearLayoutManager
        rv_radman.setPullRefreshEnabled(false)
        rv_radman.isFocusable = false
        redManAdapter = RedManAdapter(activity!!)
        rv_radman.adapter = redManAdapter

        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        rv_radman.layoutAnimation = controller
        redManAdapter!!.notifyDataSetChanged()
        rv_radman.scheduleLayoutAnimation()
    }

    private fun init() {

        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL

    }


}