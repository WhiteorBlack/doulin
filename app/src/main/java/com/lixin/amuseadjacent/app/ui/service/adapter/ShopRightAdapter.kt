package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R

/**
 * 店铺右菜单
 * Created by Slingge on 2018/8/30
 */
class ShopRightAdapter(val context: Context) : RecyclerView.Adapter<ShopRightAdapter.ViewHolder>() {

    private var flag = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shop_right, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val rightAdapter2=ShopRightAdapter2(context)
        holder.rv_right.adapter=rightAdapter2

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val rv_right=view.findViewById<RecyclerView>(R.id.rv_right)

        init {
            val linearLayoutManager=LinearLayoutManager(context)
            linearLayoutManager.orientation=LinearLayoutManager.VERTICAL
            rv_right.layoutManager=linearLayoutManager
        }

    }



}
