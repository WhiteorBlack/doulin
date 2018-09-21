package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.service.model.PopularShopDetailsModel
import com.nostra13.universalimageloader.core.ImageLoader


/**
 * 民间店铺详情
 * Created by Slingge on 2018/8/22
 */
class PopularShopDetailsAdapter(val context: Context, val serviceList: ArrayList<PopularShopDetailsModel.dataModel>) : RecyclerView.Adapter<PopularShopDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_popular_details, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = serviceList[position]
        holder.tv_info.text = model.serviceDesc
        holder.tv_name.text = model.serviceName
        holder.tv_money.text = "￥" + model.servicePrice
        ImageLoader.getInstance().displayImage(model.serviceImg, holder.image)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)

    }


}
