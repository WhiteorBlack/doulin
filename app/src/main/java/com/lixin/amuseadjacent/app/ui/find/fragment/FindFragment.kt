package com.lixin.amuseadjacent.app.ui.find.fragment

import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.activity.*
import com.lixin.amuseadjacent.app.ui.find.adapter.FindAdapter
import com.lixin.amuseadjacent.app.ui.find.model.FindModel
import com.lixin.amuseadjacent.app.ui.find.request.Find_26
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.lixin.amuseadjacent.app.util.ImageLoaderUtil
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.nostra13.universalimageloader.core.ImageLoader
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.header_find.*
import kotlinx.android.synthetic.main.header_find.view.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import kotlin.collections.ArrayList

/**
 * Created by Slingge on 2018/8/15
 */
class FindFragment : BaseFragment(), View.OnClickListener {

    private var linearLayoutManager: LinearLayoutManager? = null
    private var headerView: View? = null

    private var findadapter: FindAdapter? = null

    private val imageList = ArrayList<String>()
    private var findList = ArrayList<FindModel.dynamicModel>()

    private var banner: Banner? = null
    private var bannerUrl = ""

    private var isfirst=true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_find, container, false)
        EventBus.getDefault().register(this)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (Build.VERSION.SDK_INT > 19) {
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(activity, view_staus)
            StatusBarUtil.setColorNoTranslucent(activity, resources.getColor(R.color.white))
            StatusBarBlackWordUtil.StatusBarLightMode(activity)
        }

        tv_title.text = "发现"
        iv_back.visibility = View.GONE

        rv_dyna.layoutManager = linearLayoutManager
        rv_dyna.setLoadingMoreEnabled(false)

        rv_dyna.isFocusable = false
        rv_activity.isFocusable = false

        rv_dyna.addHeaderView(headerView)

        findadapter = FindAdapter(activity!!, findList, null)
        rv_dyna.adapter = findadapter

        val linearLayoutManager2 = LinearLayoutManager(activity)
        linearLayoutManager2.orientation = LinearLayoutManager.VERTICAL
        rv_activity.layoutManager = linearLayoutManager2


        banner = headerView!!.findViewById(R.id.banner)
        banner!!.setOnBannerListener { i ->
            val bundle = Bundle()
            bundle.putString("title", "")
            bundle.putString("url", bannerUrl)
            MyApplication.openActivity(activity!!, WebViewActivity::class.java,bundle)
        }

    }

    override fun onStart() {
        super.onStart()
        ProgressDialog.showDialog(activity!!)
        Find_26.find()
    }


    private fun init() {
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL

        headerView = LayoutInflater.from(activity).inflate(R.layout.header_find, null, false)//头布局
        headerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)


        headerView!!.findViewById<ImageView>(R.id.iv_talent).setOnClickListener(this)
        headerView!!.findViewById<TextView>(R.id.tv_talent).setOnClickListener(this)

        headerView!!.findViewById<ImageView>(R.id.iv_dynamic).setOnClickListener(this)
        headerView!!.findViewById<TextView>(R.id.tv_dynamic).setOnClickListener(this)

        headerView!!.findViewById<ImageView>(R.id.iv_activity).setOnClickListener(this)
        headerView!!.findViewById<TextView>(R.id.tv_activity).setOnClickListener(this)

        headerView!!.findViewById<TextView>(R.id.tv_help).setOnClickListener(this)
        headerView!!.findViewById<ImageView>(R.id.iv_help).setOnClickListener(this)

        headerView!!.redman.setOnClickListener(this)
    }

    override fun loadData() {

    }


    @Subscribe
    fun onEvent(model: FindModel) {
        if (imageList.isNotEmpty()) {
            imageList.clear()
        }
        bannerUrl = model.topImgDetailUrl
        imageList.add(model.topImgUrl)
        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()

        if (model.redmanList.isNotEmpty()) {
            if (model.redmanList.size == 1) {
                ImageLoader.getInstance().displayImage(model.redmanList[0].userImg, header1, ImageLoaderUtil.HeaderDIO())
            } else if (model.redmanList.size == 2) {
                ImageLoader.getInstance().displayImage(model.redmanList[0].userImg, header1, ImageLoaderUtil.HeaderDIO())
                ImageLoader.getInstance().displayImage(model.redmanList[1].userImg, header2, ImageLoaderUtil.HeaderDIO())
            } else if (model.redmanList.size == 3) {
                ImageLoader.getInstance().displayImage(model.redmanList[0].userImg, header1, ImageLoaderUtil.HeaderDIO())
                ImageLoader.getInstance().displayImage(model.redmanList[1].userImg, header2, ImageLoaderUtil.HeaderDIO())
                ImageLoader.getInstance().displayImage(model.redmanList[2].userImg, header3, ImageLoaderUtil.HeaderDIO())
            }
        }
        tv_participate.text = model.theme.themeTitle
        tv_participate.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("url", model.theme.themeDetailUrl)
            bundle.putString("title", model.theme.themeTitle)
            MyApplication.openActivity(activity!!, WebViewActivity::class.java,bundle)
        }

        if (findList.isNotEmpty()) {
            findList.clear()
            findadapter!!.notifyDataSetChanged()
        }
        findList.addAll(model.danamicList)
        findadapter!!.notifyDataSetChanged()

        val actiivtyList = ArrayList<FindModel.activityModel>()
        actiivtyList.add(model.activity)
        findadapter = FindAdapter(activity!!, null, actiivtyList)
        rv_activity.adapter = findadapter
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
            R.id.tv_help, R.id.iv_help -> {//帮帮
                MyApplication.openActivity(activity, BangBangActivity::class.java)
            }
            R.id.redman -> {//红人榜
                MyApplication.openActivity(activity, RedManListActivity::class.java)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        findadapter!!.stopPlay()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}