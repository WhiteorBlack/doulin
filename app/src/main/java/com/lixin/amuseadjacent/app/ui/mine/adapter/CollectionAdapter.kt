package com.lixin.amuseadjacent.app.ui.mine.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
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
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicDetailsActivity
import com.lixin.amuseadjacent.app.ui.find.activity.EventDetailsActivity
import com.lixin.amuseadjacent.app.ui.find.request.Event_221222223224
import com.lixin.amuseadjacent.app.ui.mine.model.CollectModel
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 收藏
 * Created by Slingge on 2018/9/3
 */
class CollectionAdapter(val context: Activity, val collectList: ArrayList<CollectModel.collectModel>) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_interaction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return collectList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = collectList[position]
        var id = ""
        if (model.type == "0") {//帮帮
            holder.cl_1.visibility = View.VISIBLE
            holder.cl_2.visibility = View.GONE
            holder.tv_type2.text = "帮帮收藏"

            holder.tv_info.text = model.bangbangContent

            id = model.bangbangId

            if (!TextUtils.isEmpty(model.bangbangVideoUrl)) {//视频
                holder.image0.visibility = View.GONE
                holder.ll_image.visibility = View.GONE
                holder.rv_image.visibility = View.GONE
//                holder.player.visibility = View.VISIBLE

//                holder.player.setUp(
//                        model.bangbangVideoUrl, JCVideoPlayer.SCREEN_LAYOUT_LIST,"")
//                ImageLoader.getInstance().displayImage(model.bangbangImageUrl,holder.player.thumbImageView)
            } else {
//                holder.player.visibility = View.GONE
                if (model.bangbangImgUrl.size == 0) {
                    holder.image0.visibility = View.GONE
                    holder.ll_image.visibility = View.GONE
                    holder.rv_image.visibility = View.GONE
                } else if (model.bangbangImgUrl.size == 1) {
                    holder.rv_image.visibility = View.GONE
                    holder.ll_image.visibility = View.VISIBLE
                    holder.image0.visibility = View.VISIBLE
                    ImageLoader.getInstance().displayImage(model.bangbangImgUrl[0], holder.image0)
                    holder.ll_image.visibility = View.GONE
                    holder.rv_image.visibility = View.GONE
                } else if (model.bangbangImgUrl.size == 2) {
                    holder.rv_image.visibility = View.GONE
                    holder.ll_image.visibility = View.VISIBLE
                    ImageLoader.getInstance().displayImage(model.bangbangImgUrl[0], holder.image1)
                    ImageLoader.getInstance().displayImage(model.bangbangImgUrl[1], holder.image2)

                    holder.rv_image.visibility = View.GONE
                    holder.image0.visibility = View.GONE
                } else {
                    holder.ll_image.visibility = View.GONE
                    holder.rv_image.visibility = View.VISIBLE
                    holder.image0.visibility = View.GONE

                    val imageAdapter = ImageAdapter(context, model.bangbangImgUrl, 1)
                    holder.rv_image.adapter = imageAdapter
                }
            }

            holder.cl_1.setOnClickListener { v->
                val intent = Intent(context, DynamicDetailsActivity::class.java)
                intent.putExtra("flag", "1")
                intent.putExtra("position", position)
                intent.putExtra("id", collectList[position].bangbangId)
                context.startActivityForResult(intent,3)
            }

        } else {//活动
            holder.cl_1.visibility = View.GONE
            holder.cl_2.visibility = View.VISIBLE
            holder.tv_type2.text = "活动收藏"

            id = model.activityId

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

        holder.iv_del.setOnClickListener { v ->
            dialog(model.type, id, position)////0帮帮 1活动
        }

        holder.cl_2.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("name", collectList[position].activityName)
            bundle.putString("id", collectList[position].activityId)
            MyApplication.openActivity(context, EventDetailsActivity::class.java, bundle)
        }

    }

    fun dialog(type: String, id: String, i: Int) {
        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("确定删除收藏？")
        dialog.setPositiveButton("确定"
        ) { arg0, arg1 ->
            Event_221222223224.EventCollect(type, id, object : Event_221222223224.CollectCallBack {
                override fun collect() {
                    collectList.removeAt(i)
                    notifyDataSetChanged()
                    dialog.create().dismiss()
                }
            })
        }
        dialog.setNegativeButton("取消") { arg0, arg1 -> }
        dialog.show()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl_1 = view.findViewById<View>(R.id.cl_1)//动态
        val cl_2 = view.findViewById<View>(R.id.cl_2)//帮帮

        val line2 = view.findViewById<View>(R.id.line2)
        val line1 = view.findViewById<View>(R.id.line1)

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

//        val player = view.findViewById<JCVideoPlayerStandard>(R.id.player)

        //帮帮
        val tv_info = view.findViewById<TextView>(R.id.tv_info)

        val image0 = view.findViewById<ImageView>(R.id.image0)

        val ll_image = view.findViewById<LinearLayout>(R.id.ll_image)
        val image1 = view.findViewById<ImageView>(R.id.image1)
        val image2 = view.findViewById<ImageView>(R.id.image2)

        val rv_image = view.findViewById<RecyclerView>(R.id.rv_image)

        init {
            line2.visibility = View.GONE
            line1.visibility = View.VISIBLE

            tv_type2.visibility = View.VISIBLE
            iv_del.visibility = View.VISIBLE

            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            rv_image.layoutManager = linearLayoutManager
        }
    }

}