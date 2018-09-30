package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.service.model.PopularShopModel
import com.nostra13.universalimageloader.core.ImageLoader
import java.util.ArrayList


/**
 * 民间店铺
 * Created by Slingge on 2018/8/22
 */
class PopularShopAdapter(val context: Context, val shopList: ArrayList<PopularShopModel.dataModel>) : RecyclerView.Adapter<PopularShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_popular_shop, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = shopList[position]
        ImageLoader.getInstance().displayImage(model.shopImg, holder.image)
        holder.tv_info.text = model.shopDesc

        holder.tv_name.text = model.shopName
        holder.tv_meony.text = "￥" + model.shopPrice
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val tv_meony = view.findViewById<TextView>(R.id.tv_meony)

    }


}
