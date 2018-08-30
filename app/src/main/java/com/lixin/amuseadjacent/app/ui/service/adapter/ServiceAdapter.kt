package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.lixin.amuseadjacent.R
import android.widget.RelativeLayout
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.service.activity.SpecialAreaActivity


/**
 * 服务
 * Created by Slingge on 2018/8/22
 */
class ServiceAdapter(val context: Context) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 12
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position % 3 == 1) {
            val lp = FrameLayout.LayoutParams(holder.cl_item.layoutParams)
            lp.setMargins(2, 0, 2, 0)
            holder.cl_item.layoutParams = lp
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl_item = view.findViewById<ConstraintLayout>(R.id.cl_item)
    }


}
