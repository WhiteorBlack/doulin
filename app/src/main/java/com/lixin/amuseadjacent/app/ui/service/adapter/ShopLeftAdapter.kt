package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsListModel

/**
 * 店铺左菜单
 * Created by Slingge on 2018/8/30
 */
class ShopLeftAdapter(val context: Context, var leftList: ArrayList<ShopGoodsListModel.dataModel>) : RecyclerView.Adapter<ShopLeftAdapter.ViewHolder>() {

    private var flag = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shop_left, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return leftList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (flag == position) {
            holder.line.setBackgroundColor(context.resources.getColor(R.color.colorTheme))
            holder.tv_name.setBackgroundColor(context.resources.getColor(R.color.white))
            holder.tv_name.setTextColor(context.resources.getColor(R.color.colorTheme))
        } else {
            holder.line.setBackgroundColor(context.resources.getColor(R.color.gray2))
            holder.tv_name.setBackgroundColor(context.resources.getColor(R.color.gray2))
            holder.tv_name.setTextColor(context.resources.getColor(R.color.black))
        }

        holder.tv_name.text = leftList[position].firstCategoryName

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val line = view.findViewById<View>(R.id.line)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
    }

    fun setSelect(flag: Int) {
        this.flag = flag
        notifyDataSetChanged()
    }

}
