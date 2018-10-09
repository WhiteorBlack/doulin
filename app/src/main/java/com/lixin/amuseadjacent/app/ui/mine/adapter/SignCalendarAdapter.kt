package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.util.abLog

/**
 * 签到日历
 * Created by Slingge on 2018/4/10 0010.
 */
class SignCalendarAdapter(val context: Context, val list: ArrayList<Int>, val week: Int, val dateList: ArrayList<String>, val days: Int) : RecyclerView.Adapter<SignCalendarAdapter.ViewHolder>() {

    private var flag = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_calendar, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < week - 1) {
            holder.tv_day.visibility = View.INVISIBLE
        } else {
            var day = (list[position] - week + 1).toString()
            holder.tv_day.text = day

            for (i in 0 until dateList.size) {
                var date = dateList[i]
                if (day.length == 1) {
                    day = "0$day"
                }
                abLog.e("今天", "$date,$day")
                if (day == date.substring(date.length - 2, date.length)) {

                    holder.iv_dian.visibility = View.VISIBLE
//                    holder.tv_day.setTextColor(context.resources.getColor(R.color.white))
//                    holder.tv_day.setBackgroundResource(R.drawable.circular_them)
                }
            }

            if (day.toInt() == days) {
                holder.tv_day.setBackgroundResource(R.drawable.circular_them)
                holder.tv_day.setTextColor(context.resources.getColor(R.color.white))
            }
        }
    }

    fun setFlag(flag: Int) {
        this.flag = flag
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_day: TextView = view.findViewById(R.id.tv_day)
        val iv_dian: ImageView = view.findViewById(R.id.iv_dian)
    }

}