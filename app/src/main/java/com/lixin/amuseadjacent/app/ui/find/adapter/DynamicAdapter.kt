package com.lixin.amuseadjacent.app.ui.find.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicDetailsActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.ImageAdapter

/**
 * 达人
 * flag 0全部，1关注
 * Created by Slingge on 2018/8/22
 */
class DynamicAdapter(val context: Context, val flag: Int) : RecyclerView.Adapter<DynamicAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dynamic, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 24
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position % 2 == 0) {
            holder.ll_image.visibility = View.VISIBLE
            holder.rv_image.visibility = View.GONE
            holder.video.visibility = View.GONE
        } else if (position % 3 == 0) {
            holder.ll_image.visibility = View.GONE
            holder.video.visibility = View.GONE
            holder.rv_image.visibility = View.VISIBLE

            val list=ArrayList<String>()
            val imageAdapter = ImageAdapter(context,list)
            holder.rv_image.adapter = imageAdapter

        } else {
            holder.ll_image.visibility = View.GONE
            holder.rv_image.visibility = View.GONE
            holder.video.visibility = View.VISIBLE
        }


        holder.itemView.setOnClickListener { v ->
            MyApplication.openActivity(context, DynamicDetailsActivity::class.java)
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ll_image = view.findViewById<LinearLayout>(R.id.ll_image)
        val rv_image = view.findViewById<RecyclerView>(R.id.rv_image)
        val video = view.findViewById<View>(R.id.video)

        init {
            val gridLayoutManager = GridLayoutManager(context, 3)
            rv_image.layoutManager = gridLayoutManager
        }

    }


}
