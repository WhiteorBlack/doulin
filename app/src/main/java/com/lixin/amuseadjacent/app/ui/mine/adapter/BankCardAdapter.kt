package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.mine.activity.BankCardAddActivity
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 银行卡
 * Created by Slingge on 2018/8/18
 */
class BankCardAdapter(val context: Context) : RecyclerView.Adapter<BankCardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bankcard, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position == 3 - 1) {
            holder.tv_name.visibility = View.GONE
            holder.tv_bottom.visibility = View.VISIBLE
            holder.line.visibility = View.GONE

            holder.tv_bottom.setOnClickListener { v ->
                MyApplication.openActivity(context, BankCardAddActivity::class.java)
            }
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_bottom = view.findViewById<TextView>(R.id.tv_bottom)

        val line = view.findViewById<View>(R.id.line)
    }

}