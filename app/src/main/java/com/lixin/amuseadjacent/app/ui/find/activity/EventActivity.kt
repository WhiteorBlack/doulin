package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.EventAdapter
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.include_banner.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.util.ArrayList

/**
 * 活动
 * Created by Slingge on 2018/8/25.
 */
class EventActivity : BaseActivity() {

    private var eventAdapter: EventAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("活动")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "创建活动"
        tv_right.setOnClickListener { v ->
            MyApplication.openActivity(this, EventReleaseActivity::class.java)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_event.layoutManager = linearLayoutManager

        rv_event.isFocusable = false

        eventAdapter = EventAdapter(this)
        rv_event.adapter = eventAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_event.layoutAnimation = controller
        eventAdapter!!.notifyDataSetChanged()
        rv_event.scheduleLayoutAnimation()


        val imageList = ArrayList<String>()
        imageList.add("http://img3.imgtn.bdimg.com/it/u=1938931313,3944636906&fm=26&gp=0.jpg")
        imageList.add("http://img3.imgtn.bdimg.com/it/u=2705115696,2812871630&fm=214&gp=0.jpg")
        imageList.add("http://i0.hdslb.com/bfs/archive/f82fd6472f0bb071deee6f3defd0dc665dab330d.jpg")
        imageList.add("http://2e.zol-img.com.cn/product/122_800x600/688/ce0nEVHbsjooc.jpg")
        imageList.add("http://img.newyx.net/photo/201604/08/44213c3996.jpg")

        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())

                .start()
    }


}