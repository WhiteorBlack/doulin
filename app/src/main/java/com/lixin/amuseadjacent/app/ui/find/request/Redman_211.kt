package com.lixin.amuseadjacent.app.ui.find.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.find.model.RedmanModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 红人帮
 * Created by Slingge on 2018/9/17
 */
object Redman_211 {

    fun redList(type: Int) {
        val json = "{\"cmd\":\"getRedmanList\",\"uid\":\"" + StaticUtil.uid +
                "\",\"communityId\":\"" + StaticUtil.communityId + "\",\"type\":\"" + type + "\"}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, RedmanModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })

    }


}