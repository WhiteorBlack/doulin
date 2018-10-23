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
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.activity.DynamicDetailsActivity
import com.lixin.amuseadjacent.app.ui.find.activity.EventDetailsActivity
import com.lixin.amuseadjacent.app.ui.find.model.FindModel
import com.lixin.amuseadjacent.app.ui.find.request.ActivityComment_272829210
import com.lixin.amuseadjacent.app.ui.find.request.DynaComment_133134
import com.lixin.amuseadjacent.app.ui.find.request.Find_26
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.ImageAdapter
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.ImageLoaderUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.ToPreviewPhoto
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard

/**
 * Created by Slingge on 2018/8/18
 */
class FindAdapter(val context: Activity, val dynaList: ArrayList<FindModel.dynamicModel>?,
                  val actiivtyList: ArrayList<FindModel.activityModel>?) : RecyclerView.Adapter<FindAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_find, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (dynaList != null) {
            return dynaList.size
        } else {
            return actiivtyList!!.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (dynaList != null) {//动态
            val model = dynaList!![position]

            ImageLoader.getInstance().displayImage(model.dynamicIcon, holder.iv_header, ImageLoaderUtil.HeaderDIO())
            holder.tv_name.text = model.dynamicName
            holder.tv_effect.text = "影响力" + model.userEffectNum
            holder.tv_info.text = model.dynamicContent
            if (TextUtils.isEmpty(model.dynamicContent)) {
                holder.tv_info.visibility = View.GONE
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
                    holder.tv_follow.visibility = View.INVISIBLE
                }
            }

            if (TextUtils.isEmpty(model.dynamicAddress) || model.dynamicAddress == "不显示位置") {
                holder.tv_add.visibility = View.GONE
                holder.iv_add.visibility = View.GONE
            } else {
                holder.tv_add.visibility = View.VISIBLE
                holder.iv_add.visibility = View.VISIBLE
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
                        } else {
                            dynaList[position].zanNum = (dynaList[position].zanNum.toInt() + 1).toString()
                            dynaList[position].isZan = "1"
                        }
                        notifyDataSetChanged()
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

                            ImageLoader.getInstance().displayImage(model.dynamicImgList[0], holder.image0)
                            holder.image0.setOnClickListener { v ->
                                ToPreviewPhoto.toPhoto(context, model.dynamicImgList, 0)
                            }

                        } else {
                            holder.image0.visibility = View.GONE

                            holder.image1.visibility = View.VISIBLE
                            holder.image2.visibility = View.VISIBLE

                            ImageLoader.getInstance().displayImage(model.dynamicImgList[0], holder.image1)
                            ImageLoader.getInstance().displayImage(model.dynamicImgList[1], holder.image2)

                            holder.image1.setOnClickListener { v ->
                                ToPreviewPhoto.toPhoto(context, model.dynamicImgList, 0)
                            }
                            holder.image2.setOnClickListener { v ->
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
                holder.ll_image.visibility = View.GONE
                holder.rv_image.visibility = View.GONE

                holder.player.visibility = View.VISIBLE
                holder.iv_start.visibility = View.VISIBLE

                holder.player.setOnClickListener { v ->
                    JCVideoPlayerStandard.startFullscreen(context, JCVideoPlayerStandard::class.java, model.dynamicVideo, "")
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
                                } else {
                                    dynaList[i].isAttention = "0"
                                }
                            }
                        }
                        notifyDataSetChanged()
                    }
                })
            }
        } else {//活动
            holder.cl_1.visibility = View.GONE
            holder.cl_2.visibility = View.VISIBLE

            val model = actiivtyList!![position]
            ImageLoader.getInstance().displayImage(model.activityImg, holder.image)
            if (model.issignup == "0") {//0未报名 1已报名
                holder.tv_type.text = "未报名"
            } else {
                holder.tv_type.text = "已报名"
            }


            if (model.userid == StaticUtil.uid) {
                holder.tv_follow.visibility = View.INVISIBLE
            } else {
                holder.tv_follow.visibility = View.VISIBLE
                if (model.isAttention == "0") {// 0未关注 1已关注
                    holder.tv_follow.text = "关注"
                } else {
                    holder.tv_follow.text = "已关注"
                    holder.tv_follow.visibility = View.INVISIBLE
                }
            }

            holder.tv_follow.setOnClickListener { v ->
                ProgressDialog.showDialog(context)
                Mail_138139.follow(actiivtyList[position].userid, object : Mail_138139.FollowCallBack {
                    override fun follow() {
                        for (i in 0 until actiivtyList.size) {
                            if (actiivtyList[position].userid == actiivtyList[i].userid) {
                                if (actiivtyList[i].isAttention == "0") {// 0未关注 1已关注
                                    actiivtyList[i].isAttention = "1"
                                } else {
                                    actiivtyList[i].isAttention = "0"
                                }
                            }
                        }
                        notifyDataSetChanged()
                    }
                })
            }

            if (model.isZan == "0") {//0未赞过 1已赞过
                AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan, holder.tv_zan, 5)
            } else {
                AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, holder.tv_zan, 5)
            }

            holder.tv_zan.setOnClickListener { v ->
                ActivityComment_272829210.zan("0", model.activityId, "", object : Find_26.ZanCallback {
                    override fun zan() {
                        if (model.isZan == "1") {
                            actiivtyList[position].isZan = "0"
                            actiivtyList[position].zanNum = (actiivtyList[position].zanNum.toInt() - 1).toString()
                        } else {
                            actiivtyList[position].isZan = "1"
                            actiivtyList[position].zanNum = (actiivtyList[position].zanNum.toInt() + 1).toString()
                        }
                        notifyItemChanged(position)
                    }
                })
            }

            holder.tv_actiivtyname.text = model.activityName
            holder.tv_price.text = model.activityMoney + "元/人"
            holder.tv_activitytime.text = "时间：" + model.activityTime
            holder.tv_address.text = "地点：" + model.activityAddress
            holder.tv_num.text = "人数：" + model.activityNownum + "/" + model.activityAllnum

            ImageLoader.getInstance().displayImage(model.userIcon, holder.iv_header, ImageLoaderUtil.HeaderDIO())
            holder.tv_name.text = model.userName
            holder.tv_effect.text = "影响力" + model.userEffectNum
            holder.tv_info.visibility = View.GONE

            holder.tv_time.text = model.time
            holder.tv_comment.text = model.commentNum
            holder.tv_zan.text = model.zanNum
        }


        holder.itemView.setOnClickListener { v ->
            if (dynaList != null) {
                val bundle = Bundle()
                bundle.putString("flag", "0")
                bundle.putString("id", dynaList[position].dynamicId)
                bundle.putInt("position", position)
                val intent = Intent(context, DynamicDetailsActivity::class.java)
                intent.putExtras(bundle)
                context.startActivityForResult(intent, 1)
            } else if (actiivtyList != null) {//FindFramgent中不使用
                val bundle = Bundle()
                bundle.putString("name", actiivtyList[position].activityName)
                bundle.putString("id", actiivtyList[position].activityId)
                bundle.putInt("count", position)
                val intent = Intent(context, EventDetailsActivity::class.java)
                intent.putExtras(bundle)
                context.startActivityForResult(intent, 2)
            }
        }

        holder.iv_header.setOnClickListener { v ->
            val bundle = Bundle()
            if (dynaList != null) {
                bundle.putString("auid", dynaList[position].dynamicUid)
                bundle.putString("isAttention", dynaList[position].isAttention)
            } else {
                bundle.putString("auid", actiivtyList!![position].userid)
                bundle.putString("isAttention", actiivtyList[position].isAttention)
            }
            MyApplication.openActivity(context, PersonalHomePageActivity::class.java, bundle)
        }

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

        //活动
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_type = view.findViewById<TextView>(R.id.tv_type)
        val tv_actiivtyname = view.findViewById<TextView>(R.id.tv_actiivtyname)
        val tv_price = view.findViewById<TextView>(R.id.tv_price)
        val tv_activitytime = view.findViewById<TextView>(R.id.tv_activitytime)
        val tv_address = view.findViewById<TextView>(R.id.tv_address)
        val tv_num = view.findViewById<TextView>(R.id.tv_num)

        //地址
        val tv_add = view.findViewById<TextView>(R.id.tv_add)
        val iv_add = view.findViewById<ImageView>(R.id.iv_add)
        //分割线
        val line = view.findViewById<View>(R.id.line)
        val line_view = view.findViewById<View>(R.id.line_view)

        init {
            cl_1.visibility = View.VISIBLE
            cl_2.visibility = View.GONE

            val linearLayoutManager = GridLayoutManager(context, 3)
            rv_image.layoutManager = linearLayoutManager

            line.visibility = View.GONE
            line_view.visibility = View.VISIBLE

            player.isFocusable = false
        }
    }

}