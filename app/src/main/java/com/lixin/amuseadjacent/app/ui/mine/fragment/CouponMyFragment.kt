package com.lixin.amuseadjacent.app.ui.mine.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.mine.adapter.CouponMyAdapter
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 我的优惠券
 * Created by Slingge on 2018/9/3
 */
class CouponMyFragment : BaseFragment() {

    private var flag = -1//0未使用，1已使用，2已过期

    private var linearLayoutManager: LinearLayoutManager? = null
    private var couponAdapter: CouponMyAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.xrecyclerview, container, false)
        init()
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        include.visibility = View.GONE
        xrecyclerview.layoutManager = linearLayoutManager


    }


    private fun init() {
        flag = arguments!!.getInt("flag")

        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL

    }

    override fun loadData() {
        ToastUtil.showToast(flag.toString())

        couponAdapter = CouponMyAdapter(activity!!,flag)
        xrecyclerview.adapter = couponAdapter

        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        couponAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }

}