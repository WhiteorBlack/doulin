package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.PreviewPhoto
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_commodity_details.*

/**
 * Created by Slingge on 2018/10/12
 */
class CommodityDetailsActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_details)
        init()
    }


    private fun init() {
        StatusBarUtil.immersiveStatusBar(this, 0f)
        StatusBarBlackWordUtil.StatusBarLightMode(this, StatusBarBlackWordUtil.StatusBarLightMode(this))
        ic_back.setOnClickListener { v ->
            finish()
        }
        val imageUrl = intent.getStringExtra("image")
        iv_image.setOnClickListener { v ->
            PreviewPhoto.preview(this, imageUrl)
        }

        ImageLoader.getInstance().displayImage(imageUrl, iv_image)

        tv_title.text = intent.getStringExtra("title")
        tv_info.text = intent.getStringExtra("info")
        tv_money.text ="￥"+ intent.getStringExtra("money")

    }


}