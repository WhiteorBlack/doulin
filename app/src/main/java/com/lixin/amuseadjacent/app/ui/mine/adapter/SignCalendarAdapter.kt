package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlin.math.log


/**
 * 签到日历
 * Created by Slingge on 2018/4/10 0010.
 */
class SignCalendarAdapter(val context: Context, val list: ArrayList<Int>, val week: Int, val dateList: ArrayList<String>) : RecyclerView.Adapter<SignCalendarAdapter.ViewHolder>() {

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

            val day = (list[position] - week + 1).toString()
            holder.tv_day.text = day



            for (i in 0 until dateList.size) {
                val date = dateList[i]
                abLog.e("日期222222222222222222222222222222", day + "......" + date.substring(date.length - 2, date.length))
                if (day == date.substring(date.length - 2, date.length)) {
                    holder.tv_day.setTextColor(context.resources.getColor(R.color.white))
                    holder.tv_day.setBackgroundResource(R.drawable.circular_them)
                } else {
                    holder.tv_day.setTextColor(context.resources.getColor(R.color.black))
                    holder.tv_day.setBackgroundColor(context.resources.getColor(R.color.white))
                }
            }


            if (flag - week + 2 == day.toInt()) {
                holder.tv_day.setTextColor(context.resources.getColor(R.color.white))
                holder.tv_day.setBackgroundResource(R.drawable.circular_them)
            }

        }
    }

    fun setFlag(flag: Int) {
        this.flag = flag
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_day: TextView = view.findViewById(R.id.tv_day)
    }

}