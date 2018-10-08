package com.lixin.amuseadjacent.app.ui.find.fragment

import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.activity.*
import com.lixin.amuseadjacent.app.ui.find.adapter.FindAdapter
import com.lixin.amuseadjacent.app.ui.find.model.FindModel
import com.lixin.amuseadjacent.app.ui.find.request.ActivityComment_272829210
import com.lixin.amuseadjacent.app.ui.find.request.Find_26
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.util.*
import com.nostra13.universalimageloader.core.ImageLoader
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
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
    private var onRefresh = 0

    private var eventModel: FindModel.activityModel? = null
    private var footView: View? = null
    private var iv_header: ImageView? = null
    private var image: ImageView? = null

    private var tv_type: TextView? = null
    private var tv_follow: TextView? = null
    private var tv_zan: TextView? = null
    private var tv_actiivtyname: TextView? = null
    private var tv_price: TextView? = null
    private var tv_activitytime: TextView? = null
    private var tv_address: TextView? = null
    private var tv_num: TextView? = null
    private var tv_name: TextView? = null
    private var tv_effect: TextView? = null
    private var tv_info: TextView? = null
    private var tv_time: TextView? = null
    private var tv_comment: TextView? = null


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

        rv_dyna.addHeaderView(headerView)
        rv_dyna.addFootView(footView, true)
        rv_dyna.noMoreLoading()

        rv_dyna.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                if (findList.isNotEmpty()) {
                    findList.clear()
                    findadapter!!.notifyDataSetChanged()
                }
                onRefresh = 1
                Find_26.find()
            }
            override fun onLoadMore() {
            }
        })

        findadapter = FindAdapter(activity!!, findList, null)
        rv_dyna.adapter = findadapter


        banner = headerView!!.findViewById(R.id.banner)
        banner!!.setOnBannerListener { i ->
            val bundle = Bundle()
            bundle.putString("title", "")
            bundle.putString("url", bannerUrl)
            MyApplication.openActivity(activity!!, WebViewActivity::class.java, bundle)
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

        footView = LayoutInflater.from(activity).inflate(R.layout.item_find, null, false)//头布局
        footView!!.findViewById<ConstraintLayout>(R.id.cl_1).visibility = View.GONE
        footView!!.findViewById<ConstraintLayout>(R.id.cl_2).visibility = View.VISIBLE
        iv_header = footView!!.findViewById(R.id.iv_header)
        image = footView!!.findViewById(R.id.image)

        tv_type = footView!!.findViewById(R.id.tv_type)
        tv_follow = footView!!.findViewById(R.id.tv_follow)
        tv_zan = footView!!.findViewById(R.id.tv_zan)
        tv_actiivtyname = footView!!.findViewById(R.id.tv_actiivtyname)
        tv_price = footView!!.findViewById(R.id.tv_price)
        tv_activitytime = footView!!.findViewById(R.id.tv_activitytime)
        tv_address = footView!!.findViewById(R.id.tv_address)
        tv_num = footView!!.findViewById(R.id.tv_num)
        tv_name = footView!!.findViewById(R.id.tv_name)
        tv_effect = footView!!.findViewById(R.id.tv_effect)
        tv_info = footView!!.findViewById(R.id.tv_info)
        tv_time = footView!!.findViewById(R.id.tv_time)
        tv_comment = footView!!.findViewById(R.id.tv_comment)

        footView!!.findViewById<ConstraintLayout>(R.id.cl_item).setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("name", eventModel!!.activityName)
            bundle.putString("id", eventModel!!.activityId)
            MyApplication.openActivity(context, EventDetailsActivity::class.java, bundle)
        }

        tv_follow!!.setOnClickListener { v ->
            if (eventModel == null) {
                return@setOnClickListener
            }
            ProgressDialog.showDialog(activity!!)
            Mail_138139.follow(eventModel!!.userid, object : Mail_138139.FollowCallBack {
                override fun follow() {
                    if (eventModel!!.userid == eventModel!!.userid) {
                        if (eventModel!!.isAttention == "0") {// 0未关注 1已关注
                            eventModel!!.isAttention = "1"
                        } else {
                            eventModel!!.isAttention = "0"
                        }
                    }
                    tv_follow!!.visibility = View.VISIBLE
                    if (eventModel!!.isAttention == "0") {// 0未关注 1已关注
                        tv_follow!!.text = "关注"
                    } else {
                        tv_follow!!.text = "已关注"
                        tv_follow!!.visibility = View.INVISIBLE
                    }
                }
            })
        }

        tv_zan!!.setOnClickListener { v ->
            if (eventModel == null) {
                return@setOnClickListener
            }
            ProgressDialog.showDialog(activity!!)
            ActivityComment_272829210.zan("0", eventModel!!.activityId, "", object : Find_26.ZanCallback {
                override fun zan() {
                    if (eventModel!!.isZan == "1") {
                        eventModel!!.isZan = "0"
                        eventModel!!.zanNum = (eventModel!!.zanNum.toInt() - 1).toString()
                    } else {
                        eventModel!!.isZan = "1"
                        eventModel!!.zanNum = (eventModel!!.zanNum.toInt() + 1).toString()
                    }
                    if (eventModel!!.isZan == "0") {//0未赞过 1已赞过
                        AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan, tv_zan, 5)
                    } else {
                        AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, tv_zan, 5)
                    }
                    tv_zan!!.text = eventModel!!.zanNum
                }
            })
        }
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
        if (TextUtils.isEmpty(model.theme.themeTitle)) {
            jingxuna.visibility = View.GONE
            tv_participate.visibility = View.GONE
        } else {
            tv_participate.text = model.theme.themeTitle
            tv_participate.setOnClickListener { v ->
                val bundle = Bundle()
                bundle.putString("url", model.theme.themeDetailUrl)
                bundle.putString("title", model.theme.themeTitle)
                MyApplication.openActivity(activity!!, WebViewActivity::class.java, bundle)
            }
        }

        if (findList.isNotEmpty()) {
            findList.clear()
            findadapter!!.notifyDataSetChanged()
        }

        if (onRefresh == 1) {
            rv_dyna.refreshComplete()
        }
        findList.addAll(model.danamicList)
        findadapter!!.notifyDataSetChanged()

        //脚布局，活动
        eventModel = model.activity
        ImageLoader.getInstance().displayImage(eventModel!!.activityImg, image)
        if (eventModel!!.issignup == "0") {//0未报名 1已报名
            tv_type!!.text = "未报名"
        } else {
            tv_type!!.text = "已报名"
        }
        if (eventModel!!.userid == StaticUtil.uid) {
            tv_follow!!.visibility = View.INVISIBLE
        } else {
            tv_follow!!.visibility = View.VISIBLE
            if (eventModel!!.isAttention == "0") {// 0未关注 1已关注
                tv_follow!!.text = "关注"
            } else {
                tv_follow!!.text = "已关注"
                tv_follow!!.visibility = View.INVISIBLE
            }
        }
        if (eventModel!!.isZan == "0") {//0未赞过 1已赞过
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan, tv_zan, 5)
        } else {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, tv_zan, 5)
        }
        tv_actiivtyname!!.text = eventModel!!.activityName
        tv_price!!.text = eventModel!!.activityMoney + "元/人"
        tv_activitytime!!.text = "时间：" + eventModel!!.activityTime
        tv_address!!.text = "地点：" + eventModel!!.activityAddress
        tv_num!!.text = "人数：" + eventModel!!.activityNownum + "/" + eventModel!!.activityAllnum

        ImageLoader.getInstance().displayImage(eventModel!!.userIcon, iv_header, ImageLoaderUtil.HeaderDIO())
        tv_name!!.text = eventModel!!.userName
        tv_effect!!.text = "影响力" + eventModel!!.userEffectNum
        tv_info!!.visibility = View.GONE

        tv_time!!.text = eventModel!!.time
        tv_comment!!.text = eventModel!!.commentNum
        tv_zan!!.text = eventModel!!.zanNum

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
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}