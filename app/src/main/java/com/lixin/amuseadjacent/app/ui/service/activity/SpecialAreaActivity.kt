package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.service.adapter.SpecialAdapter
import com.lixin.amuseadjacent.app.ui.service.model.ServiceModel
import com.lixin.amuseadjacent.app.ui.service.model.SpecialModel
import com.lixin.amuseadjacent.app.ui.service.request.Special_311
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.lixin.amuseadjacent.app.util.PreviewPhoto
import com.lxkj.huaihuatransit.app.util.ControlWidthHeight
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.include_banner.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 专区
 * Created by Slingge on 2018/8/30
 */
class SpecialAreaActivity : BaseActivity() {

    private var specialAdapter: SpecialAdapter? = null
    private var specialList = ArrayList<SpecialModel.dataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        val model: ServiceModel.dataModel = intent.getSerializableExtra("model") as ServiceModel.dataModel
        inittitle(model.optimizationName)

        iv_right.visibility = View.VISIBLE
        iv_right.setImageResource(R.drawable.ic_car)
        iv_right.setOnClickListener { v ->
            MyApplication.openActivity(this, ShopCarActivity::class.java)
        }

        val linearLayoutManager = GridLayoutManager(this, 3)
        rv_event.layoutManager = linearLayoutManager
        rv_event.isFocusable = false

        val imageList = ArrayList<String>()
        imageList.add(model.optimizationImgs)
        ControlWidthHeight.inputhigh(ControlWidthHeight.dip2px(this, 100), banner)
        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()
        banner!!.setOnBannerListener { i ->
            if (!TextUtils.isEmpty(model.optimizationImgs)) {
                PreviewPhoto.preview(this, model.optimizationImgs)
            }
        }

        ProgressDialog.showDialog(this)
        Special_311.special(model.optimizationId)
    }


    //优惠券
    @Subscribe
    fun onEvent(models: SpecialModel) {
        specialList = models.dataList
        specialAdapter = SpecialAdapter(this, specialList)
        rv_event.adapter = specialAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_event.layoutAnimation = controller
        specialAdapter!!.notifyDataSetChanged()
        rv_event.scheduleLayoutAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}