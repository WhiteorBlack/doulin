package com.lixin.amuseadjacent.app.ui.find.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicAllCommentsActivity
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicDetailsReplyActivity
import com.lixin.amuseadjacent.app.ui.find.activity.EventDetailsReplyActivity
import com.lixin.amuseadjacent.app.ui.find.model.ActivityCommentModel1
import com.lixin.amuseadjacent.app.ui.find.request.ActivityComment_272829210
import com.lixin.amuseadjacent.app.ui.find.request.DeleteComment_227
import com.lixin.amuseadjacent.app.ui.find.request.Find_26
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 动态评论
 * Created by Slingge on 2018/8/22
 */
class DynamicCommentAdapter(val context: Activity, var commentList: ArrayList<ActivityCommentModel1.commModel>) : RecyclerView.Adapter<DynamicCommentAdapter.ViewHolder>() {

    private var dynaId = ""
    private var type = ""//0动态，1活动,2话题

    private var allComm = -1//0所有评论的适配器

    interface DelCommentCallBack {
        fun delComment()
        fun delComment(position: Int)
    }

    private var delCommentCallBack: DelCommentCallBack? = null

    fun setDelCommentCallBack(delCommentCallBack: DelCommentCallBack) {
        this.delCommentCallBack = delCommentCallBack
    }

    fun setId(dynaId: String, type: String) {
        this.dynaId = dynaId
        this.type = type
    }

    fun setallComm(allComm: Int) {
        this.allComm = allComm
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

        if (model.isZan == "1") {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, holder.tv_zan, 5)
        } else {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan, holder.tv_zan, 5)
        }
        holder.tv_zan!!.setOnClickListener { v ->
            ProgressDialog.showDialog(context)
            ActivityComment_272829210.zan("0", dynaId, model.commentId, object : Find_26.ZanCallback {
                override fun zan() {
                    if (model.isZan == "1") {
                        commentList[position].isZan = "0"
                        commentList[position].zanNum = (model.zanNum.toInt() - 1).toString()
                    } else {
                        commentList[position].isZan = "1"
                        commentList[position].zanNum = (model.zanNum.toInt() + 1).toString()
                    }
                    notifyDataSetChanged()
                    if (allComm != -1) {
                        delCommentCallBack!!.delComment(position)
                    }
                }
            })
        }

        holder.tv_del.setOnClickListener { v ->
            ProgressDialog.showDialog(context)
            DeleteComment_227.del(model.commentId, object : DeleteComment_227.DelCommentCallBack {
                override fun delComment() {
                    commentList.removeAt(position)
                    notifyDataSetChanged()
                    if (delCommentCallBack != null) {
                        if (allComm != -1) {
                            delCommentCallBack!!.delComment(position)
                        } else {
                            delCommentCallBack!!.delComment()
                        }
                    }
                }
            })
        }


        holder.cl_item.setOnClickListener { v ->
            var intent: Intent? = null
            if (type == "0") {
                intent = Intent(context, DynamicDetailsReplyActivity::class.java)
            } else {
                intent = Intent(context, EventDetailsReplyActivity::class.java)
            }

            intent.putExtra("model", commentList[position])
            intent.putExtra("id", dynaId)
            intent.putExtra("type", type)
            intent.putExtra("position", position)
            context.startActivityForResult(intent, 303)
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl_item = view.findViewById<ConstraintLayout>(R.id.cl_item)
        val iv_header = view.findViewById<CircleImageView>(R.id.iv_header)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_zan = view.findViewById<TextView>(R.id.tv_zan)
        val tv_comment = view.findViewById<TextView>(R.id.tv_comment)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_commentNum = view.findViewById<TextView>(R.id.tv_commentNum)

        val tv_del = view.findViewById<TextView>(R.id.tv_del)
    }


}
