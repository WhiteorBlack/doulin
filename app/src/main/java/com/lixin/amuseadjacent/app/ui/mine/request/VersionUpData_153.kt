package com.lixin.amuseadjacent.app.ui.mine.request

import android.content.Context
import com.lixin.amuseadjacent.app.util.AppVersionUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * 版本更新
 * Created by Slingge on 2018/9/14
 */
object VersionUpData_153 {

    interface UpdataCallBack {
        fun updata(updataUrl: String, versionName: String)
    }

    fun updata(context: Context, updataCallBack: UpdataCallBack) {
        val json = "{\"cmd\":\"getversion\",\"type\":\"" +"1"+ "\"}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            val num = obj.getString("versionNumber").toDouble()
                            if (num > AppVersionUtil.getVersionCode(context)) {
                                updataCallBack.updata(obj.getString("updataAddress"), obj.getString("versionName"))
                            }
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })

    }


}