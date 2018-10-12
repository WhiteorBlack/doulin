package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ActionMenuView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.mine.model.CouponMyModel
import com.lixin.amuseadjacent.app.ui.service.activity.LaundryActivity
import com.lixin.amuseadjacent.app.ui.service.activity.OfficialShopActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil

/**
 * 我的优惠券
 * flag //0未使用，1已使用，2已过期
 * Created by Slingge on 2018/8/18
 */
class CouponMyAdapter(val context: Activity, val couponList: ArrayList<CouponMyModel.couponModel>, val flag: Int
                      , val type: Int) : RecyclerView.Adapter<CouponMyAdapter.ViewHolder>() {

    private var totalMoney = 0.0

    fun setTotalMoney(totalMoney: Double) {
        this.totalMoney = totalMoney
    }

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

            holder.tv_use.visibility = View.VISIBLE
            holder.tv_type.visibility = View.VISIBLE
            holder.tv_tongyong.visibility = View.VISIBLE
            holder.tv_name.visibility = View.GONE
        } else {
            holder.tv_money.setTextColor(context.resources.getColor(R.color.black))
            holder.tv_money.setTextColor(context.resources.getColor(R.color.black))

            holder.tv_use.visibility = View.GONE
            holder.tv_type.visibility = View.GONE
            holder.tv_tongyong.visibility = View.GONE
            holder.tv_name.visibility = View.VISIBLE
        }


        val model = couponList[position]

        holder.tv_money.text = model.securitiesPrice

        holder.tv_time.text = model.securitiesEndTime
        holder.tv_name.text = model.securitiesName

        holder.tv_subtraction.text = "满" + model.securitiesMoney + "可用"

        holder.itemView.setOnClickListener { v ->
            if (type == -1) {
                return@setOnClickListener
            }
            if (flag == 0) {//选择优惠券
                if (totalMoney < model.securitiesMoney.toDouble()) {
                    ToastUtil.showToast("满" + model.securitiesMoney + "可用")
                    return@setOnClickListener
                }

                val bundle = Bundle()
                bundle.putSerializable("model", model)
                val intent = Intent()
                intent.putExtras(bundle)
                context.setResult(1, intent)
                context.finish()
            }
        }

        holder.tv_use.setOnClickListener { v ->
            val bundle = Bundle()
            if (model.securitiesType == "0") {//0超市便利 1洗衣洗鞋 2新鲜果蔬
                bundle.putString("type", "1")
                MyApplication.openActivity(context, OfficialShopActivity::class.java, bundle)
            } else if (model.securitiesType == "1") {
                MyApplication.openActivity(context, LaundryActivity::class.java, bundle)
            } else if (model.securitiesType == "2") {
                bundle.putString("type", "0")
                MyApplication.openActivity(context, OfficialShopActivity::class.java, bundle)
            }
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val money = view.findViewById<TextView>(R.id.money)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_tongyong = view.findViewById<TextView>(R.id.tv_tongyong)
        val tv_type = view.findViewById<TextView>(R.id.tv_type)
        val tv_use = view.findViewById<TextView>(R.id.tv_use)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)

        val tv_subtraction = view.findViewById<TextView>(R.id.tv_subtraction)
    }

}