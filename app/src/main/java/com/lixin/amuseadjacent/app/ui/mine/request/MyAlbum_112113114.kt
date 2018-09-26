package com.lixin.amuseadjacent.app.ui.mine.request

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.util.ImageFileUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject
import java.io.File

/**
 * 上传我的相册，删除
 * Created by Slingge on 2018/9/6
 */
object MyAlbum_112113114 {

    interface DelAlbumCallBacl {
        fun delAlbum(i: Int)
    }

    fun delAlbum(imgId: String, i: Int, delAlbumCallBacl: DelAlbumCallBacl) {

        val json = "{\"cmd\":\"deleteImage\",\"uid\":\"" + StaticUtil.uid + "\",\"imgId\":\"" + imgId + "\"}";
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    delAlbumCallBacl.delAlbum(i)
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


    fun AddAlbum(context: Activity, imageList: ArrayList<LocalMedia>) {
        ProgressDialog.showDialog(context)
        val listfile = arrayListOf<File>() //map是无序集合尽量用list

        abLog.e("上传相册", Gson().toJson(imageList))

        for (i in 0 until imageList.size - 1) {//图片路径集合
            if (!imageList[i].path.contains("http://")) {
                val file = File(imageList[i].path)//保存压缩
                listfile.add(file)
            }
        }

        OkHttpUtils.post().url(StaticUtil.AlbumsUrl).addParams("uid", StaticUtil.uid)
                .files("file", listfile).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            ToastUtil.showToast("上传成功")
//                            setHeaderImage(imageList[0].pictureType, context, imageList)//设置第一张为头像
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })
    }


    interface HeaderImageCallBack{
        fun headerIcon(iconUrl:String)
    }

    //设置为头像
    fun setHeaderImage(imgId: String, headerImageCallBack: HeaderImageCallBack) {
        val json = "{\"cmd\":\"addImageIcon\",\"uid\":\"" + StaticUtil.uid + "\",\"imgId\":\"" + imgId + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    StaticUtil.headerUrl = obj.getString("object")
                    headerImageCallBack.headerIcon(StaticUtil.headerUrl)
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


}