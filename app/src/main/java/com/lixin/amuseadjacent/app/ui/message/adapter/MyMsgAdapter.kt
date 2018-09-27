package com.lixin.amuseadjacent.app.ui.message.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.bingoogolapple.badgeview.BGABadgeTextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.message.model.MsgListModel
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil

/**
 * Created by Slingge on 2018/8/15
 */
class MyMsgAdapter(val context: Context, val list: ArrayList<MsgListModel.msgModel>) : RecyclerView.Adapter<MyMsgAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_msg, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]


        MyApplication.setRedNum(holder.tv_msgNum, model.messagenum.toInt())

        if (model.type == "0") {
            holder.tv_type.text = "系统消息"
        } else if (model.type == "1") {
            holder.tv_type.text = "订单消息"
        } else if (model.type == "2") {
            holder.tv_type.text = "评论消息"
        } else if (model.type == "2") {
            holder.tv_type.text = "点赞消息"
        }

        holder.tv_info.text = model.messageTitle
        holder.tv_time.text = model.messageTime
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_type = view.findViewById<TextView>(R.id.tv_type)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_msgNum = view.findViewById<BGABadgeTextView>(R.id.tv_msgNum)

    }

}