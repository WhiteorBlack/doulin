package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.CommunityDialog
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.entrance.model.CommunityModel
import com.lixin.amuseadjacent.app.ui.entrance.model.UnityModel
import com.lixin.amuseadjacent.app.ui.entrance.request.Community_17
import com.lixin.amuseadjacent.app.ui.mine.adapter.RuleAdapter
import com.lixin.amuseadjacent.app.ui.mine.request.CommunityMessage_127
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_replace_community.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 更换社区
 * Created by Slingge on 2018/9/4
 */
class ReplaceCommunityActivity : BaseActivity(), View.OnClickListener {

    private var unitModel: UnityModel.unitModel? = null

    private var ruleAdapter: RuleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_replace_community)
        EventBus.getDefault().register(this)
        init()
    }

    private fun init() {
        inittitle("更换社区")
        StatusBarWhiteColor()

        val linelayout = LinearLayoutManager(this)
        linelayout.orientation = LinearLayoutManager.VERTICAL

        rv_rule.layoutManager = linelayout
        ruleAdapter = RuleAdapter(this)
        rv_rule.adapter = ruleAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_rule.layoutAnimation = controller
        ruleAdapter!!.notifyDataSetChanged()
        rv_rule.scheduleLayoutAnimation()

        sp_bank.setOnClickListener(this)
        tv_submission.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.sp_bank -> {
//                ProgressDialog.showDialog(this)
                Community_17.getCommunity(this)
            }
            R.id.tv_submission -> {
                if (unitModel == null) {
                    ToastUtil.showToast("请选择社区信息")
                    return
                }
                val content = AbStrUtil.etTostr(et_reason)
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast("请输入更换原因")
                    return
                }
//                ProgressDialog.showDialog(this)
                CommunityMessage_127.community(this, content, unitModel!!)
            }
        }
    }

    //选择社区
    @Subscribe
    fun onEvent(model: CommunityModel) {
        CommunityDialog.communityDialog(this, model.dataList)
    }

    //选择社区
    @Subscribe
    fun onEvent(model: UnityModel.unitModel) {
        sp_bank.text = model.communityName + model.unitName + model.num
        unitModel = model
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}