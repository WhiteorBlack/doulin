package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R

/**
 * 店铺左菜单
 * Created by Slingge on 2018/8/30
 */
class ShopRightAdapter2(val context: Context) : RecyclerView.Adapter<ShopRightAdapter2.ViewHolder>() {

    private var flag = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shop_right2, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val line = view.findViewById<View>(R.id.line)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
    }



}
