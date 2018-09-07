package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.fragment.DynamicFragment
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_talent.*
import kotlinx.android.synthetic.main.include_banner.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.util.ArrayList

/**
 * Created by Slingge on 2018/8/22
 */
class DynamicActivity:BaseActivity(){

    val imageList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talent)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE
        inittitle("动态")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "发布"
        tv_right.setOnClickListener { v ->
           val  bundle=Bundle()
            bundle.putInt("flag",0)
            MyApplication.openActivity(this, DynamicReleaseActivity::class.java,bundle)
        }

        val tabList = ArrayList<String>()
        tabList.add("全部")
        tabList.add("关注")

        val list = ArrayList<Fragment>()
        for(i in 0..1){
            val fragment =  DynamicFragment()
            val bundle= Bundle()
            bundle.putInt("flag",i)
            fragment.arguments=bundle
            list.add(fragment)
        }

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)

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