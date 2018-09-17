package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.fragment.DynamicFragment
import com.lixin.amuseadjacent.app.ui.find.model.FindModel
import com.lixin.amuseadjacent.app.ui.find.request.DynamicList_219
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import kotlinx.android.synthetic.main.activity_talent.*
import kotlinx.android.synthetic.main.include_banner.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * Created by Slingge on 2018/8/22
 */
class DynamicActivity : BaseActivity() {

    private val imageList = ArrayList<String>()

    private var bannerUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talent)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE
        inittitle("动态")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "发布"
        tv_right.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putInt("flag", 0)
            MyApplication.openActivity(this, DynamicReleaseActivity::class.java, bundle)
        }

        val tabList = ArrayList<String>()
        tabList.add("全部")
        tabList.add("关注")

        val list = ArrayList<Fragment>()
        for (i in 0..1) {
            val fragment = DynamicFragment()
            val bundle = Bundle()
            bundle.putInt("flag", i)
            fragment.arguments = bundle
            list.add(fragment)
        }

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)

        banner.setOnBannerListener { i ->
            val bundle = Bundle()
            bundle.putString("title", "")
            bundle.putString("url", bannerUrl)
            MyApplication.openActivity(this, WebViewActivity::class.java, bundle)
        }

        DynamicList_219.dynamic("0", "0", 1)
    }


    @Subscribe
    fun onEvent(model: FindModel) {
        bannerUrl = model.topImgUrl
        imageList.add(bannerUrl)
        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}