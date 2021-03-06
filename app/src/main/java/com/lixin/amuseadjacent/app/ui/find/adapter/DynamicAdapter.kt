package com.lixin.amuseadjacent.app.ui.find.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicDetailsActivity
import com.lixin.amuseadjacent.app.ui.find.model.FindModel
import com.lixin.amuseadjacent.app.ui.find.request.DynaComment_133134
import com.lixin.amuseadjacent.app.ui.find.request.Find_26
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.ImageAdapter
import com.lixin.amuseadjacent.app.util.*
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * 达人
 * flag 0动态，1帮帮，2话题
 * Created by Slingge on 2018/8/22
 */
class DynamicAdapter(val context: Activity, val flag: String, val dynaList: ArrayList<FindModel.dynamicModel>) : RecyclerView.Adapter<DynamicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_find, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dynaList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = dynaList[position]

        ImageLoader.getInstance().displayImage(model.dynamicIcon, holder.iv_header, ImageLoaderUtil.HeaderDIO())
        holder.tv_name.text = model.dynamicName
        holder.tv_effect.text = "影响力" + model.userEffectNum
        if (TextUtils.isEmpty(model.dynamicContent)) {
            holder.tv_info.visibility = View.GONE
        } else {
            holder.tv_info.visibility = View.VISIBLE
            holder.tv_info.text = model.dynamicContent
        }

        holder.tv_time.text = model.time
        holder.tv_comment.text = model.commentNum
        holder.tv_zan.text = model.zanNum

        if (model.dynamicUid == StaticUtil.uid) {
            holder.tv_follow.visibility = View.INVISIBLE
        } else {
            holder.tv_follow.visibility = View.VISIBLE
            if (model.isAttention == "0") {// 0未关注 1已关注
                holder.tv_follow.text = "关注"
                holder.tv_follow.visibility = View.VISIBLE
            } else {
                holder.tv_follow.text = "已关注"
                holder.tv_follow.visibility = View.INVISIBLE
            }
        }

        if (TextUtils.isEmpty(model.dynamicAddress) || model.dynamicAddress == "不显示位置") {
            holder.tv_add.visibility = View.GONE
            holder.iv_add.visibility = View.GONE
        } else {
            holder.iv_add.visibility = View.VISIBLE
            holder.tv_add.visibility = View.VISIBLE
            holder.tv_add.text = model.dynamicAddress
        }

        if (model.isZan == "0") {//0未赞过 1已赞过
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan, holder.tv_zan, 5)
        } else {
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, holder.tv_zan, 5)
        }

        holder.tv_zan.setOnClickListener { v ->
            DynaComment_133134.zan(model.dynamicId, "", object : Find_26.ZanCallback {
                override fun zan() {
                    if (model.isZan == "1") {
                        dynaList[position].zanNum = (dynaList[position].zanNum.toInt() - 1).toString()
                        dynaList[position].isZan = "0"
                        AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan, holder.tv_zan, 5)
                    } else {
                        dynaList[position].zanNum = (dynaList[position].zanNum.toInt() + 1).toString()
                        dynaList[position].isZan = "1"
                        AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, holder.tv_zan, 5)
                    }
                    holder.tv_zan.text = model.zanNum
                }
            })
        }

        if (TextUtils.isEmpty(model.dynamicVideo)) {
            holder.player.visibility = View.GONE
            holder.iv_start.visibility = View.GONE
            if (model.dynamicImgList.size > 0) {
                if (model.dynamicImgList.size < 3) {
                    holder.rv_image.visibility = View.GONE
                    holder.ll_image.visibility = View.VISIBLE

                    if (model.dynamicImgList.size == 1) {
                        holder.image0.visibility = View.VISIBLE
                        holder.image1.visibility = View.GONE
                        holder.image2.visibility = View.GONE

                        ImageLoader.getInstance().displayImage(model.dynamicImgList[0], holder.image0, ImageLoaderUtil.DIO())
                        holder.image0.setOnClickListener { v ->
                            ToPreviewPhoto.toPhoto(context, model.dynamicImgList, 0)
                        }
                    } else {
                        holder.image0.visibility = View.GONE

                        holder.image1.visibility = View.VISIBLE
                        holder.image2.visibility = View.VISIBLE

                        ImageLoader.getInstance().displayImage(model.dynamicImgList[0], holder.image1, ImageLoaderUtil.DIO())
                        ImageLoader.getInstance().displayImage(model.dynamicImgList[1], holder.image2, ImageLoaderUtil.DIO())
                        holder.image0.setOnClickListener { v ->
                            ToPreviewPhoto.toPhoto(context, model.dynamicImgList, 0)
                        }
                        holder.image0.setOnClickListener { v ->
                            ToPreviewPhoto.toPhoto(context, model.dynamicImgList, 1)
                        }
                    }
                } else {
                    holder.ll_image.visibility = View.GONE
                    holder.rv_image.visibility = View.VISIBLE
                    val imageAdapter = ImageAdapter(context, model.dynamicImgList, 1)
                    holder.rv_image.adapter = imageAdapter
                }
            } else {
                holder.rv_image.visibility = View.GONE
                holder.ll_image.visibility = View.GONE
            }
        } else {
            holder.rv_image.visibility = View.GONE
            holder.ll_image.visibility = View.GONE
            holder.player.visibility = View.VISIBLE
            holder.iv_start.visibility = View.VISIBLE

            holder.player.setOnClickListener { v ->
                JzvdStd.startFullscreen(context, JzvdStd::class.java, model.dynamicVideo, "")
//                Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL)
            }
            ImageLoader.getInstance().displayImage(model.dynamicImg, holder.player)
        }

        holder.tv_follow.setOnClickListener { v ->
            ProgressDialog.showDialog(context)
            Mail_138139.follow(dynaList[position].dynamicUid, object : Mail_138139.FollowCallBack {
                override fun follow() {
                    for (i in 0 until dynaList.size) {
                        if (dynaList[position].dynamicUid == dynaList[i].dynamicUid) {
                            if (dynaList[i].isAttention == "0") {// 0未关注 1已关注
                                dynaList[i].isAttention = "1"
                                holder.tv_follow.text = "已关注"
                                holder.tv_follow.visibility = View.INVISIBLE
                            } else {
                                dynaList[i].isAttention = "0"
                                holder.tv_follow.text = "关注"
                                holder.tv_follow.visibility = View.VISIBLE
                            }
                        }
                    }

                }
            })
        }


        holder.itemView.setOnClickListener { v ->
            val intent = Intent(context, DynamicDetailsActivity::class.java)
            intent.putExtra("flag", flag)
            intent.putExtra("position", position)
            intent.putExtra("id", dynaList[position].dynamicId)
            context.startActivityForResult(intent, 3)
        }

        holder.iv_header.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("auid", model.dynamicUid)
            bundle.putString("isAttention", model.isAttention)
            MyApplication.openActivity(context, PersonalHomePageActivity::class.java, bundle)
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cl_item = view.findViewById<ConstraintLayout>(R.id.cl_item)
        val cl_1 = view.findViewById<ConstraintLayout>(R.id.cl_1)
        val cl_2 = view.findViewById<ConstraintLayout>(R.id.cl_2)

        val iv_header = view.findViewById<CircleImageView>(R.id.iv_header)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_effect = view.findViewById<TextView>(R.id.tv_effect)
        val tv_follow = view.findViewById<TextView>(R.id.tv_follow)
        val tv_info = view.findViewById<TextView>(R.id.tv_info)

        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_comment = view.findViewById<TextView>(R.id.tv_comment)
        val tv_zan = view.findViewById<TextView>(R.id.tv_zan)

        val ll_image = view.findViewById<LinearLayout>(R.id.ll_image)
        val image0 = view.findViewById<ImageView>(R.id.image0)
        val image1 = view.findViewById<ImageView>(R.id.image1)
        val image2 = view.findViewById<ImageView>(R.id.image2)

        val rv_image = view.findViewById<RecyclerView>(R.id.rv_image)

        val player: ImageView = view.findViewById(R.id.player)
        val iv_start: ImageView = view.findViewById(R.id.iv_start)

        //地址
        val tv_add = view.findViewById<TextView>(R.id.tv_add)
        val iv_add = view.findViewById<ImageView>(R.id.iv_add)

        //活动
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_type = view.findViewById<TextView>(R.id.tv_type)
        val tv_actiivtyname = view.findViewById<TextView>(R.id.tv_actiivtyname)
        val tv_price = view.findViewById<TextView>(R.id.tv_price)
        val tv_activitytime = view.findViewById<TextView>(R.id.tv_activitytime)
        val tv_address = view.findViewById<TextView>(R.id.tv_address)
        val tv_num = view.findViewById<TextView>(R.id.tv_num)

        init {
            cl_1.visibility = View.VISIBLE
            cl_2.visibility = View.GONE

            val linearLayoutManager = GridLayoutManager(context, 3)
            rv_image.layoutManager = linearLayoutManager

        }
    }

}
