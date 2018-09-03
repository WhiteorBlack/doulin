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
import com.lixin.amuseadjacent.app.ui.mine.activity.WebViewActivity

/**
 * 常见问题
 * Created by Slingge on 2018/9/3
 */
class ProblemAdapter(val context: Context) : RecyclerView.Adapter<ProblemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_spinner_text, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 13
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.text.text = "常见问题" + position.toString()

        holder.text.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putInt("flag", 1)
            MyApplication.openActivity(context, WebViewActivity::class.java,bundle)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text = view.findViewById<TextView>(R.id.text)

    }

}

