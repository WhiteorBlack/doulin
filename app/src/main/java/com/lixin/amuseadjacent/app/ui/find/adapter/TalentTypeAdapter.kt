package com.lixin.amuseadjacent.app.ui.find.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R

/**
 * 达人
 * Created by Slingge on 2018/8/22
 */
class TalentTypeAdapter(val context: Context) : RecyclerView.Adapter<TalentTypeAdapter.ViewHolder>() {

    private var selectId = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_flow_talent_type, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 12
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (selectId >= 0) {
            if (selectId == position) {
                holder.tv_type.setTextColor(context.resources.getColor(R.color.white))
                holder.tv_type.setBackgroundResource(R.drawable.bg_them3)
            } else {
                holder.tv_type.setTextColor(context.resources.getColor(R.color.black))
                holder.tv_type.setBackgroundResource(R.drawable.bg_gray3)
            }
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_type = view.findViewById<TextView>(R.id.tv_type)
    }


    fun setPosition(selectId: Int) {
        this.selectId = selectId
        notifyDataSetChanged()
    }


}