package com.lixin.amuseadjacent.app.ui.message.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.bingoogolapple.badgeview.BGABadgeTextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.util.ImageLoaderUtil
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.netease.nimlib.sdk.msg.model.RecentContact
import com.netease.nimlib.sdk.uinfo.model.UserInfo
import com.nostra13.universalimageloader.core.ImageLoader
import java.text.SimpleDateFormat

/**
 * 聊天
 * Created by Slingge on 2018/8/22
 */
class SearchChatAdapter(val context: Context) : RecyclerView.Adapter<SearchChatAdapter.ViewHolder>() {

    private var list: ArrayList<RecentContact>? = null
    private var onItemClickListener:OnItemClickListener? = null
    private var userInfoList: ArrayList<UserInfo>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat_msg, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (list == null){
            return 0
        }else{
            return list!!.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list!![position]
        val infoItem = userInfoList!![position]

        ImageLoader.getInstance().displayImage(infoItem!!.avatar, holder.image, ImageLoaderUtil.HeaderDIO())
        holder.tv_type.text = infoItem!!.name
        holder.tv_info.text = item.content
        holder.tv_msgNum.text = item.unreadCount.toString()
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dateString = formatter.format(item.time)
        holder.tv_time.text = dateString

        holder.ll_bank.setOnClickListener { v ->
            if (onItemClickListener != null){
                onItemClickListener!!.itemClick(item.contactId)
            }
        }

    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun itemClick(account:String)
    }

    fun refresh(list: ArrayList<RecentContact>,userInfoList: ArrayList<UserInfo>) {
        this.list = list
        this.userInfoList = userInfoList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<CircleImageView>(R.id.image)
        val tv_type = view.findViewById<TextView>(R.id.tv_type)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_msgNum = view.findViewById<BGABadgeTextView>(R.id.tv_msgNum)
        val ll_bank = view.findViewById<ConstraintLayout>(R.id.ll_bank)
    }

}
