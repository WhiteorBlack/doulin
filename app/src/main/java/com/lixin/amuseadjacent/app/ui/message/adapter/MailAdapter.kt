package com.lixin.amuseadjacent.app.ui.message.adapter

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.message.model.MailModel
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * flag 0好友，1关注，2粉丝
 * Created by Slingge on 2018/8/16
 */
class MailAdapter(val context: Activity, val flag: Int, val mailList: ArrayList<MailModel.addModel>) : RecyclerView.Adapter<MailAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mailList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = mailList[position]

        if (flag == 2) {

            if (mailList[position].userId == StaticUtil.uid) {
                holder.tv_follow.visibility = View.INVISIBLE
            } else {
                holder.tv_follow.visibility = View.VISIBLE
                holder.tv_follow.visibility = View.VISIBLE
                if (mailList[position].isAttention == "0") {
                    holder.tv_follow.text = "+  关注"
                    holder.tv_follow.visibility = View.VISIBLE
                } else {
                    holder.tv_follow.text = "已关注"
                    holder.tv_follow.visibility = View.INVISIBLE
                }
            }

            holder.tv_follow.setOnClickListener { v ->
                ProgressDialog.showDialog(context)
                Mail_138139.follow(model.userId, object : Mail_138139.FollowCallBack {
                    override fun follow() {
                        if (mailList[position].isAttention == "0") {
                            mailList[position].isAttention = "1"
                        } else {
                            mailList[position].isAttention = "0"
                        }
                        notifyDataSetChanged()
                    }
                })
            }
        }

        holder.tv_header.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("auid", mailList[position].userId)
            bundle.putString("isAttention", mailList[position].isAttention)
            MyApplication.openActivity(context, PersonalHomePageActivity::class.java, bundle)
        }

        holder.tv_name.text = model.userName
        holder.tv_info.text = model.userAutograph
        holder.tv_age.text = model.userAge
        if (model.userSex == "0") {//0女 1男
            holder.tv_age.setBackgroundResource(R.drawable.bg_girl8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, holder.tv_age, 5)
        } else {
            holder.tv_age.setBackgroundResource(R.drawable.bg_boy8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy, holder.tv_age, 5)
        }

        ImageLoader.getInstance().displayImage(model.userIcon, holder.tv_header)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_follow = view.findViewById<TextView>(R.id.tv_follow)

        val tv_header = view.findViewById<CircleImageView>(R.id.tv_header)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val tv_age = view.findViewById<TextView>(R.id.tv_age)

    }


}