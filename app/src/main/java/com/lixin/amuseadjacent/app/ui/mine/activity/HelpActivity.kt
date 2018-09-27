package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.ProblemAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.HelpModel
import com.lixin.amuseadjacent.app.ui.mine.request.Help_128
import kotlinx.android.synthetic.main.activity_help.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import android.content.Intent
import android.net.Uri
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.netease.nim.uikit.api.NimUIKit


/**
 * 帮助
 * Created by Slingge on 2018/9/3
 */
class HelpActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("帮助")
        StatusBarWhiteColor()

        val linelayout = LinearLayoutManager(this)
        linelayout.orientation = LinearLayoutManager.VERTICAL
        rv_problem.isFocusable = false
        rv_problem.layoutManager = linelayout

        tv_phone.setOnClickListener(this)

        tv_report.setOnClickListener(this)
        tv_service.setOnClickListener(this)

        ProgressDialog.showDialog(this)
        Help_128.help()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_report -> {//违规举报
                MyApplication.openActivity(this, ViolationReportActivity::class.java)
            }
            R.id.tv_phone -> {//
                val intent = Intent(Intent.ACTION_DIAL)
                val data = Uri.parse("tel:" + AbStrUtil.tvTostr(tv_phone))
                intent.data = data
                startActivity(intent)
            }
            R.id.tv_service -> {
                NimUIKit.startP2PSession(this@HelpActivity, "admin")
            }
        }
    }


    @Subscribe
    fun onEvent(model: HelpModel) {

        tv_phone.text = model.phone

        val problemAdapter = ProblemAdapter(this, model.dataList)
        rv_problem.adapter = problemAdapter

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}