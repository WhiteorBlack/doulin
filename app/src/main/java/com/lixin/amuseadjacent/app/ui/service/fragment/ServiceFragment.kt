package com.lixin.amuseadjacent.app.ui.service.fragment

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.CouponDialog
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.ui.service.activity.*
import com.lixin.amuseadjacent.app.ui.service.adapter.ServiceAdapter
import com.lixin.amuseadjacent.app.ui.service.model.CouponModel
import com.lixin.amuseadjacent.app.ui.service.model.ServiceModel
import com.lixin.amuseadjacent.app.ui.service.request.Coupon_3132
import com.lixin.amuseadjacent.app.ui.service.request.Service_33
import com.lixin.amuseadjacent.app.util.*
import com.lxkj.huaihuatransit.app.util.ControlWidthHeight
import com.nostra13.universalimageloader.core.ImageLoader
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 服务
 * Created by Slingge on 2018/8/15
 */
class ServiceFragment : BaseFragment(), View.OnClickListener {

    private var banner: Banner? = null

    private var linearLayoutManager: LinearLayoutManager? = null
    private var headerView: View? = null

    private var serviceAdapter: ServiceAdapter? = null
    private var serviceList = ArrayList<ServiceModel.dataModel>()

    private var bannerList = ArrayList<ServiceModel.bannerModel>()

    private lateinit var sdvSecond: SimpleDraweeView
    private lateinit var sdvBest: SimpleDraweeView
    private lateinit var sdvMarket: SimpleDraweeView
    private lateinit var sdvTotal: SimpleDraweeView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.xrecyclerview, container, false)
        EventBus.getDefault().register(this)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rl_main.fitsSystemWindows = false
        if (Build.VERSION.SDK_INT > 19) {
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(activity, view_staus)
            StatusBarUtil.setColorNoTranslucent(activity, resources.getColor(R.color.white))
            StatusBarBlackWordUtil.StatusBarLightMode(activity)
        }

        tv_title.text = "服务"

        iv_back.setImageResource(R.drawable.ic_car)
        iv_back.setOnClickListener(this)

        xrecyclerview.layoutManager = linearLayoutManager
        xrecyclerview.setPullRefreshEnabled(false)
        xrecyclerview.setLoadingMoreEnabled(false)
        xrecyclerview.isFocusable = false

        xrecyclerview.addHeaderView(headerView)

        banner = headerView!!.findViewById(R.id.banner)
        val params=banner!!.layoutParams as FrameLayout.LayoutParams
        params.width=ScreenUtil.getWidthAndHeight().widthPixels-ScreenUtil.dip2px(24f)
        params.height= (params.width*0.428).toInt()
        banner!!.layoutParams=params
        banner!!.setOnBannerListener { i ->
            if (TextUtils.isEmpty(bannerList[i].topImgDetailUrl) || bannerList[i].topImgDetailUrlState == "1") {
                return@setOnBannerListener
            }
            val bundle = Bundle()
            bundle.putString("title", "详情")
            bundle.putString("url", bannerList[i].topImgDetailUrl)
            MyApplication.openActivity(activity!!, WebViewActivity::class.java, bundle)
        }

        ProgressDialog.showDialog(activity!!)
        Service_33.service()
        //获取优惠券
        Coupon_3132.getCoupon()
    }


    private fun init() {
        linearLayoutManager = GridLayoutManager(activity, 3)

        headerView = LayoutInflater.from(activity).inflate(R.layout.header_service, null, false)//头布局
        headerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)


        headerView!!.findViewById<ImageView>(R.id.iv_talent).setOnClickListener(this)
        headerView!!.findViewById<TextView>(R.id.tv_talent).setOnClickListener(this)

        headerView!!.findViewById<ImageView>(R.id.iv_dynamic).setOnClickListener(this)
        headerView!!.findViewById<TextView>(R.id.tv_dynamic).setOnClickListener(this)

        headerView!!.findViewById<ImageView>(R.id.iv_activity).setOnClickListener(this)
        headerView!!.findViewById<TextView>(R.id.tv_activity).setOnClickListener(this)

        headerView!!.findViewById<TextView>(R.id.tv_help).setOnClickListener(this)
        headerView!!.findViewById<ImageView>(R.id.iv_help).setOnClickListener(this)

        sdvBest = headerView!!.findViewById(R.id.sdv_best)
        sdvBest.setOnClickListener(this)
        sdvMarket = headerView!!.findViewById(R.id.sdv_market)
        sdvMarket.setOnClickListener(this)
        sdvSecond = headerView!!.findViewById(R.id.sdv_second)
        sdvSecond.setOnClickListener(this)
        sdvTotal = headerView!!.findViewById(R.id.sdv_total_buy)
        sdvTotal.setOnClickListener(this)
        val wideSize = ScreenUtil.getWidthAndHeight().widthPixels
        val secParams = sdvSecond.layoutParams
        secParams.width = (wideSize - ScreenUtil.dip2px(26f)) / 3
        sdvSecond.layoutParams = secParams

        val totalParams = sdvTotal.layoutParams
        totalParams.width = (wideSize - ScreenUtil.dip2px(26f)) * 2 / 3
        totalParams.height = totalParams.width / 2
        sdvTotal.layoutParams = totalParams

        val bestParams = sdvBest.layoutParams
        val marketParams = sdvMarket.layoutParams

        bestParams.width = (wideSize - ScreenUtil.dip2px(28f)) / 3
        marketParams.width = (wideSize - ScreenUtil.dip2px(28f)) / 3

        sdvMarket.layoutParams = marketParams
        sdvBest.layoutParams = bestParams
    }


    @Subscribe
    fun onEvent(model: ServiceModel) {
        val imageList = ArrayList<String>()
        bannerList.clear()
        for (i in 0 until model.bannerList.size) {
            imageList.add(model.bannerList[i].topImgUrl)
        }
        bannerList = model.bannerList
        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()

        serviceList = model.dataList
        if (serviceList.size >= 4) {
            sdvSecond.setImageURI(serviceList.get(0).optimizationImg)
            sdvTotal.setImageURI(serviceList.get(1).optimizationImg)
            sdvBest.setImageURI(serviceList.get(2).optimizationImg)
            sdvMarket.setImageURI(serviceList.get(3).optimizationImg)
        }
        serviceAdapter = ServiceAdapter(activity!!, ArrayList())
        xrecyclerview.adapter = serviceAdapter
        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        serviceAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }

    //优惠券
    @Subscribe
    fun onEvent(model: CouponModel) {
        if (model.dataList.isNotEmpty()) {
            CouponDialog.communityDialog(activity!!, model.dataList)
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_talent, R.id.tv_talent -> {//新鲜果蔬
                val bundle = Bundle()
                bundle.putString("type", "0")
                MyApplication.openActivity(activity, OfficialShopActivity::class.java, bundle)
            }
            R.id.iv_dynamic, R.id.tv_dynamic -> {//洗衣洗鞋
                MyApplication.openActivity(activity, LaundryActivity::class.java)
            }
            R.id.iv_activity, R.id.tv_activity -> {//超市便利
                val bundle = Bundle()
                bundle.putString("type", "2")
                MyApplication.openActivity(activity, OfficialShopActivity::class.java, bundle)
            }
            R.id.tv_help, R.id.iv_help -> {//小区店铺
                MyApplication.openActivity(activity, PopularShopActivity::class.java)
            }
            R.id.iv_back -> {//购物车
                MyApplication.openActivity(activity, ShopCarActivity::class.java)
            }

            R.id.sdv_second -> { //限时优惠
                val bundle = Bundle()
                bundle.putSerializable("model", serviceList[0])
                MyApplication.openActivity(context, SpecialAreaActivity::class.java, bundle)
            }

            R.id.sdv_total_buy -> { //整体购买
                val bundle = Bundle()
                bundle.putSerializable("model", serviceList[1])
                MyApplication.openActivity(context, SpecialAreaActivity::class.java, bundle)
            }

            R.id.sdv_best -> { //精选
                val bundle = Bundle()
                bundle.putSerializable("model", serviceList[2])
                MyApplication.openActivity(context, SpecialAreaActivity::class.java, bundle)
            }

            R.id.sdv_market -> { //评价超市
                val bundle = Bundle()
                bundle.putSerializable("model", serviceList[3])
                MyApplication.openActivity(context, SpecialAreaActivity::class.java, bundle)
            }
        }
    }


    override fun loadData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}