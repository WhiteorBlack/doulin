package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.lixin.amuseadjacent.R

/**
 * 提现规则
 * Created by Slingge on 2018/9/2.
 */
class RuleAdapter(val context: Context) : RecyclerView.Adapter<RuleAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rule, parent, false)
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