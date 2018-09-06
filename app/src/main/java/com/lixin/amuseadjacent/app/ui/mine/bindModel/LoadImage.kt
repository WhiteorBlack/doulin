package com.lixin.amuseadjacent.app.ui.mine.bindModel

import android.databinding.BindingAdapter
import android.widget.ImageView

import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 加载网络图片
 * Created by Slingge on 2018/8/1 0001.
 */
object LoadImage {


    @BindingAdapter("bind:imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String) {
        ImageLoader.getInstance().displayImage(url, imageView)
    }

}
