package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.mine.activity.CommonProblemActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity
import com.lixin.amuseadjacent.app.ui.mine.model.HelpModel
import com.lixin.amuseadjacent.app.util.AbStrUtil

/**
 * 常见问题
 * Created by Slingge on 2018/9/3
 */
class ProblemAdapter(val context: Context, val list: ArrayList<HelpModel.problemModel>) : RecyclerView.Adapter<ProblemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_spinner_text, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.text.text = list[position].title

        holder.text.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("title", list[position].title)
            bundle.putString("content", list[position].content)
            MyApplication.openActivity(context, CommonProblemActivity::class.java, bundle)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text = view.findViewById<TextView>(R.id.text)

        init {
            AbStrUtil.setDrawableRight(context, R.drawable.ic_right, text, 0)
        }

    }

}

