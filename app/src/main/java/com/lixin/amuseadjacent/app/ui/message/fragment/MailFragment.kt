package com.lixin.amuseadjacent.app.ui.message.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.message.adapter.MailAdapter
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_mail.*

/**
 * 通讯录
 * flag 0好友，1关注，2粉丝
 * Created by Slingge on 2018/8/16
 */
class MailFragment : BaseFragment() {
    override fun loadData() {

    }

    private var flag = -1

    private var mailAdapter: MailAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mail, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_mail.layoutManager = linearLayoutManager
        init()
    }

    private fun init() {
        flag = arguments!!.getInt("flag")

        ToastUtil.showToast(flag.toString())

        mailAdapter = MailAdapter(activity!!,flag)
        rv_mail.adapter = mailAdapter

        val controller = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation_from_bottom)
        rv_mail.layoutAnimation = controller
        mailAdapter!!.notifyDataSetChanged()
        rv_mail.scheduleLayoutAnimation()
    }



}