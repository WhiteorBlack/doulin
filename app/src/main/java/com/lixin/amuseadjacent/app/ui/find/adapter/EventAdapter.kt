package com.lixin.amuseadjacent.app.ui.find.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.find.activity.EventDetailsActivity
import com.lixin.amuseadjacent.app.util.ImageLoaderUtil
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 活动
 * Created by Slingge on 2018/8/25.
 */
class EventAdapter(val context: Context) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_find, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 12
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        ImageLoader.getInstance().displayImage("", holder.image, ImageLoaderUtil.DIO5())

        holder.itemView.setOnClickListener { v ->
            MyApplication.openActivity(context, EventDetailsActivity::class.java)
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ll_image = view.findViewById<LinearLayout>(R.id.ll_image)
        val cl_2 = view.findViewById<ConstraintLayout>(R.id.cl_2)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)

        val image = view.findViewById<ImageView>(R.id.image)

        init {
            ll_image.visibility = View.GONE
            cl_2.visibility = View.VISIBLE
            tv_info.visibility = View.GONE
        }


    }


}