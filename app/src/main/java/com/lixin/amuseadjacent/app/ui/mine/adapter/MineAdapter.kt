package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R

/**
 * 个人主页动态
 * flag 0常用功能，1更多功能
 * Created by Slingge on 2018/8/18
 */
class MineAdapter(val context: Context, val flag: Int) : RecyclerView.Adapter<MineAdapter.ViewHolder>() {

    private var usedImage = intArrayOf(R.drawable.ic_userd1, R.drawable.ic_userd2, R.drawable.ic_userd3, R.drawable.ic_userd4, R.drawable.ic_userd5, R.drawable.ic_userd6)
    private var userText = arrayOf("钱包", "优惠券", "购物车", "订单", "收藏", "实名认证")

    private var moreImage = intArrayOf(R.drawable.ic_more1, R.drawable.ic_more2, R.drawable.ic_more3, R.drawable.ic_more4, R.drawable.ic_more5)
    private var moreText = arrayOf("帮助", "邀请好友", "更换社区", "App反馈", "关于逗邻")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mine, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (flag == 0) {
            return usedImage.size
        } else {
            return moreImage.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (flag == 0) {
            holder.image.setImageResource(usedImage[position])
            holder.tv_name.text = userText[position]
        } else {
            holder.image.setImageResource(moreImage[position])
            holder.tv_name.text = moreText[position]
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
    }

}