package com.lixin.amuseadjacent.app.ui.find.fragment

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicActivity
import com.lixin.amuseadjacent.app.ui.find.activity.EventActivity
import com.lixin.amuseadjacent.app.ui.find.activity.TalentActivity
import com.lixin.amuseadjacent.app.ui.find.adapter.FindAdapter
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import java.util.*

/**
 * Created by Slingge on 2018/8/15
 */
class FindFragment : BaseFragment(), View.OnClickListener {

    private var linearLayoutManager: LinearLayoutManager? = null
    private var headerView: View? = null

    private var findadapter: FindAdapter? = null

    val imageList = ArrayList<String>()

    private var banner: Banner? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.xrecyclerview, container, false)
        init()
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (Build.VERSION.SDK_INT > 19) {
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(activity, view_staus)
            StatusBarUtil.transparentStatusBar(activity)
            StatusBarBlackWordUtil.StatusBarLightMode(activity)
        }

        tv_title.text = "发现"
        iv_back.visibility = View.GONE

        xrecyclerview.layoutManager = linearLayoutManager
        xrecyclerview.setPullRefreshEnabled(false)

        xrecyclerview.isFocusable = false

        xrecyclerview.addHeaderView(headerView)

        xrecyclerview.adapter = findadapter

        banner = headerView!!.findViewById(R.id.banner)

        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .start()

    }


    private fun init() {

        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL

        headerView = LayoutInflater.from(activity).inflate(R.layout.header_find, null, false)//头布局
        headerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        findadapter = FindAdapter(activity!!)

        imageList.add("http://img3.imgtn.bdimg.com/it/u=1938931313,3944636906&fm=26&gp=0.jpg")
        imageList.add("http://img3.imgtn.bdimg.com/it/u=2705115696,2812871630&fm=214&gp=0.jpg")
        imageList.add("http://i0.hdslb.com/bfs/archive/f82fd6472f0bb071deee6f3defd0dc665dab330d.jpg")
        imageList.add("http://2e.zol-img.com.cn/product/122_800x600/688/ce0nEVHbsjooc.jpg")
        imageList.add("http://img.newyx.net/photo/201604/08/44213c3996.jpg")

        headerView!!.findViewById<ImageView>(R.id.iv_talent).setOnClickListener(this)
        headerView!!.findViewById<TextView>(R.id.tv_talent).setOnClickListener(this)

        headerView!!.findViewById<ImageView>(R.id.iv_dynamic).setOnClickListener(this)
        headerView!!.findViewById<TextView>(R.id.tv_dynamic).setOnClickListener(this)

        headerView!!.findViewById<ImageView>(R.id.iv_activity).setOnClickListener(this)
        headerView!!.findViewById<TextView>(R.id.tv_activity).setOnClickListener(this)
    }

    override fun loadData() {

    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_talent, R.id.tv_talent -> {//达人
                MyApplication.openActivity(activity, TalentActivity::class.java)
            }
            R.id.iv_dynamic, R.id.tv_dynamic -> {//动态
                MyApplication.openActivity(activity, DynamicActivity::class.java)
            }
            R.id.iv_activity, R.id.tv_activity -> {//活动
                MyApplication.openActivity(activity, EventActivity::class.java)
            }
        }

    }


}