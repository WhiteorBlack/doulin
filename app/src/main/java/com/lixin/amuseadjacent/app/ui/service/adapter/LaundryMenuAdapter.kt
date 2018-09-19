package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsListModel
import java.util.ArrayList

/**
 * Created by Slingge on 2018/8/31
 */
class LaundryMenuAdapter(val context: Context, val menuList: ArrayList<ShopGoodsListModel.secondModel>) : RecyclerView.Adapter<LaundryMenuAdapter.ViewHolder>() {

    private var flag = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_text, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (flag >= 0) {
            if (flag == position) {
                holder.text.setTextColor(context.resources.getColor(R.color.white))
                holder.text.setBackgroundResource(R.drawable.bg_them30)
            } else {
                holder.text.setTextColor(Color.parseColor("#333333"))
                holder.text.setBackgroundResource(R.drawable.bg_white30)
            }
        }

        holder.text.text = menuList[position].secondCategoryName

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text = view.findViewById<TextView>(R.id.text)
    }


    fun setSelect(flag: Int) {
        this.flag = flag
        notifyDataSetChanged()
    }

}
