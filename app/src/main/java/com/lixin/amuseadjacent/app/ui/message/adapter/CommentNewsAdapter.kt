package com.lixin.amuseadjacent.app.ui.message.adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicDetailsActivity
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicDetailsReplyActivity
import com.lixin.amuseadjacent.app.ui.message.model.CommentNewModel
import com.lixin.amuseadjacent.app.ui.message.request.MsgList_21
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil

/**
 * type=""//2评论信息 3点赞信息
 * Created by Slingge on 2018/8/16
 */
class CommentNewsAdapter(val context: Context, val type: String, val commentList: ArrayList<CommentNewModel.msgModel>) : RecyclerView.Adapter<CommentNewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_comment_news, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = commentList[position]

        if (type == "2") {
            holder.text.text = "收到新的评论"
            holder.tex2.text = model.messageTitle
            holder.image.setImageResource(R.drawable.ic_comment)
        } else {
            holder.text.text = "收到新的点赞"
            holder.tex2.text = "点赞消息"
            holder.image.setImageResource(R.drawable.ic_zan_msg)
        }

        holder.tv_time.text = model.messageTime


        holder.tv_see.setOnClickListener { v ->
            if (model.state == "1") {//已经隐藏
                ToastUtil.showToast("没有找到回复")
            } else {
                val bundle = Bundle()
                bundle.putString("commentId", model.commentId)
                bundle.putString("id", model.objid)
                if (model.type == "1") {//1动态帮帮 2活动 3话题
                    if (!TextUtils.isEmpty(model.commentId)) {
                        bundle.putSerializable("model", null)
                        MyApplication.openActivity(context, DynamicDetailsReplyActivity::class.java, bundle)
                    } else {
                        bundle.putString("flag", "-1")
                        MyApplication.openActivity(context, DynamicDetailsActivity::class.java, bundle)
                    }
                } else if (model.type == "2") {

                } else if (model.type == "3") {

                }
            }

        }

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
        val image = view.findViewById<ImageView>(R.id.image)

        val tv_del = view.findViewById<TextView>(R.id.tv_del)
    }


}