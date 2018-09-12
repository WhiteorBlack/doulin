package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.mine.model.BalanceDetailsModel

/**
 * 交易明细
 * Created by Slingge on 2018/9/2.
 */
class TransactionAdapter(val context: Context, val detailsList: ArrayList<BalanceDetailsModel.detailsModel>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tv_balance.text=detailsList[position].money
        holder.tv_type.text=detailsList[position].title
        holder.tv_time.text=detailsList[position].time

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_type=view.findViewById<TextView>(R.id.tv_type)
        val tv_balance=view.findViewById<TextView>(R.id.tv_balance)
        val tv_time=view.findViewById<TextView>(R.id.tv_time)
    }


}