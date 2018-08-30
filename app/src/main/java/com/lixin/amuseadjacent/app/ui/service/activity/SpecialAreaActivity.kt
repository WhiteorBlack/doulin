package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.service.adapter.SpecialAdapter
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.util.ArrayList

/**
 * 专区
 * Created by Slingge on 2018/8/30
 */
class SpecialAreaActivity : BaseActivity() {

    private var specialAdapter: SpecialAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("专区")

        iv_right.visibility = View.VISIBLE
        iv_right.setImageResource(R.drawable.ic_car)
        iv_right.setOnClickListener{v->}

        val linearLayoutManager = GridLayoutManager(this, 3)
        rv_event.layoutManager = linearLayoutManager

        rv_event.isFocusable = false

        specialAdapter = SpecialAdapter(this)
        rv_event.adapter = specialAdapter

//        val lp = ConstraintLayout.LayoutParams(rv_event.layoutParams)
//        lp.setMargins(6, 0, 6, 0)
//        rv_event.layoutParams = lp

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_event.layoutAnimation = controller
        specialAdapter!!.notifyDataSetChanged()
        rv_event.scheduleLayoutAnimation()


        val imageList = ArrayList<String>()
        imageList.add("http://img3.imgtn.bdimg.com/it/u=1938931313,3944636906&fm=26&gp=0.jpg")
        imageList.add("http://img3.imgtn.bdimg.com/it/u=2705115696,2812871630&fm=214&gp=0.jpg")
        imageList.add("http://i0.hdslb.com/bfs/archive/f82fd6472f0bb071deee6f3defd0dc665dab330d.jpg")
        imageList.add("http://2e.zol-img.com.cn/product/122_800x600/688/ce0nEVHbsjooc.jpg")
        imageList.add("http://img.newyx.net/photo/201604/08/44213c3996.jpg")

        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .start()
    }


}