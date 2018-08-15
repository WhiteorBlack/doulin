package com.lixin.amuseadjacent.app.ui.message.fragment

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.message.activity.MailActivity
import com.lixin.amuseadjacent.app.ui.message.adapter.MyMsgAdapter
import com.lixin.amuseadjacent.app.ui.message.model.MyMsgListModel
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * Created by Slingge on 2018/8/15
 */
class MessageFragment : BaseFragment(), View.OnClickListener {


    private var msgAdaptation: MyMsgAdapter? = null
    private var msgList = ArrayList<MyMsgListModel.dataModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_message, container, false)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        iv_mail.setOnClickListener(this)

        if (Build.VERSION.SDK_INT > 19) {
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(activity, view_staus)
            StatusBarUtil.transparentStatusBar(activity)
            StatusBarBlackWordUtil.StatusBarLightMode(activity)
        }

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_msg.layoutManager = linearLayoutManager
        msgAdaptation = MyMsgAdapter(activity!!, msgList)
        rv_msg.adapter = msgAdaptation
    }


    override fun loadData() {

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_mail -> {
                MyApplication.openActivity(activity, MailActivity::class.java)
            }
        }
    }

}