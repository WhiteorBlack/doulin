package com.lixin.amuseadjacent.app.ui.service.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.service.adapter.LaundryAdapter
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_laundry.*

/**
 * 洗衣洗鞋
 * Created by Slingge on 2018/8/31
 */
class LaundryFrament : BaseFragment() {

    private var flag = -1


    private var laundryAdapter: LaundryAdapter?=null


    private var gridLayoutManager: GridLayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_laundry, container, false)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_clothes.layoutManager=gridLayoutManager

        laundryAdapter=LaundryAdapter(activity!!)
        rv_clothes.adapter=laundryAdapter

        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        rv_clothes.layoutAnimation = controller
        laundryAdapter!!.notifyDataSetChanged()
        rv_clothes.scheduleLayoutAnimation()
    }

    private fun init() {
        flag = arguments!!.getInt("flag", -1)

        gridLayoutManager= GridLayoutManager(activity!!,3)
    }


    override fun loadData() {
        ToastUtil.showToast(flag.toString())

    }


}