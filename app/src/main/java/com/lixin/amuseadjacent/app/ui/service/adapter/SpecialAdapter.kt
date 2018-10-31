package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.service.activity.CommodityDetailsActivity
import com.lixin.amuseadjacent.app.ui.service.model.SpecialModel
import com.lixin.amuseadjacent.app.ui.service.request.ShopCar_12412537
import com.lixin.amuseadjacent.app.util.PreviewPhoto
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader
import org.w3c.dom.Text
import java.util.ArrayList


/**
 * 专区
 * Created by Slingge on 2018/8/22
 */
class SpecialAdapter(val context: Context, var specialList: ArrayList<SpecialModel.dataModel>) : RecyclerView.Adapter<SpecialAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_specialarea, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return specialList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = specialList[position]
        ImageLoader.getInstance().displayImage(model.goodsImg, holder.image)
        model.num=model.count
        holder.tv_name.text = model.goodsName
        holder.tv_num.text = model.count.toString()
        if (TextUtils.isEmpty(model.goodsCuprice)) {
            holder.tv_money.text = "￥" + model.goodsPrice
        } else {
            holder.tv_money.text = "￥" + model.goodsCuprice
        }

        if (TextUtils.equals(model.optimizationId, "1")) {
            holder.tv_class.visibility = View.VISIBLE
        } else {
            holder.tv_class.visibility = View.GONE
        }

//        if (model.count < 1) {
//            holder.iv_reduce.visibility = View.INVISIBLE
//
//        } else {
//            holder.iv_reduce.visibility = View.VISIBLE
//        }


        if (model.count > 0) {
            holder.iv_reduce.visibility = View.VISIBLE
            holder.tv_num.visibility = View.VISIBLE
        } else {
            holder.iv_reduce.visibility = View.INVISIBLE
            holder.tv_num.visibility = View.INVISIBLE
        }


        holder.iv_add.setOnClickListener { v ->
            if (model.isSelect) {
                return@setOnClickListener
            }
//            model.num = model.num + 1
            ShopCar_12412537.addCar(model.goodsType, model.goodsId, "1", object : ShopCar_12412537.AddCarCallback {
                override fun addCar() {
//                    ToastUtil.showToast("加入购物车成功")
//                    holder.tv_num.visibility = View.VISIBLE
                    model.num = model.num + 1
                    model.count=model.count+1
                    notifyItemChanged(position + 1)
                }
            })
        }

        holder.iv_reduce.setOnClickListener { v ->

            ShopCar_12412537.addCar(model.goodsType, model.goodsId, "-1", object : ShopCar_12412537.AddCarCallback {
                override fun addCar() {
                    model.num = model.num - 1
                    model.count=model.count-1
                    notifyItemChanged(position + 1)
                }
            })
        }

        holder.itemView.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putSerializable("title", model.goodsName)
            bundle.putString("info", model.goodsDesc)
            if (TextUtils.isEmpty(model.goodsCuprice)) {
                bundle.putString("money", model.goodsPrice)
            } else {
                bundle.putString("money", model.goodsCuprice)
            }
            bundle.putString("image", model.goodsImg)
            MyApplication.openActivity(context, CommodityDetailsActivity::class.java, bundle)
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val iv_add = view.findViewById<ImageView>(R.id.iv_add)

        val tv_num = view.findViewById<TextView>(R.id.tv_num)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)

        val tv_class = view.findViewById<TextView>(R.id.tv_class)
        val iv_reduce = view.findViewById<ImageView>(R.id.iv_reduce)
    }


}
