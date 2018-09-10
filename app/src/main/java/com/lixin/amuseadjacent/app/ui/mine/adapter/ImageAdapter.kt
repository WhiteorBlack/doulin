package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.lxkj.runproject.app.view.SquareImage
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 方形图片
 * Created by Slingge on 2018/8/18
 */
class ImageAdapter(val context: Context, val list: ArrayList<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        ImageLoader.getInstance().displayImage(list[position], holder.image)

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<SquareImage>(R.id.image)

    }


}