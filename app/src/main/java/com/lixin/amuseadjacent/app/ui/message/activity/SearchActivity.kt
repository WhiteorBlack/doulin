package com.lixin.amuseadjacent.app.ui.message.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import kotlinx.android.synthetic.main.activity_search.*
import com.lixin.amuseadjacent.app.ui.message.adapter.SearchChatAdapter
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallbackWrapper
import com.netease.nimlib.sdk.ResponseCode
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.model.RecentContact
import com.netease.nimlib.sdk.uinfo.model.UserInfo

/**
 * Created by Slingge on 2018/8/16
 */
class SearchActivity : BaseActivity() {

    private var flag = 0//303搜索小区店铺
    private var recentList: ArrayList<RecentContact> = ArrayList()
    private var adapter: SearchChatAdapter? = null
    private var userInfo:UserInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        init()
        initRecycler_view()
    }

    private fun initRecycler_view(){
        adapter = SearchChatAdapter(this)
        recycle_view.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        recycle_view.adapter = adapter
        adapter!!.setOnClickListener(object: SearchChatAdapter.OnItemClickListener{
            override fun itemClick(account: String) {
                NimUIKit.startP2PSession(this@SearchActivity, account)
            }
        })
    }

    private fun init() {
        StatusBarWhiteColor()
        flag = intent.getIntExtra("flag", 0)

        for (i in 0 until 10) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_textview, flowLayout, false) as TextView
            tv.text = "洛克贝尔" + i.toString()
            tv.setTextColor(Color.parseColor("#666666"))
            tv.setOnClickListener {

            }
            flowLayout.addView(tv)
        }
        tv_cancel.setOnClickListener { v ->
            finish()
        }


        et_search.setOnEditorActionListener(TextView.OnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH || p1 == EditorInfo.IME_ACTION_UNSPECIFIED) {
                val keytag = AbStrUtil.etTostr(et_search)
                if (TextUtils.isEmpty(keytag)) {
                    return@OnEditorActionListener true
                }
                if (flag == 303) {
                    val intent = Intent()
                    intent.putExtra("search", keytag)
                    setResult(0, intent)
                    finish()
                }else {
                    // 查询最近联系人列表数据
                    ProgressDialog.showDialog(this)
                    recentList.clear()
                    userInfo = null
                    NIMClient.getService(MsgService::class.java).queryRecentContacts().setCallback(object : RequestCallbackWrapper<List<RecentContact>?>() {
                        override fun onResult(code: Int, recents: List<RecentContact>?, exception: Throwable?) {
                            if (code != ResponseCode.RES_SUCCESS.toInt() || recents == null) {
                                ProgressDialog.dissDialog()
                                return
                            }
                            for (i in 0 until recents.size){
                                userInfo = NimUIKit.getUserInfoProvider().getUserInfo(recents[i].contactId)  //根据账号获取用户信息
                                if (userInfo != null && !TextUtils.isEmpty(userInfo!!.name) && keytag == userInfo!!.name){
                                    recentList.add(recents[i])
                                    break
                                }
                            }

                            if (recentList.isEmpty()){
                                ToastUtil.showToast("此聊天不存在")
                            }else{
                                adapter!!.refresh(recentList,userInfo!!)
                            }
                            ProgressDialog.dissDialog()
                        }

                    })
                }

                return@OnEditorActionListener true
            }
            false
        })
    }

}