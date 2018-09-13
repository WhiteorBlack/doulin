package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.mine.model.CouponMyModel

/**
 * 我的优惠券
 * flag //0未使用，1已使用，2已过期
 * Created by Slingge on 2018/8/18
 */
class CouponMyAdapter(val context: Context, val couponList: ArrayList<CouponMyModel.couponModel>, val flag: Int) : RecyclerView.Adapter<CouponMyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_coupon_my, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return couponList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (flag == 0) {
            holder.tv_money.setTextColor(context.resources.getColor(R.color.red))
            holder.tv_money.setTextColor(context.resources.getColor(R.color.red))

            holder.tv_type.visibility = View.VISIBLE
            holder.tv_tongyong.visibility = View.VISIBLE
            holder.tv_name.visibility = View.GONE
        } else {
            holder.tv_money.setTextColor(context.resources.getColor(R.color.black))
            holder.tv_money.setTextColor(context.resources.getColor(R.color.black))

            holder.tv_type.visibility = View.GONE
            holder.tv_tongyong.visibility = View.GONE
            holder.tv_name.visibility = View.VISIBLE
        }


        val model = couponList[position]

        holder.tv_money.text = model.securitiesPrice

        holder.tv_time.text = model.securitiesEndTime
        holder.tv_name.text = model.securitiesName

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val money = view.findViewById<TextView>(R.id.money)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_tongyong = view.findViewById<TextView>(R.id.tv_tongyong)
        val tv_type = view.findViewById<TextView>(R.id.tv_type)

        val tv_time = view.findViewById<TextView>(R.id.tv_time)
    }

}