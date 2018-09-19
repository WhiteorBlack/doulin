package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
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
 * 洗衣洗鞋
 * Created by Slingge on 2018/8/22
 */
class LaundryAdapter(val context: Context, val goodList: ArrayList<ShopGoodsModel.dataModel>) : RecyclerView.Adapter<LaundryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_laundry, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return goodList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = goodList[position]

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
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)
        val image = view.findViewById<ImageView>(R.id.image)
    }


}
