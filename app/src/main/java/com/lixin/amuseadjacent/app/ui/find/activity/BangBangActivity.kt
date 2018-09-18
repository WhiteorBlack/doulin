package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.DynamicAdapter
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclModel
import com.lixin.amuseadjacent.app.ui.find.model.FindModel
import com.lixin.amuseadjacent.app.ui.find.request.DynamicList_219
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.include_banner.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 帮帮
 * Created by Slingge on 2018/8/30
 */
class BangBangActivity : BaseActivity() {

    private var bangAdapter: DynamicAdapter? = null
    private var dynaList = ArrayList<FindModel.dynamicModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    private val imageList = ArrayList<String>()
    private var bannerUrl = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("帮帮")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "发布"
        tv_right.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("flag", "1")
            MyApplication.openActivityForResult(this, DynamicReleaseActivity::class.java, bundle, 1)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_event.layoutManager = linearLayoutManager

        rv_event.isFocusable = false

        bangAdapter = DynamicAdapter(this,"1", dynaList)
        rv_event.adapter = bangAdapter

        rv_event.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                if (dynaList.isNotEmpty()) {
                    dynaList.clear()
                    bangAdapter!!.notifyDataSetChanged()
                }
                onRefresh = 1
                DynamicList_219.dynamic("1", 0, nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    rv_event.noMoreLoading()
                    return
                }
                onRefresh = 2
                DynamicList_219.dynamic("1", 0, nowPage)
            }
        })

        banner.setOnBannerListener { i ->
            val bundle = Bundle()
            bundle.putString("title", "")
            bundle.putString("url", bannerUrl)
            MyApplication.openActivity(this, WebViewActivity::class.java, bundle)
        }
        ProgressDialog.showDialog(this)
        DynamicList_219.dynamic("1", 0, nowPage)
    }

    @Subscribe
    fun onEvent(model: DynamiclModel) {
        if (imageList.isEmpty()) {
            bannerUrl = model.topImgUrl
            imageList.add(bannerUrl)
            banner!!.setImages(imageList)
                    .setImageLoader(GlideImageLoader())
                    .start()
        }

        dynaList.addAll(model.dataList)

        totalPage = model.totalPage

        if (totalPage <= 1) {
            if (dynaList.isNotEmpty()) {
                rv_event.noMoreLoading()
            }
        }

        if (onRefresh == 1) {
            rv_event.refreshComplete()
        } else if (onRefresh == 2) {
            rv_event.loadMoreComplete()
        }

        if (nowPage == 1) {
            val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
            rv_event.layoutAnimation = controller
            bangAdapter!!.notifyDataSetChanged()
            rv_event.scheduleLayoutAnimation()
        } else {
            bangAdapter!!.notifyDataSetChanged()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1) {
            if (dynaList.isNotEmpty()) {
                dynaList.clear()
                bangAdapter!!.notifyDataSetChanged()
            }
            ProgressDialog.showDialog(this)
            DynamicList_219.dynamic("1", 0, nowPage)
        }
    }

    override fun onPause() {
        super.onPause()
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}