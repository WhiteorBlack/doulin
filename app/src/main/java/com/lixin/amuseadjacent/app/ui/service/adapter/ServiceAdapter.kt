package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.lixin.amuseadjacent.R
import android.widget.TextView
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.service.activity.SpecialAreaActivity
import com.lixin.amuseadjacent.app.ui.service.model.ServiceModel
import com.lixin.amuseadjacent.app.util.PreviewPhoto
import com.nostra13.universalimageloader.core.ImageLoader
import java.util.ArrayList


/**
 * 服务
 * Created by Slingge on 2018/8/22
 */
class ServiceAdapter(val context: Context, val serviceList: ArrayList<ServiceModel.dataModel>) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position % 3 == 1) {
            val lp = FrameLayout.LayoutParams(holder.cl_item.layoutParams)
            lp.setMargins(2, 0, 2, 0)
            holder.cl_item.layoutParams = lp
        }

        val model = serviceList[position]

        ImageLoader.getInstance().displayImage(model.optimizationImg, holder.iv_image)
        holder.tv_name.text = model.optimizationName
        holder.tv_info.text = model.optimizationDesc

        holder.cl_item.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putSerializable("model", serviceList[position])
            MyApplication.openActivity(context, SpecialAreaActivity::class.java, bundle)
        }

//        holder.iv_image.setOnClickListener { v ->
//            PreviewPhoto.preview(context, model.optimizationImg)
//        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl_item = view.findViewById<ConstraintLayout>(R.id.cl_item)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val iv_image = view.findViewById<ImageView>(R.id.iv_image)
    }


}
