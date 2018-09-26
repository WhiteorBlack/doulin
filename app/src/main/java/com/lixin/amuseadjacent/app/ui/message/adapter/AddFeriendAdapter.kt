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
import com.lixin.amuseadjacent.app.ui.message.model.CommunityUserModel
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * Created by Slingge on 2018/8/16
 */
class AddFeriendAdapter(val context: Activity, val userList: ArrayList<CommunityUserModel.userModel>)
    : RecyclerView.Adapter<AddFeriendAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_addfriends, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = userList[position]
        ImageLoader.getInstance().displayImage(model.userIcon, holder.iv_header)
        holder.tv_name.text = model.userName
        holder.tv_age.text = model.userAge
        if (model.userSex == "0") {//女
            holder.tv_age.setBackgroundResource(R.drawable.bg_girl8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, holder.tv_age, 3)
        } else {
            holder.tv_age.setBackgroundResource(R.drawable.bg_boy8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy, holder.tv_age, 3)
        }

        holder.tv_constellation.text = model.constellation

        if(model.userId==StaticUtil.uid){
            holder.tv_follow.visibility=View.INVISIBLE
        }else{
            if (model.isAttention == "0") {//0未关注 1已关注
                holder.tv_follow.text = "关注"
                AbStrUtil.setDrawableLeft(context, R.drawable.ic_add, holder.tv_follow, 5)
            } else {
                holder.tv_follow.text = "已关注"
                AbStrUtil.setDrawableLeft(context, -1, holder.tv_follow, 5)
                holder.tv_follow.visibility=View.INVISIBLE
            }
        }


        holder.tv_follow.setOnClickListener { v ->
            ProgressDialog.showDialog(context)
            Mail_138139.follow(userList[position].userId, object : Mail_138139.FollowCallBack {
                override fun follow() {
                    if (userList[position].isAttention == "0") {
                        userList[position].isAttention = "1"
                    } else {
                        userList[position].isAttention = "0"
                    }
                   notifyDataSetChanged()
                }
            })
        }

        holder.tv_name.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("auid", userList[position].userId)
            bundle.putString("isAttention", model.isAttention)
            MyApplication.openActivity(context, PersonalHomePageActivity::class.java, bundle)
        }
        holder.iv_header.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("auid", userList[position].userId)
            bundle.putString("isAttention", model.isAttention)
            MyApplication.openActivity(context, PersonalHomePageActivity::class.java, bundle)
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_header = view.findViewById<CircleImageView>(R.id.iv_header)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_age = view.findViewById<TextView>(R.id.tv_age)
        val tv_constellation = view.findViewById<TextView>(R.id.tv_constellation)
        val tv_follow = view.findViewById<TextView>(R.id.tv_follow)

    }

}