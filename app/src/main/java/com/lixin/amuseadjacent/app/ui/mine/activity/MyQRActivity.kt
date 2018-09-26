package com.lixin.amuseadjacent.app.ui.mine.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.lixin.amuseadjacent.zxing.encoding.EncodingHandler
import com.netease.nim.uikit.common.media.picker.adapter.PickerAlbumAdapter
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_code_my.*

/**
 * 我的二维码
 * Created by Slingge on 2018/9/1
 */
class MyQRActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_my)
        init()
    }


    private fun init() {
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.setColorNoTranslucent(this, Color.parseColor("#1A1A1A"))
        }

        ImageLoader.getInstance().displayImage(StaticUtil.headerUrl, iv_header)
        tv_name.text = StaticUtil.nickName
        tv_uid.text = StaticUtil.uid

        val contents = String( StaticUtil.uid.toByteArray(charset("UTF-8")), charset("ISO-8859-1"))
        val mBitmap = EncodingHandler.createQRCode(contents, 500)
        iv_code.setImageBitmap(mBitmap)

        iv_back.setOnClickListener { v ->
            finish()
        }
    }



}