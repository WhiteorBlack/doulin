package com.lixin.amuseadjacent.app.ui.message.adapter

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.message.model.OfficialNewModel
import com.lixin.amuseadjacent.app.ui.message.request.MsgList_21
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * Created by Slingge on 2018/8/16
 */
class OfficialNewsAdapter(val context: Context, var offList: ArrayList<OfficialNewModel.msgModel>) : RecyclerView.Adapter<OfficialNewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_official_news, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return offList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = offList[position]
        ImageLoader.getInstance().displayImage(model.messageUrl, holder.image)
        holder.tv_info.text = model.messageTitle

        holder.image.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("url", model.messageUrl)
            bundle.putString("title", model.messageTitle)
            MyApplication.openActivity(context, WebViewActivity::class.java, bundle)
        }

        holder.tv_del.setOnClickListener { v ->
            MsgList_21.delMsg(model.messageId, "0", object : MsgList_21.DelMsgCallBack {
                override fun delMsg() {
                    offList.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_del = view.findViewById<TextView>(R.id.tv_del)

        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val image = view.findViewById<ImageView>(R.id.image)

        val cl_item = view.findViewById<ConstraintLayout>(R.id.cl_item)
    }


}