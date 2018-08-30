package com.lixin.amuseadjacent.app.ui.find.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.find.activity.BangDetailsActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.ImageAdapter

/**
 * 帮帮
 * Created by Slingge on 2018/8/22
 */
class BangAdapter(val context: Context) : RecyclerView.Adapter<BangAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_find, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 12
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position % 3 == 0) {
            holder.image0.visibility = View.VISIBLE

            holder.image1.visibility = View.GONE
            holder.image2.visibility = View.GONE
            holder.rv_image.visibility = View.GONE
        } else if (position % 3 == 1) {
            holder.image0.visibility = View.GONE

            holder.image1.visibility = View.VISIBLE
            holder.image2.visibility = View.VISIBLE
            holder.rv_image.visibility = View.GONE
        } else {
            holder.image0.visibility = View.GONE

            holder.image1.visibility = View.GONE
            holder.image2.visibility = View.GONE
            holder.rv_image.visibility = View.VISIBLE

            val imageAdapter = ImageAdapter(context)
            holder.rv_image.adapter = imageAdapter
        }


        holder.itemView.setOnClickListener { v ->
            MyApplication.openActivity(context, BangDetailsActivity::class.java)
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ll_image = view.findViewById<LinearLayout>(R.id.ll_image)
        val image0 = view.findViewById<ImageView>(R.id.image0)
        val image1 = view.findViewById<ImageView>(R.id.image1)
        val image2 = view.findViewById<ImageView>(R.id.image2)

        val rv_image = view.findViewById<RecyclerView>(R.id.rv_image)

        init {
            ll_image.visibility = View.VISIBLE

            val linearLayoutManager = GridLayoutManager(context,3)
            rv_image.layoutManager = linearLayoutManager
        }
    }


}
