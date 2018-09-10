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
import com.lixin.amuseadjacent.app.ui.mine.activity.EditAddressActivity

/**
 * 地址
 * Created by Slingge on 2018/8/18
 */
class AddressAdapter(val context: Context) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {




        holder.tv_edit.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putInt("flag", 1)
            MyApplication.openActivity(context, EditAddressActivity::class.java, bundle)
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_edit = view.findViewById<TextView>(R.id.tv_edit)
    }

}