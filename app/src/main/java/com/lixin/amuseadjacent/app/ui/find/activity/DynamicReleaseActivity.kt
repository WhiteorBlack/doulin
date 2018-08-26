package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.PermissionsDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.AlbumAdapter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import kotlinx.android.synthetic.main.activity_dynamic_release.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.util.ArrayList

/**
 * 动态发布
 * Created by Slingge on 2018/8/22
 */
class DynamicReleaseActivity : BaseActivity(), AlbumAdapter.ImageRemoveCallback {

    private var albumAdapter: AlbumAdapter? = null
    private val imageList = ArrayList<LocalMedia>()
    private val maxNum =3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_release)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("动态发布")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "完成"
        tv_right.setOnClickListener { v ->
            MyApplication.openActivity(this, TalentAuthenticationActivity::class.java)
        }
        tv_address.setOnClickListener { v ->
            MyApplication.openActivity(this, SelectionAddressActivity::class.java)
        }


        val linearLayoutManager = GridLayoutManager(this,3)

        rv_album.layoutManager = linearLayoutManager

        imageList.add(LocalMedia())

        albumAdapter = AlbumAdapter(this,imageList,maxNum,this)
        rv_album.adapter = albumAdapter
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