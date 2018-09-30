package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.mine.model.EffectModel
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil

/**
 * 社区影响力
 * flag 0 社区影响力，1影响力详情
 * Created by Slingge on 2018/9/2.
 */
class EffectAdapter(val context: Context, val effectList: ArrayList<EffectModel.dataModel>) : RecyclerView.Adapter<EffectAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_effect, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return effectList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = effectList[position]
        if (model.isFinishTask == "1") {
            holder.tv_eeffect.text = "已完成"
        } else {
            holder.tv_eeffect.text = "+" + model.effectNum + "影响力"
        }

        holder.tv_name.text = model.taskTitle

        when (model.taskId) {// 1每日签到 ,2邀请好友 3发布活动  4发布动态 5完善资料 6认证用户
            "1" -> {
                holder.image.setImageResource(R.drawable.ic_effect0)
            }
            "2" -> {
                holder.tv_name.text = "邀请好友（" + model.isFinishTask + "）"
                holder.image.setImageResource(R.drawable.ic_effect5)
            }
            "3" -> {
                holder.image.setImageResource(R.drawable.ic_effect1)
            }
            "4" -> {
                holder.image.setImageResource(R.drawable.ic_effect2)
            }
            "5" -> {
                holder.image.setImageResource(R.drawable.ic_effect3)
            }
            "6" -> {
                holder.image.setImageResource(R.drawable.ic_effect4)
            }
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_eeffect = view.findViewById<TextView>(R.id.tv_eeffect)

    }


}