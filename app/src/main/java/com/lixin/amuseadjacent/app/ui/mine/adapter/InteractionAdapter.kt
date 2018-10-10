package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.app.Activity
import android.content.Context
import android.service.carrier.MessagePdu
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.model.InteractionModel
import com.lixin.amuseadjacent.app.ui.mine.request.Myinteraction_161162
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.nostra13.universalimageloader.core.ImageLoader
import com.xiao.nicevideoplayer.NiceVideoPlayer
import com.xiao.nicevideoplayer.TxVideoPlayerController

/**
 * 个人主页 互动
 * Created by Slingge on 2018/8/18
 */
class InteractionAdapter(val context: Activity, val auid: String, val interactionList: ArrayList<InteractionModel.dataModel>) : RecyclerView.Adapter<InteractionAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return interactionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = interactionList[position]

        if (model.type == "0") {//0帮帮 1活动
            holder.cl_1.visibility = View.VISIBLE
            holder.cl_2.visibility = View.GONE
            holder.tv_info.visibility = View.VISIBLE

            holder.tv_info.text = model.bangbangContent
            if (TextUtils.isEmpty(model.bangbangVideoUrl)) {
                holder.player.visibility = View.GONE
                if (model.bangbangImgUrl.size == 1) {
                    holder.image0.visibility = View.VISIBLE
                    holder.ll_image.visibility = View.GONE
                    holder.rv_image.visibility = View.GONE

                    ImageLoader.getInstance().displayImage(model.bangbangImgUrl[0], holder.image0)
                } else if (model.bangbangImgUrl.size == 1) {
                    holder.image0.visibility = View.GONE
                    holder.ll_image.visibility = View.VISIBLE
                    holder.rv_image.visibility = View.GONE

                    ImageLoader.getInstance().displayImage(model.bangbangImgUrl[0], holder.image1)
                    ImageLoader.getInstance().displayImage(model.bangbangImgUrl[1], holder.image2)
                } else {
                    holder.image0.visibility = View.GONE
                    holder.ll_image.visibility = View.GONE
                    holder.rv_image.visibility = View.VISIBLE

                    val imageAdapter = ImageAdapter(context, model.bangbangImgUrl, 1)
                    holder.rv_image.adapter = imageAdapter
                }
            } else {
                holder.player.visibility = View.VISIBLE

                holder.image0.visibility = View.GONE
                holder.ll_image.visibility = View.GONE
                holder.rv_image.visibility = View.GONE

                holder.player.setUp(model.bangbangVideoUrl, null)
                val controller = TxVideoPlayerController(context)
                ImageLoader.getInstance().displayImage(model.bangbangImageUrl, controller.imageView())
                holder.player.setController(controller)
            }
        } else {
            holder.tv_info.visibility = View.GONE
            holder.cl_1.visibility = View.GONE
            holder.cl_2.visibility = View.VISIBLE

            ImageLoader.getInstance().displayImage(model.activityImg, holder.iv_image)
            holder.tv_name.text = model.activityName
            holder.tv_price.text = model.activityMoney + "元/人"
            holder.tv_time.text = "时间：" + model.activityTime
            holder.tv_address.text = "地点：" + model.activityName
            holder.tv_num.text = "人数：" + model.activityNownum + "/" + model.activityAllnum
        }

        holder.tv_date.text = model.time

        if (StaticUtil.uid != model.userId) {
            holder.iv_del.visibility = View.GONE
        }else{
            holder.iv_del.visibility = View.VISIBLE
        }

        holder.iv_del.setOnClickListener { v ->
            ProgressDialog.showDialog(context)
            var id = ""
            if (model.type == "0") {
                id = model.bangbangId
            } else {
                id = model.activityId
            }
            Myinteraction_161162.DelInteraction(model.type, id, object : Myinteraction_161162.DelInteractionCallBack {
                override fun del() {
                    interactionList.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_interaction, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl_1 = view.findViewById<ConstraintLayout>(R.id.cl_1)
        val cl_2 = view.findViewById<ConstraintLayout>(R.id.cl_2)

        val tv_info = view.findViewById<TextView>(R.id.tv_info)
        val image0 = view.findViewById<ImageView>(R.id.image0)
        val ll_image = view.findViewById<LinearLayout>(R.id.ll_image)
        val image1 = view.findViewById<ImageView>(R.id.image1)
        val image2 = view.findViewById<ImageView>(R.id.image2)
        val rv_image = view.findViewById<RecyclerView>(R.id.rv_image)
        val player = view.findViewById<NiceVideoPlayer>(R.id.player)


        val iv_image = view.findViewById<ImageView>(R.id.iv_image)
        val tv_type = view.findViewById<TextView>(R.id.tv_type)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_price = view.findViewById<TextView>(R.id.tv_price)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_address = view.findViewById<TextView>(R.id.tv_address)
        val tv_num = view.findViewById<TextView>(R.id.tv_num)


        val iv_del = view.findViewById<ImageView>(R.id.iv_del)
        val tv_date = view.findViewById<TextView>(R.id.tv_date)
        val line = view.findViewById<View>(R.id.line)

        val tv_type2 = view.findViewById<TextView>(R.id.tv_type2)

        init {
            iv_del.visibility = View.VISIBLE
            iv_del.setImageResource(R.drawable.ic_del2)
            tv_type2.visibility = View.INVISIBLE

            tv_date.visibility = View.VISIBLE
            line.visibility = View.VISIBLE

            tv_type.visibility = View.GONE

            if (auid == StaticUtil.uid) {
                iv_del.visibility = View.VISIBLE
            }
        }

    }


}