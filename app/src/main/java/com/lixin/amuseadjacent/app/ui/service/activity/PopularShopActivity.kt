package com.lixin.amuseadjacent.app.ui.service.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.message.activity.SearchActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.ui.service.adapter.PopularShopAdapter
import com.lixin.amuseadjacent.app.ui.service.model.PopularShopModel
import com.lixin.amuseadjacent.app.ui.service.request.PopularShop_39310
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 小区店铺
 * Created by Slingge on 2018/8/31
 */
class PopularShopActivity : BaseActivity() {

    private var shopAdapter: PopularShopAdapter? = null
    private var shopList = ArrayList<PopularShopModel.dataModel>()
    private var bannerList = ArrayList<PopularShopModel.bannerModel>()

    private var banner: Banner? = null

    private var search = ""//搜索内容
    private var bannerUrl = ""//搜索内容

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("小区店铺")
        StatusBarWhiteColor()

        iv_right.visibility = View.VISIBLE
        iv_right.setImageResource(R.drawable.ic_search1)
        iv_right.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putInt("flag", 303)
            MyApplication.openActivityForResult(this, SearchActivity::class.java, bundle, 0)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        val headerView = LayoutInflater.from(this).inflate(R.layout.include_banner, null, false)//头布局
        headerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        xrecyclerview.layoutManager = linearLayoutManager
        xrecyclerview.addHeaderView(headerView)
        xrecyclerview.setLoadingMoreEnabled(false)

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
            }

            override fun onRefresh() {
                search = ""
                PopularShop_39310.shop(search)
            }
        })

        xrecyclerview.addOnItemTouchListener(object : RecyclerItemTouchListener(xrecyclerview) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition - 2
                if (i < 0 || i >= shopList.size) {
                    return
                }
                val bundle = Bundle()
                bundle.putString("id", shopList[i].shopId)
                MyApplication.openActivity(this@PopularShopActivity, PopularShopDetailsActivity::class.java, bundle)
            }
        })

        banner = headerView.findViewById(R.id.banner)
        banner!!.setOnBannerListener { i ->
            val bundle = Bundle()
            bundle.putString("title", "")
            bundle.putString("url", bannerList[i].topImgDetailUrl)
            MyApplication.openActivity(this, WebViewActivity::class.java, bundle)
        }

        ProgressDialog.showDialog(this)
        PopularShop_39310.shop(search)
    }

    @Subscribe
    fun onEvent(model: PopularShopModel) {
        val imageList = ArrayList<String>()
        bannerList = model.bannerList
        for (i in 0 until bannerList.size) {
            imageList.add(bannerList[i].topImgUrl)
        }
        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()

        shopList = model.dataList
        shopAdapter = PopularShopAdapter(this, shopList)
        xrecyclerview.adapter = shopAdapter
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        shopAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        search = data.getStringExtra("search")
        ProgressDialog.showDialog(this)
        PopularShop_39310.shop(search)
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}