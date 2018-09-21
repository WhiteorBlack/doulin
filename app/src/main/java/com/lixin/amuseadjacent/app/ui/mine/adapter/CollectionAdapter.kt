package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.content.Context
import android.media.Image
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.mine.model.CollectModel
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 收藏
 * Created by Slingge on 2018/9/3
 */
class CollectionAdapter(val context: Context, val collectList: ArrayList<CollectModel.collectModel>) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_interaction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return collectList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = collectList[position]



        if (model.type == "0") {//帮帮
            holder.cl_1.visibility = View.VISIBLE
            holder.cl_2.visibility = View.GONE
            holder.tv_type2.text = "帮帮收藏"

            if (!TextUtils.isEmpty(model.bangbangVideoUrl)) {//视频
                holder.image0.visibility = View.GONE
                holder.ll_image.visibility = View.GONE
                holder.rv_image.visibility = View.GONE


            } else {
                if (model.bangbangImgUrl.size == 0) {
                    holder.image0.visibility = View.GONE
                    holder.ll_image.visibility = View.GONE
                    holder.rv_image.visibility = View.GONE
                } else if (model.bangbangImgUrl.size == 1) {
                    holder.image0.visibility = View.VISIBLE
                    ImageLoader.getInstance().displayImage(model.bangbangImgUrl[0], holder.image0)
                    holder.ll_image.visibility = View.GONE
                    holder.rv_image.visibility = View.GONE
                } else if (model.bangbangImgUrl.size == 2) {
                    holder.ll_image.visibility = View.VISIBLE
                    ImageLoader.getInstance().displayImage(model.bangbangImgUrl[0], holder.image1)
                    ImageLoader.getInstance().displayImage(model.bangbangImgUrl[1], holder.image2)

                    holder.rv_image.visibility = View.GONE
                    holder.image0.visibility = View.GONE
                } else {
                    holder.ll_image.visibility = View.GONE
                    holder.rv_image.visibility = View.VISIBLE
                    holder.image0.visibility = View.GONE

                    val imageAdapter = ImageAdapter(context, model.bangbangImgUrl)
                    holder.rv_image.adapter = imageAdapter
                }
            }
        } else {//活动
            holder.cl_1.visibility = View.GONE
            holder.cl_2.visibility = View.VISIBLE
            holder.tv_type2.text = "活动收藏"

            ImageLoader.getInstance().displayImage(model.activityImg, holder.iv_image)
            holder.tv_name.text = model.activityName

            if (model.issignup == "0") {
                holder.tv_type.text = "未报名"
            } else {
                holder.tv_type.text = "已报名"
            }

            holder.tv_price.text = model.activityMoney + "元/人"
            holder.tv_time.text = "时间：" + model.activityTime
            holder.tv_address.text = "地点：" + model.activityAddress
            holder.tv_num.text = "人数：" + model.activityNownum
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl_1 = view.findViewById<View>(R.id.cl_1)//动态
        val cl_2 = view.findViewById<View>(R.id.cl_2)//帮帮

        val line2 = view.findViewById<View>(R.id.line2)
        val line = view.findViewById<View>(R.id.line)

        val tv_type2 = view.findViewById<TextView>(R.id.tv_type2)
        val iv_del = view.findViewById<ImageView>(R.id.iv_del)


        //活动
        val iv_image = view.findViewById<ImageView>(R.id.iv_image)
        val tv_type = view.findViewById<TextView>(R.id.tv_type)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_price = view.findViewById<TextView>(R.id.tv_price)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_address = view.findViewById<TextView>(R.id.tv_address)
        val tv_num = view.findViewById<TextView>(R.id.tv_num)

        //帮帮
        val tv_info = view.findViewById<TextView>(R.id.tv_info)

        val image0 = view.findViewById<ImageView>(R.id.image0)

        val ll_image = view.findViewById<LinearLayout>(R.id.ll_image)
        val image1 = view.findViewById<ImageView>(R.id.image1)
        val image2 = view.findViewById<ImageView>(R.id.image2)

        val rv_image = view.findViewById<RecyclerView>(R.id.rv_image)


        init {
            line2.visibility = View.GONE
            line.visibility = View.VISIBLE

            tv_type2.visibility = View.VISIBLE
            iv_del.visibility = View.VISIBLE

            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_image.layoutManager = linearLayoutManager
        }
    }

}