package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.activity.TalentAuthenticationActivity
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.service.adapter.LaundryMenuAdapter
import com.lixin.amuseadjacent.app.ui.service.fragment.LaundryFrament
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_laundry.*
import kotlinx.android.synthetic.main.include_banner.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.util.ArrayList

/**
 * 洗衣洗鞋
 * Created by Slingge on 2018/8/31
 */
class LaundryActivity : BaseActivity() {

    private var menuAdapter: LaundryMenuAdapter?=null
    private var linearLayoutManager: LinearLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laundry)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE
        inittitle("洗衣洗鞋")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "详情"
        tv_right.setOnClickListener { v ->
            MyApplication.openActivity(this, OfficialShopDetailsActivity::class.java)
        }


        val tabList = ArrayList<String>()
        tabList.add("洗衣")
        tabList.add("洗鞋")
        tabList.add("洗家纺")

        val list = ArrayList<Fragment>()
        for (i in 0..2) {
            val fragment = LaundryFrament()
            val bundle = Bundle()
            bundle.putInt("flag", i)
            fragment.arguments = bundle
            list.add(fragment)
        }

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)

        val imageList = ArrayList<String>()
        imageList.add("http://img3.imgtn.bdimg.com/it/u=1938931313,3944636906&fm=26&gp=0.jpg")
        imageList.add("http://img3.imgtn.bdimg.com/it/u=2705115696,2812871630&fm=214&gp=0.jpg")
        imageList.add("http://i0.hdslb.com/bfs/archive/f82fd6472f0bb071deee6f3defd0dc665dab330d.jpg")
        imageList.add("http://2e.zol-img.com.cn/product/122_800x600/688/ce0nEVHbsjooc.jpg")
        imageList.add("http://img.newyx.net/photo/201604/08/44213c3996.jpg")

        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())

                .start()


        linearLayoutManager= LinearLayoutManager(this)
        linearLayoutManager!!.orientation=LinearLayoutManager.HORIZONTAL

        rv_menu.layoutManager=linearLayoutManager
        menuAdapter=LaundryMenuAdapter(this)
        rv_menu.adapter=menuAdapter

        rv_menu.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_menu){
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i=vh!!.adapterPosition
                ToastUtil.showToast(i.toString())
                if(i<0){
                    return
                }
                menuAdapter!!.setSelect(i)
            }
        })

        MyApplication.setRedNum(tv_msgNum, 21)
    }

}