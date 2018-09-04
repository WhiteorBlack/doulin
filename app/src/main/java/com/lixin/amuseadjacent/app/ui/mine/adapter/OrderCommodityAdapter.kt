package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
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

/**
 * 订单商品
 * Created by Slingge on 2018/9/3
 */
class OrderCommodityAdapter(val context: Context) : RecyclerView.Adapter<OrderCommodityAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order_commodity, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener { v ->
            MyApplication.openActivity(context, OrderDetailsActivity::class.java)
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        /*val line1 = view.findViewById<View>(R.id.line1)
        val line2 = view.findViewById<View>(R.id.line2)
        val line = view.findViewById<View>(R.id.line)

        val tv_type2 = view.findViewById<TextView>(R.id.tv_type2)
        val iv_del = view.findViewById<ImageView>(R.id.iv_del)
*/

    }

}