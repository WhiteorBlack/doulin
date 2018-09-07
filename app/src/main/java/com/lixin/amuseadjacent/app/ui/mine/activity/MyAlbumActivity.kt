package com.lixin.amuseadjacent.app.ui.mine.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.PermissionsDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.AlbumAdapter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*

/**
 * 我的相册
 * Created by Slingge on 2018/9/1
 */
class MyAlbumActivity : BaseActivity(), AlbumAdapter.ImageRemoveCallback {

    private val maxNum = 9
    private var imageList = ArrayList<LocalMedia>()
    private var albumAdapter: AlbumAdapter? = null

    private var ishowDel =false// 不显示删除， 显示删除

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        init()
    }


    private fun init() {
        inittitle("我的相册")
        StatusBarWhiteColor()

        tv_right.visibility = View.VISIBLE
        tv_right.text = "编辑"
        tv_right.setOnClickListener { v ->
            ishowDel = !ishowDel
            albumAdapter!!.setDelShow(ishowDel)
            if(ishowDel){
                tv_right.text="完成"
            }else{
                tv_right.text="编辑"
            }
        }

        val gridLayoutManager = GridLayoutManager(this, 4)
        xrecyclerview.layoutManager = gridLayoutManager

        imageList.add(LocalMedia())
        albumAdapter = AlbumAdapter(this, imageList, maxNum, this)
        xrecyclerview.adapter = albumAdapter
        albumAdapter!!.setDelShow(ishowDel)
    }

    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            SelectPictureUtil.selectPicture(this, maxNum, 0, false)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡和拍照权限")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            // 图片、视频、音频选择结果回调
            for (i in 0 until PictureSelector.obtainMultipleResult(data).size) {
                imageList.add(imageList.size - 1, PictureSelector.obtainMultipleResult(data)[i])
            }
            albumAdapter!!.notifyDataSetChanged()
        }
    }


    override fun imageRemove(i: Int) {
        imageList.removeAt(i)
        albumAdapter!!.notifyDataSetChanged()
    }


}