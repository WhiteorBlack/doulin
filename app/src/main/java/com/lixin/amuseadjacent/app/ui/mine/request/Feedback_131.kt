package com.lixin.amuseadjacent.app.ui.mine.request

import android.app.Activity
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * 意见反馈
 * Created by Slingge on 2018/9/14
 */
object Feedback_131 {

    fun feed(context: Activity, content: String) {
        val json = "{\"cmd\":\"addFeedback\",\"uid\":\"" + StaticUtil.uid +
                "\",\"type\":\"" + "0" + "\",\"content\":\"" + content + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            ToastUtil.showToast("提交成功")
                            context.finish()
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })
    }


}