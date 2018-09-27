package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.mine.model.EffectDetailsModel

/**
 * 社区影响力
 * Created by Slingge on 2018/9/2.
 */
class EffectDetailsAdapter(val context: Context, val effList: ArrayList<EffectDetailsModel.dataModel>) : RecyclerView.Adapter<EffectDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_effect_details, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return effList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = effList[position]

        holder.tv_name.text = model.effectNumTitle
        holder.tv_eeffect.text = "+" + model.effectNum
        holder.tv_time.text = model.effectNumTime
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_eeffect = view.findViewById<TextView>(R.id.tv_eeffect)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
    }


}