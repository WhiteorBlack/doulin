package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.MenuPopupWindow
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.find.model.TalenExperienceModel
import com.lixin.amuseadjacent.app.util.PreviewPhoto
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 个人主页 达人经历
 * Created by Slingge on 2018/8/18
 */
class ExperienceAdapter(val context: Context, val talentExpList: ArrayList<TalenExperienceModel.dataModel>) : RecyclerView.Adapter<ExperienceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_experience, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return talentExpList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = talentExpList[position]

        holder.tv_title.text = model.title
        holder.tv_info.text = model.content
        holder.tv_time.text = model.startTime + "-" + model.endTime

        if (model.imgurl.size == 1) {
            holder.rv_image.visibility = View.GONE
            holder.ll_image.visibility = View.VISIBLE
            holder.image0.visibility = View.VISIBLE

            holder.image0.setOnClickListener { v ->
                PreviewPhoto.preview(context, model.imgurl, 0)
            }

            holder.image1.visibility = View.GONE
            holder.image2.visibility = View.GONE

            ImageLoader.getInstance().displayImage(model.imgurl[0], holder.image0)

        } else if (model.imgurl.size == 2) {
            holder.rv_image.visibility = View.GONE
            holder.ll_image.visibility = View.VISIBLE
            holder.image0.visibility = View.GONE

            holder.image1.visibility = View.VISIBLE
            holder.image2.visibility = View.VISIBLE

            holder.image0.setOnClickListener { v ->
                PreviewPhoto.preview(context, model.imgurl, 0)
            }
            holder.image1.setOnClickListener { v ->
                PreviewPhoto.preview(context, model.imgurl, 1)
            }

            ImageLoader.getInstance().displayImage(model.imgurl[0], holder.image1)
            ImageLoader.getInstance().displayImage(model.imgurl[1], holder.image2)
        } else {
            holder.rv_image.visibility = View.VISIBLE
            holder.ll_image.visibility = View.GONE
            val imageAdapter = ImageAdapter(context, model.imgurl, 0)
            holder.rv_image.adapter = imageAdapter
        }


    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rv_image = view.findViewById<RecyclerView>(R.id.rv_image)

        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)

        val ll_image = view.findViewById<LinearLayout>(R.id.ll_image)
        val image0 = view.findViewById<ImageView>(R.id.image0)
        val image1 = view.findViewById<ImageView>(R.id.image1)
        val image2 = view.findViewById<ImageView>(R.id.image2)


        init {
            val linearLayoutManager = GridLayoutManager(context, 3)
            rv_image.layoutManager = linearLayoutManager
        }

    }


}