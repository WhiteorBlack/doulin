package com.lixin.amuseadjacent.app.ui.service.fragment

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
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
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.lxkj.huaihuatransit.app.util.ControlWidthHeight
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.xrecyclerview, container, false)
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

        tv_title.text = "服务"

        iv_back.setImageResource(R.drawable.ic_car)
        iv_back.setOnClickListener(this)

        xrecyclerview.layoutManager = linearLayoutManager
        xrecyclerview.setPullRefreshEnabled(false)
        xrecyclerview.setLoadingMoreEnabled(false)
        xrecyclerview.isFocusable = false

        xrecyclerview.addHeaderView(headerView)

        banner = headerView!!.findViewById(R.id.banner)
        ControlWidthHeight.inputhigh(ControlWidthHeight.dip2px(activity!!,100),banner!!)

        banner!!.setOnBannerListener { i ->
            val bundle = Bundle()
            bundle.putString("title", "")
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
    }


    @Subscribe
    fun onEvent(model: ServiceModel) {
        val imageList = ArrayList<String>()
        bannerList.clear()
        for (i in 0 until model.bannerList.size) {
            imageList.add(model.bannerList[i].topImgUrl)
            bannerList.add(model.bannerList[i])
        }
        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()

        serviceList = model.dataList
        serviceAdapter = ServiceAdapter(activity!!, serviceList)
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
        }
    }


    override fun loadData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}