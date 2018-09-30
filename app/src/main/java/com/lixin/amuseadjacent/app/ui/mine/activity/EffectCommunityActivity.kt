package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicReleaseActivity
import com.lixin.amuseadjacent.app.ui.find.activity.EventReleaseActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.EffectAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.EffectModel
import com.lixin.amuseadjacent.app.ui.mine.request.Effect_312166
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_community_effect.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 社区影响力
 * Created by Slingge on 2018/9/2.
 */
class EffectCommunityActivity : BaseActivity() {

    private var effectAdapter: EffectAdapter? = null
    private var effectList = ArrayList<EffectModel.dataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_effect)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("社区影响力")
        StatusBarWhiteColor()

        tv_right.visibility = View.VISIBLE
        tv_right.text = "影响力规则"
        tv_right.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putInt("flag", 6)
            MyApplication.openActivity(this, WebViewActivity::class.java, bundle)
        }

        tv_eeffect.setOnClickListener { v ->
            MyApplication.openActivity(this, EffectDetailsActivity::class.java)
        }

        val linelayout = LinearLayoutManager(this)
        linelayout.orientation = LinearLayoutManager.VERTICAL

        rv_effect.layoutManager = linelayout
        rv_effect.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_effect) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                ToastUtil.showToast(i.toString())
                if (i < 0 || i >= effectList.size) {
                    return
                }
                when (effectList[i].taskId) {//1每日签到 ,2邀请好友 3发布活动  4发布动态 5完善资料 6认证用户
                    "1" -> {
                        MyApplication.openActivity(this@EffectCommunityActivity, QianDaoActivity::class.java)
                    }
                    "2" -> {
                        MyApplication.openActivity(this@EffectCommunityActivity, InvitingFriendsAcivity::class.java)
                    }
                    "3" -> {
                        MyApplication.openActivity(this@EffectCommunityActivity, EventReleaseActivity::class.java)
                    }
                    "4" -> {
                        val bundle = Bundle()
                        bundle.putString("flag", "0")
                        MyApplication.openActivity(this@EffectCommunityActivity, DynamicReleaseActivity::class.java, bundle)
                    }
                    "5" -> {
                        MyApplication.openActivity(this@EffectCommunityActivity, PersonalDataActivity::class.java)
                    }
                    "6" -> {
                        if (effectList[i].isFinishTask == "1") {
                            ToastUtil.showToast("已认证")
                            return
                        }
                        MyApplication.openActivity(this@EffectCommunityActivity, RealNameAuthenticationActivity::class.java)
                    }
                }
            }
        })


        ProgressDialog.showDialog(this)
        Effect_312166.effect()
    }

    @Subscribe
    fun onEvent(model: EffectModel) {
        tv_eeffect.text = model.allEffectNum
        tv_already.text = "今日已获得" + model.dayEffectNum + "影响力"

        effectList = model.dataList
        effectAdapter = EffectAdapter(this, model.dataList)
        rv_effect.adapter = effectAdapter
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_effect.layoutAnimation = controller
        effectAdapter!!.notifyDataSetChanged()
        rv_effect.scheduleLayoutAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}