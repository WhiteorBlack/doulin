package com.lixin.amuseadjacent.app.ui.find.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.poi.PoiResult
import com.lixin.amuseadjacent.R

/**
 * 地址
 * Created by Slingge on 2018/8/22
 */
class SearchAddressAdapter(val context: Context) : RecyclerView.Adapter<SearchAddressAdapter.ViewHolder>() {

    private var list: List<PoiInfo>? = null
    private var onItemClickListener:OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_address_choice, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (list == null){
            return 0
        }else{
            return list!!.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list!![position]

        holder.tv_title.text = item.name
        holder.tv_content.text = item.address
        holder.itemView.setOnClickListener { v ->
            if (onItemClickListener != null){
                onItemClickListener!!.itemClick(item.name)
            }
        }

    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun itemClick(address:String)
    }

    fun refresh(result: PoiResult) {
        this.list = result.allPoi
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_title = view.findViewById<TextView>(R.id.tv_title)
        var tv_content = view.findViewById<TextView>(R.id.tv_content)
    }

}
