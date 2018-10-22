package com.lixin.amuseadjacent.app.ui.find.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.iceteck.silicompressorr.VideoCompress
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.PermissionsDialog
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.adapter.ReleaseAdapter
import com.lixin.amuseadjacent.app.ui.find.request.ReleaseDynamicBang_220
import com.lixin.amuseadjacent.app.util.*
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.linxintechnologylibrary.app.util.SelectPictureUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_dynamic_release.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.io.File
import java.util.ArrayList

@Suppress("INACCESSIBLE_TYPE")
/**
 * 动态发布
 * Created by Slingge on 2018/8/22
 */
class DynamicReleaseActivity : BaseActivity(), ReleaseAdapter.ImageRemoveCallback, View.OnClickListener {

    private var albumAdapter: ReleaseAdapter? = null
    private val imageList = ArrayList<LocalMedia>()
    private val maxNum = 9

    private var flag = "0"//0动态发布，1帮帮发布

    private var videoPath = ""
    private var isChecked = true

    //压缩视频输出路径
    private val currentOutputVideoPath = MyApplication.CameraVideoPath + "out.mp4"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_release)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("发布动态")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "完成"
        tv_right.setOnClickListener(this)
        tv_address.setOnClickListener(this)
        flag = intent.getStringExtra("flag")
        if (flag == "1") {
            tv_address.visibility = View.GONE

            inittitle("发布帮帮")
            et_info.hint = "提出你的问题，邻里都来帮忙！！"
        }

        iv_del.setOnClickListener(this)

        val linearLayoutManager = GridLayoutManager(this, 3)

        rv_album.layoutManager = linearLayoutManager

        imageList.add(LocalMedia())

        albumAdapter = ReleaseAdapter(this, imageList, maxNum, this)
        rv_album.adapter = albumAdapter
        albumAdapter!!.setFlag(flag.toInt())

        checkPermission()
    }

    /**
     * 检查权限
     */
    private fun checkPermission() {
        val helper = PermissionHelper(this)
        helper.requestPermissions(object : PermissionHelper.PermissionListener {
            override fun doAfterGrand(vararg permission: String?) {
//                initMap()
            }

            override fun doAfterDenied(vararg permission: String?) {
            }

        }, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_del -> {
                iv_video.visibility = View.GONE
                iv_del.visibility = View.GONE
                rv_album.visibility = View.VISIBLE
            }
            R.id.tv_address -> {
                val bundle = Bundle()
                bundle.putBoolean("isChecked", isChecked)
                MyApplication.openActivityForResult(this, SelectionAddressActivity::class.java, bundle, 200)
            }
            R.id.tv_right -> {
                val content = AbStrUtil.etTostr(et_info)
                var adress = AbStrUtil.tvTostr(tv_address)
                if (adress == "不显示位置") {
                    adress = ""
                }

                if (TextUtils.isEmpty(content) && imageList.size == 1 && TextUtils.isEmpty(videoPath)) {
                    ToastUtil.showToast("请输入发布内容")
                    return
                }
                if (!TextUtils.isEmpty(videoPath)) {
                    abLog.e("视频路径", videoPath)
                    execCommand(content, videoPath, adress)
                    return
                }
                ProgressDialog.showDialog(this)
                ReleaseDynamicBang_220.release(this, flag, content, imageList, videoPath, adress,"","")
            }
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
                videoPath = PictureSelector.obtainMultipleResult(data)[i].path
                if (videoPath.endsWith(".mp4") || videoPath.endsWith(".avi") || videoPath.endsWith(".mkv")
                        || videoPath.endsWith(".FLV") || videoPath.endsWith(".MPG") || videoPath.endsWith(".RMVB")
                        || videoPath.endsWith(".3gp") || videoPath.endsWith(".WMV")
                        || videoPath.endsWith(".MP4") || videoPath.endsWith(".AVI") || videoPath.endsWith(".MKV")
                        || videoPath.endsWith(".flv") || videoPath.endsWith(".mpg") || videoPath.endsWith(".rmvb")
                        || videoPath.endsWith(".3GP") || videoPath.endsWith(".wmv")) {

                    Thread(Runnable {
                        PreviewingBitmap = ImageUtil.getVideoThumbnail(this, videoPath)
                        handler.sendEmptyMessage(1)
                    }).start()
                    rv_album.visibility = View.GONE
                    iv_video.visibility = View.VISIBLE
                    iv_del.visibility = View.VISIBLE
                    return
                } else {
                    imageList.add(imageList.size - 1, PictureSelector.obtainMultipleResult(data)[i])
                    videoPath = ""
                }
            }
            albumAdapter!!.notifyDataSetChanged()
        }

        if (requestCode == 200 && resultCode == 200 && data != null) {
            if (TextUtils.isEmpty(data.getStringExtra("address"))) {
                isChecked = true
                tv_address.text = ""
            } else {
                isChecked = false
                tv_address.text = data.getStringExtra("address")
            }
        }
    }

    private var PreviewingBitmap: Bitmap? = null
    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> if (PreviewingBitmap != null) {
                    iv_video.scaleType = ImageView.ScaleType.CENTER_CROP
                    iv_video.setImageBitmap(PreviewingBitmap)
                    try {
                        ImageFileUtil.saveFileBitmap(PreviewingBitmap)
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }


    //压缩视频
    private fun execCommand(content: String, videoPath: String, address: String) {
        ProgressDialog.showDialog(this)
        val destDir = File(currentOutputVideoPath)
        if (!destDir.exists()) {//如果不存在则创建
            destDir.mkdirs()
        }
        val mFile = File(currentOutputVideoPath)
        if (mFile.exists()) {
            mFile.delete()
        }

        VideoCompress.compressVideoMedium(videoPath, currentOutputVideoPath, object : VideoCompress.CompressListener {
            override fun onStart() {
            }

            override fun onSuccess() {
                ProgressDialog.showDialog(this@DynamicReleaseActivity)
                upVideo(currentOutputVideoPath, content, address)
            }

            override fun onFail() {
                ProgressDialog.dissDialog()
                ToastUtil.showToast("视频处理异常，按原文件上传")
                ProgressDialog.showDialog(this@DynamicReleaseActivity)
                upVideo(videoPath, content, address)
            }

            override fun onProgress(percent: Float) {
            }
        })
    }

    //上传视频
    private fun upVideo(path: String, content: String, address: String) {
        val retr = MediaMetadataRetriever()
        retr.setDataSource(path)
        val height = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)// 视频高度
        val width = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
        abLog.e("视频宽高",height+","+width)
        try {
            ReleaseDynamicBang_220.release(this@DynamicReleaseActivity, flag, content, imageList,
                    path, address, height, width)
        }catch (e:Exception){

        }
    }

    override fun imageRemove(i: Int) {
        imageList.removeAt(i)
        albumAdapter!!.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (PreviewingBitmap != null) {
            PreviewingBitmap!!.recycle()
            PreviewingBitmap = null
        }
    }

}