package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R

/**
 * 个人主页 互动
 * Created by Slingge on 2018/8/18
 */
class InteractionAdapter(val context: Context) : RecyclerView.Adapter<InteractionAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return 12
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(position%2==0){
            holder.cl_1.visibility=View.VISIBLE
            holder.cl_2.visibility=View.GONE
        }else{
            holder.cl_1.visibility=View.GONE
            holder.cl_2.visibility=View.VISIBLE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_interaction, parent, false)
        return ViewHolder(view)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl_1=view.findViewById<ConstraintLayout>(R.id.cl_1)
        val cl_2=view.findViewById<ConstraintLayout>(R.id.cl_2)

    }


}