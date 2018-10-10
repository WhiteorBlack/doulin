package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.bingoogolapple.badgeview.BGABadgeTextView
import com.google.gson.Gson
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.ui.service.request.ShopCar_12412537
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader


/**
 * 洗衣洗鞋
 * Created by Slingge on 2018/8/22
 */
class LaundryAdapter(val context: Context, val goodList: ArrayList<ShopGoodsModel.dataModel>) : RecyclerView.Adapter<LaundryAdapter.ViewHolder>() {

    interface AddShopCar {
        fun addCar(position: Int)
    }

    private var addShopCar: AddShopCar? = null
    fun setAddShopCar(addShopCar: AddShopCar) {
        this.addShopCar = addShopCar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_laundry, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return goodList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        abLog.e("适配器商品.......", Gson().toJson(goodList[position]))
        goodList[position].UnitPrice = if (TextUtils.isEmpty(goodList[position].goodsCuprice)) {
            goodList[position].goodsPrice.toDouble()
        } else {
            goodList[position].goodsCuprice.toDouble()
        }

        if (goodList[position].money == 0.0) {
            goodList[position].money = goodList[position].UnitPrice
        }

        val money = goodList[position].money
        holder.tv_money.text = " ￥$money"

        holder.tv_name.text = goodList[position].goodsName
        ImageLoader.getInstance().displayImage(goodList[position].goodsImg, holder.image)

        if (goodList[position].isSelect) {
            holder.tv_msgNum.visibility = View.VISIBLE
            MyApplication.setRedNum(holder.tv_msgNum, goodList[position].goodsNum)
        } else {
            holder.tv_msgNum.visibility = View.GONE
        }

        abLog.e("数量.......", goodList[position].goodsNum.toString())

        holder.itemView.setOnClickListener { v ->
            addShopCar!!.addCar(position)
            ShopCar_12412537.addCar("1", goodList[position].goodsId, "1", object : ShopCar_12412537.AddCarCallback {
                override fun addCar() {
                }
            })
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_msgNum = view.findViewById<BGABadgeTextView>(R.id.tv_msgNum)
    }


}
