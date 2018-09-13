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
import com.lixin.amuseadjacent.app.ui.service.activity.*
import com.lixin.amuseadjacent.app.ui.service.adapter.ServiceAdapter
import com.lixin.amuseadjacent.app.ui.service.model.CouponModel
import com.lixin.amuseadjacent.app.ui.service.request.Coupon_3132
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
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

    val imageList = ArrayList<String>()


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

        xrecyclerview.adapter = serviceAdapter
        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        serviceAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()


        xrecyclerview.addOnItemTouchListener(object : RecyclerItemTouchListener(xrecyclerview) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition - 2
                if (i < 0) {
                    return
                }
                MyApplication.openActivity(activity, SpecialAreaActivity::class.java)
            }
        })

        banner = headerView!!.findViewById(R.id.banner)
        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()
    }


    private fun init() {

        linearLayoutManager = GridLayoutManager(activity, 3)

        headerView = LayoutInflater.from(activity).inflate(R.layout.header_service, null, false)//头布局
        headerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        serviceAdapter = ServiceAdapter(activity!!)

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

        headerView!!.findViewById<TextView>(R.id.tv_help).setOnClickListener(this)
        headerView!!.findViewById<ImageView>(R.id.iv_help).setOnClickListener(this)

        //获取优惠券
        Coupon_3132.getCoupon()
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_talent, R.id.tv_talent -> {//新鲜果蔬
                MyApplication.openActivity(activity, OfficialShopActivity::class.java)
            }
            R.id.iv_dynamic, R.id.tv_dynamic -> {//洗衣洗鞋
                MyApplication.openActivity(activity, LaundryActivity::class.java)
            }
            R.id.iv_activity, R.id.tv_activity -> {//超市便利
                MyApplication.openActivity(activity, LaundryActivity::class.java)
            }
            R.id.tv_help, R.id.iv_help -> {//小区店铺
                MyApplication.openActivity(activity, PopularShopActivity::class.java)
            }
            R.id.iv_back -> {//购物车
                MyApplication.openActivity(activity, ShopCarActivity::class.java)
            }
        }
    }


    //优惠券
    @Subscribe
    fun onEvent(model: CouponModel) {
        if (model.dataList.isNotEmpty()) {
            CouponDialog.communityDialog(activity!!, model.dataList)
        }
    }



    override fun loadData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}