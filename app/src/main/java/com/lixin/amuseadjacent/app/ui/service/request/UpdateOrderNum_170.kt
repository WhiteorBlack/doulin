package com.lixin.amuseadjacent.app.ui.service.request

import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * Created by Slingge on 2018/10/24
 */
object UpdateOrderNum_170 {

    fun upOrderNum(orderNum: String) {

        val json = "{\"cmd\":\"getnewordernum\",\"uid\":\"" + StaticUtil.uid + "\",\"orderNum\":\"" + orderNum + "\"}";

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            EventBus.getDefault().post(obj.getString("object"))
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })
    }

}