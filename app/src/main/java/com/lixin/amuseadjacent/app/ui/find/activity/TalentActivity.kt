package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.view.View
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.fragment.TalentFragment
import com.lixin.amuseadjacent.app.ui.find.model.TalentModel
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.activity_talent.*
import kotlinx.android.synthetic.main.include_banner.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 达人
 * Created by Slingge on 2018/8/20
 */
class TalentActivity : BaseActivity() {

    private val imageList = ArrayList<String>()

    private var url = ""
    private var topImgDetailUrlState=""//图片点击详情链接状态 0 有效 1无效
    var type = "0"//0未申请达人 1达人审核中 2审核通过 3申请达人失败

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talent)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE
        inittitle("社区达人")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "达人认证"
        tv_right.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("type", type)
            MyApplication.openActivity(this, TalentAuthenticationActivity::class.java, bundle)
        }

        val tabList = ArrayList<String>()
        tabList.add("技能类")
        tabList.add("职业类")
        tabList.add("商业类")

        val list = ArrayList<Fragment>()
        for (i in 0..2) {
            val fragment = TalentFragment()
            val bundle = Bundle()
            bundle.putInt("flag", i)
            fragment.arguments = bundle
            list.add(fragment)
        }

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        tab.setupWithViewPager(viewPager)

        banner.setOnBannerListener { i ->
            if(topImgDetailUrlState=="1"){
                return@setOnBannerListener
            }
            val bundle = Bundle()
            bundle.putString("title", "")
            bundle.putString("url", url)
            MyApplication.openActivity(this, WebViewActivity::class.java, bundle)
        }

        appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset >= 0) {
                EventBus.getDefault().post(true)
            } else {
                EventBus.getDefault().post(false)
            }
        }

    }


    @Subscribe
    fun onEvent(model: TalentModel) {
        if (imageList.isNotEmpty()) {
            return
        }
        type = model.isApply
        url = model.topImgDetailUrl
        topImgDetailUrlState=model.topImgDetailUrlState
        imageList.clear()
        imageList.add(model.topImgUrl)
        banner.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}