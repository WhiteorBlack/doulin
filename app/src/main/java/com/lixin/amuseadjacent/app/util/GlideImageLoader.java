package com.lixin.amuseadjacent.app.util;

import android.content.Context;

import com.youth.banner.RoundImageView;
import com.youth.banner.loader.ImageLoader;

/**
 * 加载轮播图片
 * Created by Slingge on 2017/7/11 0011.
 */

public class GlideImageLoader extends ImageLoader {


    @Override
    public void displayImage(Context context, Object path, RoundImageView imageView) {
        /**
         * context 上下文
         * path 图片路径
         * imageView 加载的控件
         * 可以使用任意图片加载方式
         * */
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(path.toString(), imageView);
    }


}
