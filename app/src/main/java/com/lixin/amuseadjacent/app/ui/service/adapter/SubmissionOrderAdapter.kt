package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 确认订单数据
 * Created by Slingge on 2018/8/30
 */
class SubmissionOrderAdapter(val context: Context, val orderList: ArrayList<ShopGoodsModel.dataModel>) : RecyclerView.Adapter<SubmissionOrderAdapter.ViewHolder>() {

    private var flag = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shop_right2, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = orderList[position]

        model.UnitPrice = if (TextUtils.isEmpty(model.goodsCuprice)) {
            model.goodsPrice.toDouble()
        } else {
            model.goodsCuprice.toDouble()
        }

        if (model.money == 0.0) {
            model.money = model.UnitPrice
        }

        var money = model.money
        holder.tv_money.text = " ￥：$money"


        holder.tv_name.text = model.goodsName
        ImageLoader.getInstance().displayImage(model.goodsImg, holder.image)

        holder.tv_volume.text = "销量：" + model.goodsSallnum

        if (model.goodsNum <= 0) {
            holder.tv_num.visibility = View.GONE
        } else {
            holder.tv_num.visibility = View.VISIBLE
            holder.tv_num.text = "x" + model.goodsNum.toString()
        }


    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)

        val image = view.findViewById<ImageView>(R.id.image)
        val tv_volume = view.findViewById<TextView>(R.id.tv_volume)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)
        val iv_add = view.findViewById<ImageView>(R.id.iv_add)
        val tv_num = view.findViewById<TextView>(R.id.tv_num)

        init {
            tv_title.visibility = View.GONE
            tv_volume.visibility = View.GONE
            iv_add.visibility = View.INVISIBLE

        }
    }


}
