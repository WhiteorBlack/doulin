package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.mine.model.IrregularitiesModel
import com.lixin.amuseadjacent.app.ui.mine.model.ReportModel
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 违规举报
 * flag 0我的违规，1我的举报
 * Created by Slingge on 2018/8/18
 */
class ViolationReportAdapter(val context: Context, val flag: Int,
                             val violationList: ArrayList<IrregularitiesModel.irreguModel>, val reportList: ArrayList<ReportModel.irreguModel>)
    : RecyclerView.Adapter<ViolationReportAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_violation_report, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (flag == 0) {
            return violationList.size
        } else {
            return reportList.size
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (flag == 0) {

            val irreguModel = violationList[position]

            holder.image.visibility = View.GONE
            holder.tv_result.setTextColor(context.resources.getColor(R.color.red))
            holder.tv_result.text = "已违规"

            holder.tv_name.text = irreguModel.larTitle
            holder.tv_info.text = irreguModel.larContent
            holder.tv_time.text = irreguModel.time


        } else {
            val irreguModel = reportList[position]

            holder.image.visibility = View.VISIBLE
            holder.tv_result.setTextColor(context.resources.getColor(R.color.colorTheme))
            if (irreguModel.state == "0") {
                holder.tv_result.text = "待审核"
            } else if (irreguModel.state == "1") {
                holder.tv_result.text = "已举报"
            } else {
                holder.tv_result.text = "举报失败"
            }

            ImageLoader.getInstance().displayImage(irreguModel.userIcon, holder.image)

            holder.tv_name.text = irreguModel.larTitle
            holder.tv_info.text = irreguModel.larContent
            holder.tv_time.text = irreguModel.time

        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)

        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val tv_result = view.findViewById<TextView>(R.id.tv_result)//是否违规状态
        val tv_time = view.findViewById<TextView>(R.id.tv_time)

    }

}