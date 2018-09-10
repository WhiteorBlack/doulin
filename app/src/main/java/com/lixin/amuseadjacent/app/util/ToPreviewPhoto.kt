package com.lixin.amuseadjacent.app.util

import android.content.Context
import android.os.Bundle
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.photoView.imagepage.ImagePagerActivity

/**
 * 浏览网络图片
 * Created by Slingge on 2018/9/10.
 */
object ToPreviewPhoto {


    fun toPhoto(context: Context, list: ArrayList<String>, position: Int) {

        val bundle = Bundle()
        bundle.putSerializable("image_urls", list)
        bundle.putInt("image_index", position)
        MyApplication.openActivity(context, ImagePagerActivity::class.java, bundle)
    }


}