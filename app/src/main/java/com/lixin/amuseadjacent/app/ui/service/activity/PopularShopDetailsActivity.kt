package com.lixin.amuseadjacent.app.ui.service.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.service.adapter.PopularShopDetailsAdapter
import com.lixin.amuseadjacent.app.ui.service.model.PopularShopDetailsModel
import com.lixin.amuseadjacent.app.ui.service.model.PopularShopModel
import com.lixin.amuseadjacent.app.ui.service.request.PopularShop_39310
import com.lixin.amuseadjacent.app.util.PreviewPhoto
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_popular_shop_details.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import android.content.Intent.ACTION_CALL
import android.net.Uri
import com.lixin.amuseadjacent.app.util.GlideImageLoader
import kotlinx.android.synthetic.main.include_banner.*


/**
 * 小区店铺详情
 * Created by Slingge on 2018/8/31
 */
class PopularShopDetailsActivity : BaseActivity(), View.OnClickListener {

    private var detailsAdapter: PopularShopDetailsAdapter? = null
    private var serviceList = ArrayList<PopularShopDetailsModel.dataModel>()

    private var imageList = ArrayList<String>()
    private var phone = ""

    private var shopId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_shop_details)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("")

        iv_phone.setOnClickListener(this)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_service.setPullRefreshEnabled(false)
        rv_service.isFocusable = false
        rv_service.layoutManager = linearLayoutManager

        shopId = intent.getStringExtra("id")

        ProgressDialog.showDialog(this)
        PopularShop_39310.shopDetails(shopId)
    }


    @Subscribe
    fun onEvent(model: PopularShopDetailsModel) {
        inittitle(model.`object`.shopName)

        tv_name.text = model.`object`.shopName
        tv_money.text = "人均：￥" + model.`object`.shopPrice
        tv_info.text = model.`object`.shopDesc
        tv_address.text = model.`object`.shopAddress
        tv_time.text = "营业时间：" + model.`object`.shopTime
        phone = model.`object`.shopPhone

        imageList = model.`object`.shopImgUrl

        banner!!.setImages(imageList)
                .setImageLoader(GlideImageLoader())
                .start()
        banner!!.setOnBannerListener { i->
            PreviewPhoto.preview(this,imageList,i)
        }

        tv_num.text = imageList.size.toString()

        serviceList = model.dataList
        detailsAdapter = PopularShopDetailsAdapter(this, serviceList)
        rv_service.adapter = detailsAdapter
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        detailsAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.image -> {
                if(imageList.isNotEmpty()){
                    PreviewPhoto.preview(this, imageList, 0)
                }
            }
            R.id.iv_phone -> {
                val intent = Intent(Intent.ACTION_DIAL)
                val data = Uri.parse("tel:$phone")
                intent.data = data
                startActivity(intent)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}