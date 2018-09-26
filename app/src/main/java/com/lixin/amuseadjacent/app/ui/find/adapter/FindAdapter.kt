package com.lixin.amuseadjacent.app.ui.find.adapter

import android.app.Activity
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
import com.lixin.amuseadjacent.app.ui.message.request.Mail_138139
import com.lixin.amuseadjacent.app.ui.mine.adapter.ImageAdapter
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.ImageLoaderUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.view.CircleImageView
import com.nostra13.universalimageloader.core.ImageLoader
import com.xiao.nicevideoplayer.NiceVideoPlayer
import com.xiao.nicevideoplayer.TxVideoPlayerController

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

            holder.tv_time.text = model.time
            holder.tv_comment.text = model.commentNum
            holder.tv_zan.text = model.zanNum

            if(model.dynamicUid== StaticUtil.uid){
                holder.tv_follow.visibility=View.INVISIBLE
            }else{
                holder.tv_follow.visibility=View.VISIBLE
                if (model.isAttention == "0") {// 0未关注 1已关注
                    holder.tv_follow.text = "关注"
                    holder.tv_follow.visibility=View.VISIBLE
                } else {
                    holder.tv_follow.visibility=View.INVISIBLE
                }
            }




            if (model.isZan == "0") {//0未赞过 1已赞过
                AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan, holder.tv_zan, 5)
            } else {
                AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, holder.tv_zan, 5)
            }
            if (TextUtils.isEmpty(model.dynamicVideo)) {
                holder.player.visibility = View.GONE

                if (model.dynamicImgList.size > 0) {
                    if (model.dynamicImgList.size <= 3) {
                        holder.rv_image.visibility = View.GONE
                        holder.ll_image.visibility = View.VISIBLE

                        if (model.dynamicImgList.size == 1) {
                            holder.image0.visibility = View.VISIBLE

                            holder.image1.visibility = View.GONE
                            holder.image2.visibility = View.GONE

                            ImageLoader.getInstance().displayImage(model.dynamicImgList[0], holder.image0)

                        } else {
                            holder.image0.visibility = View.GONE

                            holder.image1.visibility = View.VISIBLE
                            holder.image2.visibility = View.VISIBLE

                            ImageLoader.getInstance().displayImage(model.dynamicImgList[0], holder.image1)
                            ImageLoader.getInstance().displayImage(model.dynamicImgList[1], holder.image2)
                        }

                    } else {
                        holder.ll_image.visibility = View.GONE
                        holder.rv_image.visibility = View.VISIBLE
                        val imageAdapter = ImageAdapter(context, model.dynamicImgList,1)
                        holder.rv_image.adapter = imageAdapter
                    }
                } else {
                    holder.rv_image.visibility = View.GONE
                    holder.ll_image.visibility = View.GONE
                }
            } else {
                holder.player.visibility = View.VISIBLE
                holder.player.setPlayerType(NiceVideoPlayer.TYPE_IJK)
                holder.player.setUp(model.dynamicVideo, null)
                val controller = TxVideoPlayerController(context)
                controller.setTitle("")

                ImageLoader.getInstance().displayImage(model.dynamicImg, controller.imageView())
                holder.player.setController(controller)
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

            if (model.isAttention == "0") {// 0未关注 1已关注
                holder.tv_follow.text = "关注"
            } else {
                holder.tv_follow.text = "已关注"
            }
            if(model.userid== StaticUtil.uid){
                holder.tv_follow.visibility=View.INVISIBLE
            }else{
                holder.tv_follow.visibility=View.VISIBLE
            }


            if (model.isZan == "0") {//0未赞过 1已赞过
                AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan, holder.tv_zan, 5)
            } else {
                AbStrUtil.setDrawableLeft(context, R.drawable.ic_zan_hl, holder.tv_zan, 5)
            }

            holder.tv_actiivtyname.text = model.activityName
            holder.tv_price.text = model.activityMoney + "元/人"
            holder.tv_activitytime.text = "时间：" + model.activityTime
            holder.tv_address.text = "地点：" + model.activityAddress
            holder.tv_num.text = "人数：" + model.activityAllnum

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
                MyApplication.openActivity(context, DynamicDetailsActivity::class.java, bundle)
            } else if (actiivtyList != null) {
                val bundle = Bundle()
                bundle.putString("name", actiivtyList[position].activityName)
                bundle.putString("id", actiivtyList[position].activityId)
                MyApplication.openActivity(context, EventDetailsActivity::class.java, bundle)
            }
        }

    }

    fun stopPlay() {
        if (niceVideoPlayer != null) {
            niceVideoPlayer!!.pause()
        }
    }

    private var niceVideoPlayer: NiceVideoPlayer? = null

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

        val player: NiceVideoPlayer = view.findViewById(R.id.player)

        //活动
        val image = view.findViewById<ImageView>(R.id.image)
        val tv_type = view.findViewById<TextView>(R.id.tv_type)
        val tv_actiivtyname = view.findViewById<TextView>(R.id.tv_actiivtyname)
        val tv_price = view.findViewById<TextView>(R.id.tv_price)
        val tv_activitytime = view.findViewById<TextView>(R.id.tv_activitytime)
        val tv_address = view.findViewById<TextView>(R.id.tv_address)
        val tv_num = view.findViewById<TextView>(R.id.tv_num)


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

            player.setPlayerType(NiceVideoPlayer.TYPE_IJK) // IjkPlayer or MediaPlayer

            niceVideoPlayer = player
        }
    }

}