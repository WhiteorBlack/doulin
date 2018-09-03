package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.mine.activity.OrderDetailsActivity

/**
 * 订单
 * Created by Slingge on 2018/9/3
 */
class OrderAdapter(val context: Context) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 13
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val orderCommodityAdapter = OrderCommodityAdapter(context)
        holder.rv_comment.adapter = orderCommodityAdapter


        holder.itemView.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putInt("flag", position)
            MyApplication.openActivity(context, OrderDetailsActivity::class.java, bundle)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rv_comment = view.findViewById<RecyclerView>(R.id.rv_comment)


        /* val line1 = view.findViewById<View>(R.id.line1)
         val line2 = view.findViewById<View>(R.id.line2)
         val line = view.findViewById<View>(R.id.line)

         val tv_type2 = view.findViewById<TextView>(R.id.tv_type2)
         val iv_del = view.findViewById<ImageView>(R.id.iv_del)*/

        init {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_comment.layoutManager = linearLayoutManager
        }
    }

}