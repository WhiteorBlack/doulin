package com.lixin.amuseadjacent.app.ui.find.adapter

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.find.model.TalentModel
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 达人
 * Created by Slingge on 2018/8/22
 */
class TalentAdapter(val context: Context, val talentList: ArrayList<TalentModel.dataModel>) : RecyclerView.Adapter<TalentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_talent, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return talentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = talentList[position]

        holder.tv_name.text = model.userName
        ImageLoader.getInstance().displayImage(model.userImg, holder.iv_header)

        holder.tv_name.text = model.userName
        holder.tv_effect.text = "影响力" + model.userEffectNum
        holder.tv_info.text = model.userDesc
        holder.tv_occupation.text = model.userlabel

        if(model.isAttention=="0"){// 0未关注 1已关注

        }

        holder.tv_dialogue.setOnClickListener { v ->
            ToastUtil.showToast("对话")
        }
        holder.itemView.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("auid", model.userId)
            bundle.putString("isAttention", model.isAttention)
            MyApplication.openActivity(context, PersonalHomePageActivity::class.java, bundle)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_dialogue = view.findViewById<TextView>(R.id.tv_dialogue)

        val iv_header = view.findViewById<CircleImageView>(R.id.iv_header)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_occupation = view.findViewById<TextView>(R.id.tv_occupation)
        val tv_effect = view.findViewById<TextView>(R.id.tv_effect)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)
    }


}
