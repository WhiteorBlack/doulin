package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.EffectAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.EffectModel
import com.lixin.amuseadjacent.app.ui.mine.request.Effect_312166
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

        ProgressDialog.showDialog(this)
        Effect_312166.effect()
    }

    @Subscribe
    fun onEvent(model: EffectModel) {
        tv_eeffect.text = model.allEffectNum
        tv_already.text = "今日已获得" + model.dayEffectNum + "点影响力"

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