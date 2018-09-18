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
 * 店铺右菜单
 * Created by Slingge on 2018/8/30
 */
class ShopRightAdapter(val context: Context, val titleList: String, val rightList: ArrayList<ShopGoodsModel.dataModel>, val addShopCar: AddShopCar) : RecyclerView.Adapter<ShopRightAdapter.ViewHolder>() {

    private var flag = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shop_right2, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rightList.size
    }

    interface AddShopCar {
        fun addCar(position: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.tv_title.text = titleList
            holder.tv_title.visibility = View.VISIBLE
        } else {
            holder.tv_title.visibility = View.GONE
        }
        val model = rightList[position]
        holder.tv_name.text = model.goodsName
        ImageLoader.getInstance().displayImage(model.goodsImg, holder.image)
        if (TextUtils.isEmpty(model.goodsCuprice)) {
            holder.tv_money.text = " ￥：" + model.goodsPrice
        } else {
            holder.tv_money.text = " ￥：" + model.goodsCuprice
        }

        holder.tv_volume.text = "销量：" + model.goodsSallnum

        if (model.goodsNum <= 0) {
            holder.tv_num.visibility = View.GONE
        } else {
            holder.tv_num.visibility = View.VISIBLE
            holder.tv_num.text = model.goodsNum.toString()
        }


        holder.iv_add.setOnClickListener { v ->
            addShopCar.addCar(position)
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
    }


}
