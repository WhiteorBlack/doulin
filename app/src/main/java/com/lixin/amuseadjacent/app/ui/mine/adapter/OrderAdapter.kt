package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.mine.activity.order.EvaluateActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.order.OrderDetailsActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.order.RefundActivity
import com.lixin.amuseadjacent.app.ui.service.activity.ShopCarActivity

/**
 * 订单
 * orderType 0/新鲜果蔬 1洗衣洗鞋 2超市便利
 *  orderState //1待付款,2待送货,3待收货,4待取货,5清洗中,6待归还,7归还中,8退款中,9已退款,10待评价,11已完成
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

        var orderState = -1

        holder.itemView.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putInt("flag", position)
            MyApplication.openActivity(context, OrderDetailsActivity::class.java, bundle)
        }

        if (position % 3 == 0) {
            orderState = 1
            holder.tv_pay.text = "立即付款"
        } else if (position % 3 == 1) {
            orderState = 3
            holder.tv_pay.text = "确认收货"
        } else if (position % 3 == 2) {
            orderState = 10
            holder.tv_pay.text = " 去评价 "
        }


        if (orderState == 1) {//1待付款

        } else if (orderState == 2) {//2待送货

        } else if (orderState == 3) {//3待收货

        } else if (orderState == 4) {//4待取货

        } else if (orderState == 5) {//5清洗中

        } else if (orderState == 6) {//6待归还

        } else if (orderState == 7) {//7归还中

        } else if (orderState == 8) {//8退款中

        } else if (orderState == 9) {//9已退款

        } else if (orderState == 10) {//10待评价

        } else if (orderState == 11) {//11已完成

        }

        holder.tv_pay.setOnClickListener { v ->
            if (orderState == 10) {//评价
                MyApplication.openActivity(context, EvaluateActivity::class.java)
            } else if (orderState == 1) {//付款

            } else if (orderState == 3) {//确认收货

            }

        }


        holder.tv_again.setOnClickListener { v ->
            //再来一单
            MyApplication.openActivity(context, ShopCarActivity::class.java)
        }

        holder.tv_refund.setOnClickListener { v ->
            //退款
            MyApplication.openActivity(context, RefundActivity::class.java)
        }


    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rv_comment = view.findViewById<RecyclerView>(R.id.rv_comment)

        val tv_pay = view.findViewById<TextView>(R.id.tv_pay)
        val tv_again = view.findViewById<TextView>(R.id.tv_again)
        val tv_refund = view.findViewById<TextView>(R.id.tv_refund)

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