package com.lixin.amuseadjacent.app.ui.message.fragment

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.message.activity.*
import com.lixin.amuseadjacent.app.ui.message.adapter.MyMsgAdapter
import com.lixin.amuseadjacent.app.ui.message.model.MyMsgListModel
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * 消息首页
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
        iv_add.setOnClickListener(this)
        fl_search.setOnClickListener(this)

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

        rv_msg.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_msg) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val position = vh!!.adapterPosition - 1
                ToastUtil.showToast(position.toString())
                if (position < 0) {
                    return
                }
                if (position % 2 == 0) {
                    MyApplication.openActivity(activity, OfficialNewsActivity::class.java)
                }else if(position % 3 == 0){
                    MyApplication.openActivity(activity, CommentNewsActivity::class.java)
                }else{
                    MyApplication.openActivity(activity, OrderNewsActivity::class.java)
                }
            }
        })
    }


    override fun loadData() {

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_mail -> {
                MyApplication.openActivity(activity, MailActivity::class.java)
            }
            R.id.iv_add -> {
                MyApplication.openActivity(activity, AddFriendsActivity::class.java)
            }
            R.id.fl_search -> {
                MyApplication.openActivity(activity, SearchActivity::class.java)
            }
        }
    }

}