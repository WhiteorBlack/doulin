package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.mine.activity.order.OrderDetailsActivity
import com.lixin.amuseadjacent.app.ui.mine.model.MyOrderModel
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 订单商品
 * Created by Slingge on 2018/9/3
 */
class OrderCommodityAdapter(val context: Context, val position: Int, val num: String?, val list: ArrayList<MyOrderModel.orderModel>) : RecyclerView.Adapter<OrderCommodityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order_commodity, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]
        holder.tv_name.text = model.commodityTitle
        ImageLoader.getInstance().displayImage(model.commodityPic, holder.image)
        holder.tv_num.text = "x" + model.commodityBuyNum
        holder.tv_money.text = "￥" + model.commodityPrice

        holder.itemView.setOnClickListener { v ->
            if (num != null) {
                val bundle = Bundle()
                bundle.putString("num", num)
                bundle.putInt("position", position)
                MyApplication.openActivity(context, OrderDetailsActivity::class.java, bundle)
            }
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)

        val tv_money = view.findViewById<TextView>(R.id.tv_money)
        val tv_num = view.findViewById<TextView>(R.id.tv_num)

    }

}