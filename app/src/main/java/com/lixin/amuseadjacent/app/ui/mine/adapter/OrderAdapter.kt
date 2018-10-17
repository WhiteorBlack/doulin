package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.activity.order.EvaluateActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.order.OrderActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.order.OrderDetailsActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.order.RefundActivity
import com.lixin.amuseadjacent.app.ui.mine.model.MyOrderModel
import com.lixin.amuseadjacent.app.ui.mine.request.MyOrder_144155
import com.lixin.amuseadjacent.app.ui.service.activity.PaymentActivity
import com.lixin.amuseadjacent.app.ui.service.activity.ShopCarActivity
import com.lixin.amuseadjacent.app.util.DoubleCalculationUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import java.util.ArrayList

/**
 * 订单
 * orderType 0/新鲜果蔬 1洗衣洗鞋 2超市便利
 *  orderState //1待付款,2待送货,3待收货,4待取货,5清洗中,6待归还,7归还中,8退款中,9已退款,10待评价,11已完成12已取消
 * Created by Slingge on 2018/9/3
 */
class OrderAdapter(val context: Activity, val orderList: ArrayList<MyOrderModel.dataModel>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = orderList[position]

        var num = 0//集合商品数量
        for (i in 0 until model.orderCommodity.size) {
            num = DoubleCalculationUtil.add(model.orderCommodity[i].commodityBuyNum.toDouble(), num.toDouble()).toInt()
        }
        holder.tv_info.text = num.toString() + "件商品  优惠券-" + DoubleCalculationUtil.sub(model.oderAllPrice.toDouble(), model.oderPayPrice.toDouble()) + "元  合计：" + model.oderPayPrice + "元"

        val orderCommodityAdapter = OrderCommodityAdapter(context, position, model.orderNum, model.orderCommodity)
        holder.rv_comment.adapter = orderCommodityAdapter

        holder.tv_orderNum.text ="订单号："+ model.orderNum

        var orderState = model.orderState//1待付款,2待送货,3待收货,4待取货,5清洗中,6待归还,7归还中,8退款中,9已退款,10待评价,11已完成 12已取消

        holder.cl_item.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("num", model.orderNum)
            bundle.putInt("position", position)
            MyApplication.openActivityForResult(context, OrderDetailsActivity::class.java, bundle,StaticUtil.OrderDetailsResult)
        }

        if (orderState == "1") {//1待付款
            holder.tv_type.text = "待付款"
            holder.tv_pay.text = "立即付款"
            holder.tv_again.text = "取消"

            holder.tv_again.visibility = View.VISIBLE
            holder.tv_pay.visibility = View.VISIBLE
            holder.tv_refund.visibility = View.GONE
            holder.tv_del.visibility = View.GONE
        } else if (orderState == "2") {//2待送货
            holder.tv_type.text = "待送货"
            holder.tv_pay.text = "确认送达"
            holder.tv_again.text="再来一单"

            holder.tv_again.visibility = View.VISIBLE
            holder.tv_pay.visibility = View.GONE
            holder.tv_refund.visibility = View.VISIBLE
            holder.tv_del.visibility = View.GONE
        } else if (orderState == "3") {//3待收货
            holder.tv_type.text = "待收货"
            holder.tv_pay.text = "确认收货"
            holder.tv_refund.text = "    退款    "
            holder.tv_again.text="再来一单"

            holder.tv_again.visibility = View.VISIBLE
            holder.tv_pay.visibility = View.VISIBLE
            holder.tv_refund.visibility = View.VISIBLE
            holder.tv_del.visibility = View.GONE
        } else if (orderState == "4") {//4待取货
            holder.tv_type.text = "待取货"
            holder.tv_pay.text = "确认取件"
            holder.tv_refund.text = "    退款    "
            holder.tv_again.text="再来一单"

            holder.tv_again.visibility = View.VISIBLE
            holder.tv_pay.visibility = View.VISIBLE
            holder.tv_refund.visibility = View.VISIBLE
            holder.tv_del.visibility = View.GONE
        } else if (orderState == "5") {//5清洗中
            holder.tv_type.text = "清洗中"

            holder.tv_again.visibility = View.GONE
            holder.tv_pay.visibility = View.GONE
            holder.tv_refund.visibility = View.GONE
        } else if (orderState == "6") {//6带归还
            holder.tv_type.text = "待归还"
            holder.tv_again.text="再来一单"
            holder.tv_pay.text = "归还"

            holder.tv_again.visibility = View.VISIBLE
            holder.tv_pay.visibility = View.VISIBLE
            holder.tv_refund.visibility = View.GONE
            holder.tv_del.visibility = View.GONE
        } else if (orderState == "7") {//7归还中
            holder.tv_type.text = "归还中"
            holder.tv_again.text="再来一单"
            holder.tv_pay.text = "确认收货"

            holder.tv_again.visibility = View.VISIBLE
            holder.tv_pay.visibility = View.VISIBLE
            holder.tv_refund.visibility = View.GONE
            holder.tv_del.visibility = View.GONE
        } else if (orderState == "8") {//8退款中
            holder.tv_type.text = "退款中"

            holder.tv_pay.text = "确认收货"
            holder.tv_again.visibility = View.VISIBLE
            holder.tv_pay.visibility = View.VISIBLE
            holder.tv_refund.visibility = View.GONE
            holder.tv_del.visibility = View.GONE
        } else if (orderState == "9") {//9已退款
            holder.tv_type.text = "已退款"
            holder.tv_again.text="再来一单"

            holder.tv_again.visibility = View.VISIBLE
            holder.tv_pay.visibility = View.GONE
            holder.tv_refund.visibility = View.GONE
            holder.tv_del.visibility = View.GONE
        } else if (orderState == "10") {//10待评价
            holder.tv_type.text = "待评价"
            holder.tv_pay.text = "去评价"
            holder.tv_again.text="再来一单"

            holder.tv_again.visibility = View.VISIBLE
            holder.tv_pay.visibility = View.VISIBLE
            holder.tv_refund.visibility = View.GONE
            holder.tv_del.visibility = View.GONE
        } else if (orderState == "11") {//11已完成
            holder.tv_type.text = "已完成"
            holder.tv_again.text="再来一单"

            holder.tv_again.visibility = View.VISIBLE
            holder.tv_pay.visibility = View.GONE
            holder.tv_refund.visibility = View.GONE
            holder.tv_del.visibility = View.GONE
        } else if (orderState == "12") {//12已取消
            holder.tv_type.text = "已取消"
            holder.tv_again.text="再来一单"
            holder.tv_again.visibility = View.VISIBLE
            holder.tv_pay.visibility = View.GONE
            holder.tv_refund.visibility = View.GONE

            holder.tv_del.visibility = View.VISIBLE
        }

        holder.tv_pay.setOnClickListener { v ->
            if (orderState == "10") {//评价
                val bundle = Bundle()
                bundle.putInt("position", position)
                bundle.putString("num", model.orderNum)
                MyApplication.openActivityForResult(context, EvaluateActivity::class.java, bundle, StaticUtil.EvaluateResult)
            } else if (orderState == "1") {//付款
                val bundle = Bundle()
                bundle.putString("oderNum", model.orderNum)
                bundle.putString("balance", StaticUtil.balance)
                bundle.putString("payMoney", model.oderPayPrice)
                MyApplication.openActivity(context, PaymentActivity::class.java, bundle)
            } else if (orderState == "3" || orderState == "7" || orderState == "8") {//确认收货
                ProgressDialog.showDialog(context)
                MyOrder_144155.ConfirmOrder(null, model.orderNum, position, object : MyOrder_144155.OrderEditCallBack {
                    override fun cancel() {
                        orderList[position].orderState = "10"
                        notifyDataSetChanged()
                    }
                })
            } else if (orderState == "4") {//归还，洗衣洗鞋，确认已取件
                ProgressDialog.showDialog(context)
                MyOrder_144155.TakepartLaundry(null, model.orderNum, position, object : MyOrder_144155.OrderEditCallBack {
                    override fun cancel() {
                        orderList[position].orderState = "5"
                        notifyDataSetChanged()
                    }
                })
            } else if (orderState == "6") {//归还，洗衣洗鞋，洗完之后，点击开始送还
                ProgressDialog.showDialog(context)
                MyOrder_144155.ReturnLaundry(null, model.orderNum, position, object : MyOrder_144155.OrderEditCallBack {
                    override fun cancel() {
                        orderList[position].orderState = "7"
                        notifyDataSetChanged()
                    }
                })
            }
        }


        holder.tv_again.setOnClickListener { v ->
            if (orderState == "1") {//取消订单
                ProgressDialog.showDialog(context)
                MyOrder_144155.CancelOrder(null, model.orderNum, position, object : MyOrder_144155.OrderEditCallBack {
                    override fun cancel() {
                        orderList[position].orderState = "12"
                       notifyDataSetChanged()
                    }
                })
            } else {//再来一单
                ProgressDialog.showDialog(context)
                MyOrder_144155.againOrder(context, model.orderNum)
            }
        }

        holder.tv_refund.setOnClickListener { v ->
            //退款
            if (orderState == "2" || orderState == "3" || orderState == "4") {
                val bundle = Bundle()
                bundle.putString("num", model.orderNum)
                bundle.putInt("position", position)
                MyApplication.openActivityForResult(context, RefundActivity::class.java, bundle, StaticUtil.RefundResult)
            }
        }

        holder.tv_del.setOnClickListener{v->
            ProgressDialog.showDialog(context)
            MyOrder_144155.delOrder( model.orderNum,object :MyOrder_144155.OrderEditCallBack{
                override fun cancel() {
                    orderList.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rv_comment = view.findViewById<RecyclerView>(R.id.rv_comment)

        val tv_del = view.findViewById<TextView>(R.id.tv_del)

        val tv_pay = view.findViewById<TextView>(R.id.tv_pay)
        val tv_again = view.findViewById<TextView>(R.id.tv_again)
        val tv_refund = view.findViewById<TextView>(R.id.tv_refund)

        val tv_orderNum = view.findViewById<TextView>(R.id.tv_orderNum)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)

        val tv_type = view.findViewById<TextView>(R.id.tv_type)

        val cl_item = view.findViewById<ConstraintLayout>(R.id.cl_item)

        init {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_comment.layoutManager = linearLayoutManager
        }
    }

}