package com.lixin.amuseadjacent.app.ui.find.fragment

import android.content.Intent
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
import cn.jzvd.Jzvd
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.activity.*
import com.lixin.amuseadjacent.app.ui.find.adapter.FindAdapter
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclDetailsModel
import com.lixin.amuseadjacent.app.ui.find.model.EventDetailsModel
import com.lixin.amuseadjacent.app.ui.find.model.FindModel
import com.lixin.amuseadjacent.app.ui.find.model.TopicModel
import com.lixin.amuseadjacent.app.ui.find.request.ActivityComment_272829210
import com.lixin.amuseadjacent.app.ui.find.request.Find_26
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.util.*
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
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
    private var topImgDetailUrlState = ""///banner点击详情链接状态 0 有效 1无效
    private var onRefresh = 0

    private var eventModel: FindModel.activityModel? = null
    private var footView: View? = null
    private var iv_header: ImageView? = null
    private var image: ImageView? = null

    private var themeId = ""//话题id
    private var tv_participate: TextView? = null//话题内容
    private var iv_topic: ImageView? = null
    private var tv_topictime: TextView? = null//时间
    private var tv_topiccomment: TextView? = null//话题回复数
    private var tv_topiczan: TextView? = null//话题点赞
    private var isZanTopic = "0"//是否赞了话题0未赞过 1已赞过
    private var isZanNum = 0

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
            if (TextUtils.isEmpty(bannerUrl) || topImgDetailUrlState == "1") {
                return@setOnBannerListener
            }
            val bundle = Bundle()
            bundle.putString("title", "详情")
            bundle.putString("url", bannerUrl)
            MyApplication.openActivity(activity!!, WebViewActivity::class.java, bundle)
        }
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

        tv_topictime = headerView!!.findViewById(R.id.tv_topictime)
        tv_participate = headerView!!.findViewById(R.id.tv_participate)
        iv_topic = headerView!!.findViewById(R.id.iv_topic)
        tv_topiccomment = headerView!!.findViewById(R.id.tv_topiccomment)
        tv_topiczan = headerView!!.findViewById(R.id.tv_topiczan)
        tv_topiczan!!.setOnClickListener(this)
        iv_topic!!.setOnClickListener(this)
        tv_participate!!.setOnClickListener(this)

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
        tv_time = headerView!!.findViewById(R.id.tv_time)
        tv_comment = footView!!.findViewById(R.id.tv_comment)

        footView!!.findViewById<ConstraintLayout>(R.id.cl_item).visibility = View.GONE
        footView!!.findViewById<ConstraintLayout>(R.id.cl_item).setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("name", eventModel!!.activityName)
            bundle.putString("id", eventModel!!.activityId)
            bundle.putInt("count", -303)
            MyApplication.openActivityForResult(activity, EventDetailsActivity::class.java, bundle, 2)
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

        ProgressDialog.showDialog(activity!!)
        Find_26.find()
    }

    override fun loadData() {

    }


    @Subscribe
    fun onEvent(model: FindModel) {
        if (imageList.isNotEmpty()) {
            imageList.clear()
        }
        bannerUrl = model.topImgDetailUrl
        topImgDetailUrlState = model.topImgDetailUrlState
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
            tv_participate!!.visibility = View.GONE
            iv_topic!!.visibility = View.GONE
            tv_topiczan!!.visibility=View.GONE
            tv_topiccomment!!.visibility=View.GONE
        } else {
            jingxuna.visibility = View.VISIBLE
            iv_topic!!.visibility=View.VISIBLE
            tv_participate!!.visibility = View.VISIBLE
            tv_topiczan!!.visibility=View.VISIBLE
            tv_topiccomment!!.visibility=View.VISIBLE
            tv_participate!!.text = model.theme.themeTitle
            themeId = model.theme.themeId

            tv_topictime!!.text = model.theme.themeTime
            ImageLoader.getInstance().displayImage(model.theme.themeImage, iv_topic)
            isZanNum = model.theme.zanNum.toInt()
            isZanTopic = model.theme.isZan
            if (isZanTopic == "1") {//0未赞过 1已赞过
                AbStrUtil.setDrawableLeft(activity, R.drawable.ic_zan_hl, tv_topiczan, 5)
            }
            tv_topiczan!!.text = model.theme.zanNum
            tv_topiccomment!!.text = model.theme.commentNum
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
        if (TextUtils.isEmpty(eventModel!!.activityName)) {
            return
        }
        footView!!.findViewById<ConstraintLayout>(R.id.cl_item).visibility = View.VISIBLE

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
            R.id.tv_participate, R.id.iv_topic -> {//话题
                val bundle = Bundle()
                bundle.putString("id", themeId)
                MyApplication.openActivityForResult(activity!!, TopicActivity::class.java, bundle, 3)
            }
            R.id.tv_topiczan -> {//话题点赞
                ProgressDialog.showDialog(activity!!)
                ActivityComment_272829210.zan("1", themeId, "", object : Find_26.ZanCallback {
                    override fun zan() {
                        if (isZanTopic == "1") {
                            isZanTopic = "0"
                            isZanNum--
                            AbStrUtil.setDrawableLeft(activity, R.drawable.ic_zan, tv_topiczan, 5)
                        } else {
                            isZanTopic = "1"
                            isZanNum++
                            AbStrUtil.setDrawableLeft(activity, R.drawable.ic_zan_hl, tv_topiczan, 5)
                        }
                        tv_topiczan!!.text = (isZanNum.toString())
                    }
                })
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 1) {
            val position = data.getIntExtra("position", -1)
            if (position == -1) {
                return
            }
            val model = data.getSerializableExtra("model") as DynamiclDetailsModel
            findList[position].commentNum = model.`object`.commentNum
            findList[position].isZan = model.`object`.isZan
            findList[position].isAttention = model.`object`.isAttention
            findList[position].zanNum = model.`object`.zanNum
            findadapter!!.notifyDataSetChanged()
        } else if (requestCode == 2) {//活动
            val model = data.getSerializableExtra("model") as EventDetailsModel

            eventModel!!.commentNum = model.`object`.commentNum
            eventModel!!.zanNum = model.`object`.zanNum
            eventModel!!.isZan = model.`object`.isZan
            eventModel!!.isAttention = model.`object`.isAttention
            eventModel!!.iscang = model.`object`.iscang
            eventModel!!.issignup = model.`object`.issignup

            if (eventModel!!.isZan == "0") {//0未赞过 1已赞过
                AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan, tv_zan, 5)
            } else {
                AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, tv_zan, 5)
            }
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
            tv_comment!!.text = eventModel!!.commentNum
            tv_zan!!.text = eventModel!!.zanNum
        } else if (requestCode == 3) {//话题，回复、点赞
            val model = data.getSerializableExtra("model") as TopicModel.objectModel
            isZanNum = model.zanNum.toInt()
            isZanTopic = model.isZan
            if (isZanTopic == "1") {//0未赞过 1已赞过
                AbStrUtil.setDrawableLeft(activity, R.drawable.ic_zan_hl, tv_topiczan, 5)
            }
            tv_topiczan!!.text = model.zanNum
            tv_topiccomment!!.text = model.commentNum
        }
    }

    @Subscribe
    fun onEvent() {

    }


    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}