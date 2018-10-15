package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.fragment.DynamicFragment
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclDetailsModel
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclModel
import com.lixin.amuseadjacent.app.ui.find.model.FindModel
import com.lixin.amuseadjacent.app.ui.find.request.DynamicList_219
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
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
    private var topImgDetailUrlState = ""//图片点击详情链接状态 0 有效 1无效

    private var fragment0: DynamicFragment? = null
    private var fragment1: DynamicFragment? = null

    private var i = 0

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
            bundle.putString("flag", "0")
            val intent = Intent(this, DynamicReleaseActivity::class.java)
            intent.putExtras(bundle)
            startActivityForResult(intent, 1)
        }

        val tabList = ArrayList<String>()
        tabList.add("全部")
        tabList.add("关注")
        val list = ArrayList<Fragment>()

        fragment0 = DynamicFragment()
        val bundle = Bundle()
        bundle.putInt("flag", 0)
        fragment0!!.arguments = bundle
        list.add(fragment0!!)

        fragment1 = DynamicFragment()
        val bundle1 = Bundle()
        bundle1.putInt("flag", 1)
        fragment1!!.arguments = bundle1
        list.add(fragment1!!)

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)

        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                i = tab!!.position
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
            }

        })

        banner.setOnBannerListener { i ->
            if (topImgDetailUrlState == "1") {
                return@setOnBannerListener
            }
            val bundle = Bundle()
            bundle.putString("title", "")
            bundle.putString("url", bannerUrl)
            MyApplication.openActivity(this, WebViewActivity::class.java, bundle)
        }
        ProgressDialog.showDialog(this)
        DynamicList_219.dynamic("0", 0, 1,null)
    }


    @Subscribe
    fun onEvent(model: DynamiclModel) {
        bannerUrl = model.topImgDetailUrl
        topImgDetailUrlState = model.topImgDetailUrlState
        imageList.clear()
        imageList.add(model.topImgUrl)
        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 1) {
            if (i == 0) {
                fragment0!!.refresh(i, null, -1)
            } else {
                fragment1!!.refresh(i, null, -1)
            }
        } else if (requestCode == 3) {
            val model = data.getSerializableExtra("model") as DynamiclDetailsModel
            val position = data.getIntExtra("position", -1)
            if (i == 0) {
                fragment0!!.refresh(i, model, position)
            } else {
                fragment1!!.refresh(i, model, position)
            }
        }
    }

    override fun onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return
        }
        super.onBackPressed()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}