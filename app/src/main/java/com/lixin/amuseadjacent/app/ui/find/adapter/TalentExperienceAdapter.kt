package com.lixin.amuseadjacent.app.ui.find.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.find.activity.TalentAuthenticationActivity
import com.lixin.amuseadjacent.app.ui.find.activity.TalentAuthenticationAddActivity
import com.lixin.amuseadjacent.app.ui.find.model.TalenExperienceModel
import com.lixin.amuseadjacent.app.ui.find.request.Talent212_218225
import com.lixin.amuseadjacent.app.ui.mine.adapter.ImageAdapter

/**
 * 达人
 * Created by Slingge on 2018/8/22
 */
class TalentExperienceAdapter(val context: Activity, val talentExpList: ArrayList<TalenExperienceModel.dataModel>,val positionCallBack: PositionCallBack) : RecyclerView.Adapter<TalentExperienceAdapter.ViewHolder>() {

    interface  PositionCallBack{
        fun select(i:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_talent_experience, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return talentExpList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = talentExpList[position]

        holder.tv_name.text = model.title
        holder.tv_info.text = model.content
        holder.tv_time.text = model.startTime + "～" + model.endTime

        val adapter = ImageAdapter(context, model.imgurl)
        holder.rv_image.adapter = adapter

        holder.iv_del.setOnClickListener { v ->
            Talent212_218225.DelTalenExperience(model.experienceId, object : Talent212_218225.DelTalenExperienceCallBack {
                override fun del() {
                    talentExpList.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }

        holder.iv_edit.setOnClickListener { v ->
            positionCallBack.select(position)
            val bundle = Bundle()
            bundle.putSerializable("model", model)
            MyApplication.openActivityForResult(context, TalentAuthenticationAddActivity::class.java, bundle, 2)
        }


    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val iv_del = view.findViewById<ImageView>(R.id.iv_del)
        val iv_edit = view.findViewById<ImageView>(R.id.iv_edit)

        val rv_image = view.findViewById<RecyclerView>(R.id.rv_image)

        init {
            val gridLayoutManager = GridLayoutManager(context, 3)
            rv_image.layoutManager = gridLayoutManager
        }

    }

}