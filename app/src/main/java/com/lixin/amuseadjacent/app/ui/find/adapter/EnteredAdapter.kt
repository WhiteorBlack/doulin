package com.lixin.amuseadjacent.app.ui.find.adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.find.model.EventDetailsModel
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 达人
 * Created by Slingge on 2018/8/22
 */
class EnteredAdapter(val context: Context, val list: ArrayList<EventDetailsModel.signModel>) : RecyclerView.Adapter<EnteredAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_entered, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]
        ImageLoader.getInstance().displayImage(model.userImg, holder.iv_header)
        holder.tv_num.text = (position + 1).toString() + "."
        holder.tv_name.text = model.userName
        holder.tv_peoNum.text = "人数：" + model.signNum
        holder.tv_age.text = model.userAge
        if (model.userSex == "0") {//女
            holder.tv_age.setBackgroundResource(R.drawable.bg_girl8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, holder.tv_age, 3)
        } else {
            holder.tv_age.setBackgroundResource(R.drawable.bg_boy8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy, holder.tv_age, 3)
        }

        holder.iv_header.setOnClickListener { v->
            val bundle=Bundle()
            bundle.putString("auid",model.userId)
            bundle.putString("isAttention","0")
            MyApplication.openActivity(context,PersonalHomePageActivity::class.java,bundle)
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_num = view.findViewById<TextView>(R.id.tv_num)
        val iv_header = view.findViewById<CircleImageView>(R.id.iv_header)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_age = view.findViewById<TextView>(R.id.tv_age)
        val tv_peoNum = view.findViewById<TextView>(R.id.tv_peoNum)
    }


}
