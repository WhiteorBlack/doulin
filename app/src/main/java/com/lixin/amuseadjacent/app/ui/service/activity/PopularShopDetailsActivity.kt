package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.service.adapter.PopularShopDetailsAdapter
import kotlinx.android.synthetic.main.activity_popular_shop_details.*

/**
 * 小区店铺详情
 * Created by Slingge on 2018/8/31
 */
class PopularShopDetailsActivity : BaseActivity() {

    private var detailsAdapter: PopularShopDetailsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_shop_details)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("店铺名")

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_service.setPullRefreshEnabled(false)
        rv_service.isFocusable = false
        rv_service.layoutManager = linearLayoutManager
        detailsAdapter = PopularShopDetailsAdapter(this)
        rv_service.adapter = detailsAdapter
    }


}