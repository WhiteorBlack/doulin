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
import com.lixin.amuseadjacent.app.ui.find.adapter.EventAdapter
import com.lixin.amuseadjacent.app.ui.find.model.EventModel
import com.lixin.amuseadjacent.app.ui.find.request.Event_221222223224
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.include_banner.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 活动
 * Created by Slingge on 2018/8/25.
 */
class EventActivity : BaseActivity() {

    private var eventAdapter: EventAdapter? = null
    private var eventList = ArrayList<EventModel.dataModel>()

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
        inittitle("活动")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "创建活动"
        tv_right.setOnClickListener { v ->
            MyApplication.openActivityForResult(this, EventReleaseActivity::class.java, 0)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_event.layoutManager = linearLayoutManager

        rv_event.isFocusable = false

        eventAdapter = EventAdapter(this, eventList)
        rv_event.adapter = eventAdapter

        rv_event.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                if (eventList.isNotEmpty()) {
                    eventList.clear()
                    eventAdapter!!.notifyDataSetChanged()
                }
                onRefresh = 1
                Event_221222223224.EventList(nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    rv_event.noMoreLoading()
                    return
                }
                onRefresh = 2
                Event_221222223224.EventList(nowPage)
            }
        })


        banner.setOnBannerListener { i ->
            val bundle = Bundle()
            bundle.putString("title", "")
            bundle.putString("url", bannerUrl)
            MyApplication.openActivity(this, WebViewActivity::class.java, bundle)
        }
        ProgressDialog.showDialog(this)
        Event_221222223224.EventList(nowPage)
    }

    @Subscribe
    fun onEvent(model: EventModel) {
        if (imageList.isEmpty()) {
            bannerUrl = model.topImgUrl
            imageList.add(bannerUrl)
            banner!!.setImages(imageList)
                    .setImageLoader(GlideImageLoader())
                    .start()
        }

        eventList.addAll(model.dataList)

        totalPage = model.totalPage

        if (onRefresh == 1) {
            rv_event.refreshComplete()
        } else if (onRefresh == 2) {
            rv_event.loadMoreComplete()
        }

        if (nowPage == 1) {
            val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
            rv_event.layoutAnimation = controller
            eventAdapter!!.notifyDataSetChanged()
            rv_event.scheduleLayoutAnimation()
        } else {
            eventAdapter!!.notifyDataSetChanged()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data==null){
            return
        }
        if(requestCode==0){
            if (eventList.isNotEmpty()) {
                eventList.clear()
                eventAdapter!!.notifyDataSetChanged()
            }
            nowPage = 1
            Event_221222223224.EventList(nowPage)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}