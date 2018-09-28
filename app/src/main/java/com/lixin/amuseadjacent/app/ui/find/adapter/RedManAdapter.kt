package com.lixin.amuseadjacent.app.ui.find.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.model.RedmanModel
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.util.ImageLoaderUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 达人
 * Created by Slingge on 2018/8/22
 */
class RedManAdapter(val context: Activity, val redmanList: ArrayList<RedmanModel.dataModel>, val followCallBack: FollowCallBack) : RecyclerView.Adapter<RedManAdapter.ViewHolder>() {

    interface FollowCallBack {
        fun follow(i: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_redman, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return redmanList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = redmanList[position]
        ImageLoader.getInstance().displayImage(model.userImg, holder.ic_header, ImageLoaderUtil.HeaderDIO())
        holder.tv_name.text = model.userName
        holder.tv_effect.text = "影响力" + model.userEffectNum
        holder.tv_num.text = (position + 1).toString()

        if (StaticUtil.uid == model.userId) {
            holder.tv_follow_right.visibility = View.INVISIBLE
        } else {
            holder.tv_follow_right.visibility = View.VISIBLE
            if (model.isAttention == "0") {// 0未关注 1已关注
                holder.tv_follow_right.text = "+ 关注"
                holder.tv_follow_right.visibility = View.VISIBLE
            } else {
                holder.tv_follow_right.text = "已关注"
                holder.tv_follow_right.visibility = View.INVISIBLE
            }
        }



        holder.ic_header.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("auid", model.userId)
            bundle.putString("isAttention", model.isAttention)
            MyApplication.openActivity(context, PersonalHomePageActivity::class.java, bundle)
        }

        holder.tv_follow_right.setOnClickListener { v ->
            followCallBack.follow(position)
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_num = view.findViewById<TextView>(R.id.tv_num)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_effect = view.findViewById<TextView>(R.id.tv_effect)
        val tv_follow_right = view.findViewById<TextView>(R.id.tv_follow_right)

        val ic_header = view.findViewById<CircleImageView>(R.id.ic_header)
    }


}
