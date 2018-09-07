package com.lixin.amuseadjacent.app.ui.mine.request

import android.app.Activity
import android.content.Context
import com.lixin.amuseadjacent.app.util.ImageFileUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
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
object MyAlbum_112113 {

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


    fun AddAlbum(context: Activity, imageList: List<LocalMedia>) {
        val listfile = arrayListOf<File>() //map是无序集合尽量用list
        for (i in 0 until imageList.size - 1) {//图片路径集合
            val file = ImageFileUtil.saveFilePath(imageList[i].path)//保存压缩
            listfile.add(file)
        }

        OkHttpUtils.post().url("http://39.107.106.122/wisdom/api/addAlbums").addParams("uid", StaticUtil.uid)
                .files("file", listfile).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            ToastUtil.showToast("上传成功")
                            context.finish()
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })


    }


}