package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.util.AbStrUtil

/**
 * 屏蔽
 * Created by Slingge on 2018/8/18
 */
class ShieldAdapter(val context: Context) : RecyclerView.Adapter<ShieldAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 13
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_follow.visibility = View.VISIBLE
    }


  inner  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_follow = view.findViewById<TextView>(R.id.tv_follow)

        init {
            tv_follow.text="移除"
            AbStrUtil.setDrawableLeft(context,-1,tv_follow,0)
        }
    }

}