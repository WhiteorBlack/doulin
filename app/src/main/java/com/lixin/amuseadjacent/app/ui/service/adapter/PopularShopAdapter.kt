package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R


/**
 * 民间店铺
 * Created by Slingge on 2018/8/22
 */
class PopularShopAdapter(val context: Context) : RecyclerView.Adapter<PopularShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_popular_shop, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 12
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}
