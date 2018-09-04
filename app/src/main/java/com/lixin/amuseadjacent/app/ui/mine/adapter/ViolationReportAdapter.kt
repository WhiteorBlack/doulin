package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R

/**
 * 违规举报
 * flag 0我的违规，1我的举报
 * Created by Slingge on 2018/8/18
 */
class ViolationReportAdapter(val context: Context,val flag:Int) : RecyclerView.Adapter<ViolationReportAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_violation_report, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 13
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(flag==0){
            holder.image.visibility=View.GONE
            holder.tv_result.setTextColor(context.resources.getColor(R.color.red))
            holder.tv_result.text="未违规"
        }else{
            holder.image.visibility=View.VISIBLE
            holder.tv_result.setTextColor(context.resources.getColor(R.color.colorTheme))
            holder.tv_result.text="已举报"
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image=view.findViewById<ImageView>(R.id.image)
        val tv_result=view.findViewById<TextView>(R.id.tv_result)

    }

}