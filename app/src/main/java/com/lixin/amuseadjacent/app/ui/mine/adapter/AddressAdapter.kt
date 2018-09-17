package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daimajia.swipe.SwipeLayout
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.activity.EditAddressActivity
import com.lixin.amuseadjacent.app.ui.mine.model.AddressModel
import com.lixin.amuseadjacent.app.ui.mine.request.Address_140141142143

/**
 * 地址
 * Created by Slingge on 2018/8/18
 */
class AddressAdapter(val context: Activity, val addList: ArrayList<AddressModel.addModel>) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return addList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = addList[position]

        holder.tv_name.text = model.username
        holder.tv_phone.text = model.userPhone
        holder.tv_address.text = model.city + model.address

        if (model.isdefault == "0") {//不是默认
            holder.tv_type.visibility = View.GONE
            holder.tv_type.setPadding(12, 0, 0, 0)
        } else {
            holder.tv_type.visibility = View.VISIBLE
            holder.tv_type.setPadding(0, 0, 0, 0)
        }

        holder.tv_edit.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putInt("flag", 1)
            bundle.putSerializable("DynamiclDetailsModel", model)
            MyApplication.openActivity(context, EditAddressActivity::class.java, bundle)
        }
        holder.tv_del.setOnClickListener { v ->
            ProgressDialog.showDialog(context)
            Address_140141142143.delAddress(model.addressId,object :Address_140141142143.DelAddressCallback{
                override fun del() {
                    holder.sl_item.close()
                    addList.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_edit = view.findViewById<TextView>(R.id.tv_edit)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_phone = view.findViewById<TextView>(R.id.tv_phone)
        val tv_address = view.findViewById<TextView>(R.id.tv_address)
        val tv_type = view.findViewById<TextView>(R.id.tv_type)

        val tv_del = view.findViewById<TextView>(R.id.tv_del)

        val sl_item = view.findViewById<SwipeLayout>(R.id.sl_item)
    }

}