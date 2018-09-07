package com.lixin.amuseadjacent.app.ui.find.adapter

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil

/**
 * 达人
 * Created by Slingge on 2018/8/22
 */
class TalentAdapter(val context: Context) : RecyclerView.Adapter<TalentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_talent, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 12
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tv_dialogue.setOnClickListener { v ->
            ToastUtil.showToast("对话")
        }
        holder.itemView.setOnClickListener { v ->
            val bundle=Bundle()
            bundle.putInt("flag",1)
            bundle.putString("auid","")
            MyApplication.openActivity(context, PersonalHomePageActivity::class.java,bundle)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_dialogue = view.findViewById<TextView>(R.id.tv_dialogue)
    }




}
