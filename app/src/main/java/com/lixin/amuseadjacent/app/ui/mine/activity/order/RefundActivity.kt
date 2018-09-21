package com.lixin.amuseadjacent.app.ui.mine.activity.order

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.PermissionsDialog
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.AlbumAdapter
import com.lixin.amuseadjacent.app.ui.mine.request.RefundOrder_147
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_refund.*
import java.util.ArrayList

/**
 * 申请退款
 * Created by Slingge on 2018/9/4
 */
class RefundActivity : BaseActivity(), AlbumAdapter.ImageRemoveCallback {

    private var albumAdapter: AlbumAdapter? = null
    private val imageList = ArrayList<LocalMedia>()
    private val maxNum = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refund)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("申请退款")

        val linearLayoutManager = GridLayoutManager(this, 3)

        rv_album.layoutManager = linearLayoutManager

        imageList.add(LocalMedia())

        albumAdapter = AlbumAdapter(this, imageList, maxNum, this)
        rv_album.adapter = albumAdapter

        tv_enter.setOnClickListener { v ->
            val content = AbStrUtil.etTostr(et_reason)
            if (TextUtils.isEmpty(content)) {
                ToastUtil.showToast("请输入退款原因")
                return@setOnClickListener
            }
            ProgressDialog.showDialog(this)
            RefundOrder_147.refund(this, intent.getStringExtra("num"), content, imageList, intent.getIntExtra("position", -1))
        }
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