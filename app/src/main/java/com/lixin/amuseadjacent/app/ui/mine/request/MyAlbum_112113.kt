package com.lixin.amuseadjacent.app.ui.mine.request

import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

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


}