package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.model.ShieldModel
import com.lixin.amuseadjacent.app.ui.mine.request.Shield_151152
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 屏蔽
 * Created by Slingge on 2018/8/18
 */
class ShieldAdapter(val context: Activity, var shieldList: ArrayList<ShieldModel.shieldModel>) : RecyclerView.Adapter<ShieldAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shieldList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = shieldList[position]
        holder.tv_name.text = model.userName
        holder.tv_info.text = model.userAutograph
        ImageLoader.getInstance().displayImage(model.userIcon, holder.tv_header)

        if (model.userSex == "0") {//女
            holder.tv_age.setBackgroundResource(R.drawable.bg_girl8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, holder.tv_age, 3)
        } else {
            holder.tv_age.setBackgroundResource(R.drawable.bg_boy8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy, holder.tv_age, 3)
        }
        holder.tv_age.text = model.userAge

        holder.tv_follow.setOnClickListener { v ->
            ProgressDialog.showDialog(context)
            Shield_151152.delShield(model.userId, object : Shield_151152.DelShieldCallback {
                override fun del() {
                    shieldList.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_follow = view.findViewById<TextView>(R.id.tv_follow)

        val tv_header = view.findViewById<CircleImageView>(R.id.tv_header)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val tv_age = view.findViewById<TextView>(R.id.tv_age)

        init {
            tv_follow.text = "移除"
            tv_follow.visibility = View.VISIBLE
            AbStrUtil.setDrawableLeft(context, -1, tv_follow, 0)
        }
    }

}