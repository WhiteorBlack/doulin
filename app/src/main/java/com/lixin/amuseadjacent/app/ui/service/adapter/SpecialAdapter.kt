package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.service.model.SpecialModel
import com.nostra13.universalimageloader.core.ImageLoader
import java.util.ArrayList


/**
 * 专区
 * Created by Slingge on 2018/8/22
 */
class SpecialAdapter(val context: Context, var specialList: ArrayList<SpecialModel.dataModel>) : RecyclerView.Adapter<SpecialAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_specialarea, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return specialList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = specialList[position]
        ImageLoader.getInstance().displayImage(model.goodsImg, holder.image)
        holder.tv_name.text = model.goodsName
        holder.tv_money.text = "￥" + model.goodsPrice
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)
    }


}
