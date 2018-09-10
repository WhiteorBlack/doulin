package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R

/**
 * 购物车商品
 * Created by Slingge on 2018/8/30
 */
class ShopCarDetailsAdapter(val context: Context) : RecyclerView.Adapter<ShopCarDetailsAdapter.ViewHolder>() {

    private var isEdit = false//true 编辑状态

    private var NUM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shop_car_details, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (isEdit) {//编辑状态
            holder.iv_del.visibility = View.VISIBLE
            holder.tv_plus.visibility = View.VISIBLE
            holder.num.visibility = View.VISIBLE
            holder.tv_reduce.visibility = View.VISIBLE

            holder.tv_num.visibility = View.GONE

        } else {
            holder.iv_del.visibility = View.GONE
            holder.tv_plus.visibility = View.GONE
            holder.num.visibility = View.GONE
            holder.tv_reduce.visibility = View.GONE

            holder.tv_num.visibility = View.VISIBLE
        }

        holder.tv_plus.setOnClickListener { v ->
            NUM++
            holder.num.text = NUM.toString()
        }
        holder.tv_reduce.setOnClickListener { v ->
            if (NUM == 0) {
                return@setOnClickListener
            }
            NUM--
            holder.num.text = NUM.toString()
        }


        if(position==3){
            holder.line2.visibility=View.INVISIBLE
        }

    }

    fun setEdite(isEdit: Boolean) {
        this.isEdit = isEdit
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_num = view.findViewById<TextView>(R.id.tv_num)
        val iv_del = view.findViewById<ImageView>(R.id.iv_del)

        val tv_plus = view.findViewById<TextView>(R.id.tv_plus)
        val num = view.findViewById<TextView>(R.id.num)
        val tv_reduce = view.findViewById<TextView>(R.id.tv_reduce)


        val line2=view.findViewById<View>(R.id.line2)
    }


}
