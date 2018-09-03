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
 * 收藏
 * Created by Slingge on 2018/9/3
 */
class CollectionAdapter(val context: Context) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_interaction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 13
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position % 2 == 0) {
            holder.cl_1.visibility = View.VISIBLE
            holder.cl_2.visibility = View.GONE
        } else {
            holder.cl_1.visibility = View.GONE
            holder.cl_2.visibility = View.VISIBLE
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl_1 = view.findViewById<View>(R.id.cl_1)
        val cl_2 = view.findViewById<View>(R.id.cl_2)

        val line1 = view.findViewById<View>(R.id.line1)
        val line2 = view.findViewById<View>(R.id.line2)
        val line = view.findViewById<View>(R.id.line)

        val tv_type2 = view.findViewById<TextView>(R.id.tv_type2)
        val iv_del = view.findViewById<ImageView>(R.id.iv_del)

        init {
            line1.visibility = View.GONE
            line2.visibility = View.GONE
            line.visibility = View.VISIBLE

            tv_type2.visibility = View.VISIBLE
            iv_del.visibility = View.VISIBLE
        }
    }

}