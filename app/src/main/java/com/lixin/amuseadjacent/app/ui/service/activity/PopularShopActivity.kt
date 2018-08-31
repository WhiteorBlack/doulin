package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.message.activity.SearchActivity
import com.lixin.amuseadjacent.app.ui.service.adapter.PopularShopAdapter
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import java.util.ArrayList

/**
 * 小区店铺
 * Created by Slingge on 2018/8/31
 */
class PopularShopActivity : BaseActivity() {

    private val imageList = ArrayList<String>()
    private var shopAdapter: PopularShopAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        inittitle("小区店铺")
        StatusBarWhiteColor()

        iv_right.visibility = View.VISIBLE
        iv_right.setImageResource(R.drawable.ic_search1)
        iv_right.setOnClickListener { v ->
            MyApplication.openActivity(this, SearchActivity::class.java)
        }


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        val headerView = LayoutInflater.from(this).inflate(R.layout.include_banner, null, false)//头布局
        headerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        xrecyclerview.layoutManager = linearLayoutManager
        xrecyclerview.addHeaderView(headerView)

        shopAdapter = PopularShopAdapter(this)
        xrecyclerview.adapter = shopAdapter

        xrecyclerview.addOnItemTouchListener(object : RecyclerItemTouchListener(xrecyclerview) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                ToastUtil.showToast(i.toString())
                if (i < 0) {
                    return
                }
                MyApplication.openActivity(this@PopularShopActivity, PopularShopDetailsActivity::class.java)
            }
        })

        imageList.add("http://img3.imgtn.bdimg.com/it/u=1938931313,3944636906&fm=26&gp=0.jpg")
        imageList.add("http://img3.imgtn.bdimg.com/it/u=2705115696,2812871630&fm=214&gp=0.jpg")
        imageList.add("http://i0.hdslb.com/bfs/archive/f82fd6472f0bb071deee6f3defd0dc665dab330d.jpg")
        imageList.add("http://2e.zol-img.com.cn/product/122_800x600/688/ce0nEVHbsjooc.jpg")
        imageList.add("http://img.newyx.net/photo/201604/08/44213c3996.jpg")

        val banner = headerView.findViewById<Banner>(R.id.banner)

        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .start()
    }


}