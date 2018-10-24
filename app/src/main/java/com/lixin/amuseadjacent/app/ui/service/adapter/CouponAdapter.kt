package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.service.model.CouponModel
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * Created by Slingge on 2018/8/30
 */
class CouponAdapter(val context: Context, val couponList: ArrayList<CouponModel.couponModel>) : RecyclerView.Adapter<CouponAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_coupon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return couponList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val model = couponList[position]

         holder.tv_money.text = model.securitiesPrice
         holder.tv_name.text = model.securitiesName
         holder.tv_time.text = model.securitiesEndTime

         ImageLoader.getInstance().displayImage(model.securitiesImg, holder.image)

         if (model.isSelect) {
             holder.cl_item.setBackgroundResource(R.drawable.bg_gray1)
         } else {
             holder.cl_item.setBackgroundResource(R.drawable.bg_white1)
         }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl_item = view.findViewById<ConstraintLayout>(R.id.cl_item)

        val image = view.findViewById<ImageView>(R.id.image)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)
    }


}
