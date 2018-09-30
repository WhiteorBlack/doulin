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
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.message.activity.*
import com.lixin.amuseadjacent.app.ui.message.adapter.MyMsgAdapter
import com.lixin.amuseadjacent.app.ui.message.model.MsgListModel
import com.lixin.amuseadjacent.app.ui.message.request.MsgList_21
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_message.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 消息首页
 * Created by Slingge on 2018/8/15
 */
class MessageFragment : BaseFragment(), View.OnClickListener {

    private var msgAdaptation: MyMsgAdapter? = null
    private var msgList = ArrayList<MsgListModel.msgModel>()

    private var flag = -1//进入0系统消息 1订单信息 2评论信息 3点赞信息，返回后清零消息数

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_message, container, false)
        EventBus.getDefault().register(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_title.text = StaticUtil.communityName

        iv_mail.setOnClickListener(this)
        iv_add.setOnClickListener(this)
        fl_search.setOnClickListener(this)

        if (Build.VERSION.SDK_INT > 19) {
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(activity, view_staus)
            StatusBarUtil.setColorNoTranslucent(activity, resources.getColor(R.color.white))
            StatusBarBlackWordUtil.StatusBarLightMode(activity)
        }

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_msg.layoutManager = linearLayoutManager
        msgAdaptation = MyMsgAdapter(activity!!, msgList)
        rv_msg.adapter = msgAdaptation

        rv_msg.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_msg) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val position = vh!!.adapterPosition
                if (position < 0 || position >= msgList.size) {
                    return
                }
                flag = msgList[position].type.toInt()
                if (msgList[position].type == "0") {//0系统消息 1订单信息 2评论信息 3点赞信息
                    val bundle = Bundle()
                    bundle.putString("type", "0")
                    MyApplication.openActivity(activity, OfficialNewsActivity::class.java)
                } else if (msgList[position].type == "1") {
                    val bundle = Bundle()
                    bundle.putString("type", "1")
                    MyApplication.openActivity(activity, OrderNewsActivity::class.java)
                } else if (msgList[position].type == "2") {
                    val bundle = Bundle()
                    bundle.putString("type", "2")
                    MyApplication.openActivity(activity, CommentNewsActivity::class.java, bundle)
                } else if (msgList[position].type == "3") {
                    val bundle = Bundle()
                    bundle.putString("type", "3")
                    MyApplication.openActivity(activity, CommentNewsActivity::class.java, bundle)
                }
            }
        })

        if (msgList.isNotEmpty()) {
            msgList.clear()
            msgAdaptation!!.notifyDataSetChanged()
        }
        ProgressDialog.showDialog(activity!!)
        MsgList_21.msgList()
    }


    @Subscribe
    fun onEvent(model: MsgListModel) {

        msgList.addAll(model.dataList)
        msgAdaptation!!.notifyDataSetChanged()
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


    override fun onStart() {
        super.onStart()
        if (flag == -1) {
            return
        }
        for (i in 0 until msgList.size) {
            if (msgList[i].type.toInt() == flag) {
                msgList[i].messagenum = "0"
                break
            }
        }
        msgAdaptation!!.notifyDataSetChanged()
        flag = -1
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}