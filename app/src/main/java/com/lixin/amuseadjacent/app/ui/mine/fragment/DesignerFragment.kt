package com.lixin.amuseadjacent.app.ui.mine.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.mine.adapter.DynamicAdapter
import com.lixin.amuseadjacent.app.ui.mine.adapter.ExperienceAdapter
import kotlinx.android.synthetic.main.fragment_designer.*
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 个人主页 达人
 * Created by Slingge on 2018/8/18
 */
class DesignerFragment : BaseFragment() {

    private var linearLayoutManager: LinearLayoutManager? = null

    private var experienceAdapter: ExperienceAdapter? = null//达人经历

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_designer, container, false)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_experience.layoutManager = linearLayoutManager

        experienceAdapter = ExperienceAdapter(activity!!)
        rv_experience.adapter = experienceAdapter

        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        rv_experience.layoutAnimation = controller
        experienceAdapter!!.notifyDataSetChanged()
        rv_experience.scheduleLayoutAnimation()
    }


    private fun init() {
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL

    }


    override fun loadData() {

    }

}