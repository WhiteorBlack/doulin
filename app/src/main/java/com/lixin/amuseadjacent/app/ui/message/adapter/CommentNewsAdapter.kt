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
import com.lixin.amuseadjacent.app.ui.message.model.CommentNewModel
import com.lixin.amuseadjacent.app.ui.message.request.MsgList_21
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil

/**
 * Created by Slingge on 2018/8/16
 */
class CommentNewsAdapter(val context: Context, val commentList: ArrayList<CommentNewModel.msgModel>) : RecyclerView.Adapter<CommentNewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_comment_news, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = commentList[position]

        holder.tex2.text = model.messageTitle
        holder.tv_time.text = model.messageTime

        holder.tv_del.setOnClickListener { v ->
            MsgList_21.delMsg(model.messageId, "2", object : MsgList_21.DelMsgCallBack {
                override fun delMsg() {
                    commentList.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_see = view.findViewById<TextView>(R.id.tv_see)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val text = view.findViewById<TextView>(R.id.text)
        val tex2 = view.findViewById<TextView>(R.id.tex2)


        val tv_del = view.findViewById<TextView>(R.id.tv_del)
    }


}