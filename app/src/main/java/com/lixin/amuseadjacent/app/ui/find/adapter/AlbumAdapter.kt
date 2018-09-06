package com.lixin.amuseadjacent.app.ui.find.adapter

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.mine.activity.MyAlbumActivity
import com.lixin.amuseadjacent.app.ui.mine.model.HomePageModel
import com.lixin.amuseadjacent.app.util.ImageFileUtil
import com.lixin.amuseadjacent.app.util.ImageLoaderUtil

import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.runproject.app.view.SquareImage
import com.nostra13.universalimageloader.core.ImageLoader


/**
 * 实名信息图片
 * Created by Slingge on 2017/5/3 0003.
 */

class AlbumAdapter(val context: Activity, val list: ArrayList<LocalMedia>, val maxNum: Int, val imageRemoveCallback: ImageRemoveCallback?) : RecyclerView.Adapter<AlbumAdapter.MyViewHolder>() {

    private var flag = -1//0只展示图片显示不删除，“加号”不选择图片，只跳转
    private var imageList: ArrayList<HomePageModel.albumModel>? = null

    interface ImageRemoveCallback {
        fun imageRemove(i: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_album_image, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        if (position == list.size - 1) {
            holder.image.setImageResource(R.drawable.ic_add2)
            holder.image.scaleType = ImageView.ScaleType.CENTER_INSIDE
            holder.iv_del.visibility = View.GONE
            holder.image.setOnClickListener { v ->
                if (flag == 0) {
                    val bundle = Bundle()
                    bundle.putSerializable("list", list)
                    MyApplication.openActivity(context, MyAlbumActivity::class.java, bundle)
                } else {
                    SelectPictureUtil.selectPicture(context, maxNum - list.size + 1, 0, false)
                }
            }
        } else {
            if (imageList == null) {
                val bitmap = ImageFileUtil.getBitmapFromPath(list[position].compressPath)//压缩的路径
                holder.image.scaleType = ImageView.ScaleType.CENTER_CROP
                if (flag == -1) {
                    holder.iv_del.visibility = View.VISIBLE
                }
                holder.image.setImageBitmap(bitmap)
            } else {
                holder.iv_del.visibility = View.GONE
                ImageLoader.getInstance().displayImage(imageList!![position].imgUrl, holder.image)
            }

        }


        holder.iv_del.setOnClickListener { v ->
            imageRemoveCallback!!.imageRemove(position)
        }

    }


    override fun getItemCount(): Int {
        if (imageList == null) {
            if (list.size - 1 == maxNum) {
                return list.size - 1
            } else {
                return list.size
            }
        } else {
            return imageList!!.size + 1
        }

    }

    fun setFlag(flag: Int) {
        this.flag = flag
        notifyDataSetChanged()
    }

    fun setFlag(flag: Int, imageList: ArrayList<HomePageModel.albumModel>) {
        this.flag = flag
        this.imageList!!.addAll(imageList)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<SquareImage>(R.id.image)
        var iv_del = itemView.findViewById<ImageView>(R.id.iv_del)
    }


}
