package com.lixin.amuseadjacent.app.ui.find.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicDetailsReplyActivity
import com.lixin.amuseadjacent.app.ui.find.model.ActivityCommentModel1
import com.lixin.amuseadjacent.app.ui.find.request.DeleteComment_227
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 动态评论
 * Created by Slingge on 2018/8/22
 */
class DynamicCommentAdapter(val context: Activity, var commentList: ArrayList<ActivityCommentModel1.commModel>) : RecyclerView.Adapter<DynamicCommentAdapter.ViewHolder>() {

    private var dynaId = ""


    fun setId(dynaId: String) {
        this.dynaId = dynaId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_comment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = commentList[position]

        holder.tv_name.text = model.commentName
        ImageLoader.getInstance().displayImage(model.commentIcon, holder.iv_header)
        holder.tv_name.text = model.commentName
        holder.tv_zan.text = model.zanNum
        holder.tv_comment.text = model.commentContent
        holder.tv_time.text = model.commentTime
        holder.tv_commentNum.text = model.secondNum + "回复"

        if (model.commentUid == StaticUtil.uid) {
            holder.tv_del.visibility = View.VISIBLE
        } else {
            holder.tv_del.visibility = View.INVISIBLE
        }

        holder.tv_del.setOnClickListener { v ->
            ProgressDialog.showDialog(context)
            DeleteComment_227.del(model.commentId, object : DeleteComment_227.DelCommentCallBack {
                override fun delComment() {
                    commentList.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }

        holder.itemView.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("id", dynaId)
            bundle.putSerializable("model", model)
            MyApplication.openActivity(context, DynamicDetailsReplyActivity::class.java, bundle)
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_header = view.findViewById<CircleImageView>(R.id.iv_header)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_zan = view.findViewById<TextView>(R.id.tv_zan)
        val tv_comment = view.findViewById<TextView>(R.id.tv_comment)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_commentNum = view.findViewById<TextView>(R.id.tv_commentNum)

        val tv_del = view.findViewById<TextView>(R.id.tv_del)
    }


}
