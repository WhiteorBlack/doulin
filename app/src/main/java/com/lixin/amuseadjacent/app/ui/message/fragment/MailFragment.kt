package com.lixin.amuseadjacent.app.ui.message.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.message.adapter.MailAdapter
import com.lixin.amuseadjacent.app.ui.message.model.MailModel
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import kotlinx.android.synthetic.main.fragment_mail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.lixin.amuseadjacent.app.ui.message.callBack.MailCallBack
import com.lixin.amuseadjacent.app.util.AbStrUtil


/**
 * 通讯录
 * flag 0好友，1关注，2粉丝
 * Created by Slingge on 2018/8/16
 */
class MailFragment : BaseFragment() {

    private var flag = ""
    private var search = ""//搜索内容

    private var mailAdapter: MailAdapter? = null
    private var mailList = ArrayList<MailModel.addModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    private var mailCallBack: MailCallBack? = null//回调人数
    fun setMailCall(mailCallBack: MailCallBack) {
        this.mailCallBack = mailCallBack
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mail, container, false)
        EventBus.getDefault().register(this)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_mail.layoutManager = linearLayoutManager

        mailAdapter = MailAdapter(activity!!, flag.toInt(), mailList)
        rv_mail.adapter = mailAdapter

        val controller = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation_from_bottom)
        rv_mail.layoutAnimation = controller
        mailAdapter!!.notifyDataSetChanged()
        rv_mail.scheduleLayoutAnimation()

        rv_mail.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (mailList.isNotEmpty()) {
                    mailList.clear()
                    mailAdapter!!.notifyDataSetChanged()
                }
                Mail_138139.mail(flag, search, nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage > totalPage) {
                    rv_mail.noMoreLoading()
                    return
                }
                onRefresh = 2
                Mail_138139.mail(flag, search, nowPage)
            }
        })

        et_search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                search = AbStrUtil.etTostr(et_search)

                if (mailList.isNotEmpty()) {
                    mailList.clear()
                    mailAdapter!!.notifyDataSetChanged()
                }
                ProgressDialog.showDialog(activity!!)
                Mail_138139.mail(flag, search, nowPage)
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun init() {
        flag = arguments!!.getInt("flag").toString()
        ProgressDialog.showDialog(activity!!)
    }

    override fun onStart() {
        super.onStart()
        if (mailList.isNotEmpty()) {
            mailList.clear()
            mailAdapter!!.notifyDataSetChanged()
        }
        Mail_138139.mail(flag, "", nowPage)
    }

    @Subscribe
    fun onEvent(model: MailModel) {
        mailCallBack!!.num(model.allnum)

        totalPage = model.totalPage
        mailList.addAll(model.dataList)

        if (onRefresh == 1) {
            rv_mail.refreshComplete()
        } else if (onRefresh == 2) {
            rv_mail.loadMoreComplete()
        }
        mailAdapter!!.notifyDataSetChanged()
    }

    override fun loadData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}