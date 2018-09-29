package com.lixin.amuseadjacent.app.util

import android.content.Context
import android.os.Bundle
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.photoView.imagepage.ImagePagerActivity

/**
 * 跳转图片预览
 * Created by Slingge on 2018/9/15
 */
object PreviewPhoto {
    private var imageList = ArrayList<String>()

    fun preview(context: Context, list: ArrayList<String>, i: Int) {
        val bundle = Bundle()
        bundle.putSerializable("image_urls", list)
        bundle.putInt("image_index", i)
        MyApplication.openActivity(context, ImagePagerActivity::class.java, bundle)
    }

    fun preview(context: Context, url: String) {
        if (imageList.isNotEmpty()) {
            imageList.clear()
        }
        imageList.add(url)
        val bundle = Bundle()
        bundle.putSerializable("image_urls", imageList)
        bundle.putInt("image_index", 0)
        MyApplication.openActivity(context, ImagePagerActivity::class.java, bundle)
    }
}