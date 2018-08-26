package com.lixin.amuseadjacent.app.ui.find.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicDetailsReplyActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity

/**
 * 评论
 * Created by Slingge on 2018/8/22
 */
class DynamicCommentAdapter(val context: Context) : RecyclerView.Adapter<DynamicCommentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_comment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener { v ->
            MyApplication.openActivity(context, DynamicDetailsReplyActivity::class.java)
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}
