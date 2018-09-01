package com.lixin.amuseadjacent.app.ui.service.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.service.activity.SubmissionOrderActivity

/**
 * Created by Slingge on 2018/8/30
 */
class ShopCarAdapter(val context: Context) : RecyclerView.Adapter<ShopCarAdapter.ViewHolder>() {

    private var isEdit = false//true 编辑状态


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shop_car, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (isEdit) {//编辑状态
            holder.line.visibility = View.GONE
            holder.tv_settlement.visibility = View.GONE
            holder.tv_totalMoney.visibility = View.GONE
        } else {
            holder.line.visibility = View.VISIBLE
            holder.tv_settlement.visibility = View.VISIBLE
            holder.tv_totalMoney.visibility = View.VISIBLE
        }

        holder.tv_settlement.setOnClickListener { v->
            MyApplication.openActivity(context,SubmissionOrderActivity::class.java)
        }

        val detailsAdapter = ShopCarDetailsAdapter(context)
        holder.rv_comment.adapter = detailsAdapter
        detailsAdapter.setEdite(isEdit)
    }

    fun setEdite(isEdit: Boolean) {
        this.isEdit = isEdit
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val line = view.findViewById<View>(R.id.line)

        val tv_settlement = view.findViewById<TextView>(R.id.tv_settlement)//结算
        val tv_totalMoney = view.findViewById<TextView>(R.id.tv_totalMoney)//商品总金额

        val rv_comment = view.findViewById<RecyclerView>(R.id.rv_comment)

        val ic_chack = view.findViewById<ImageView>(R.id.ic_chack)


        init {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_comment.layoutManager = linearLayoutManager
        }

    }


}
