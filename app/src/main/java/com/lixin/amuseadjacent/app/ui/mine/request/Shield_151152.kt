package com.lixin.amuseadjacent.app.ui.mine.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.mine.model.ShieldModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 屏蔽
 * 解除、添加
 * Created by Slingge on 2018/9/14
 */
object Shield_151152 {

    interface DelShieldCallback {
        fun del()
    }


    fun shidle(nowPage: Int) {
        val json = "{\"cmd\":\"myShield\",\"uid\":\"" + StaticUtil.uid + "\",\"nowPage\":\"" + nowPage +
                "\",\"pageCount\":\"" + "20" + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            val model = Gson().fromJson(response, ShieldModel::class.java)
                            if (model.result == "0") {
                                EventBus.getDefault().post(model)
                            } else {
                                ToastUtil.showToast(model.resultNote)
                            }
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })
    }


    //添加、解除屏蔽
    fun delShield(auid: String, shieldCallback: DelShieldCallback) {
        val json = "{\"cmd\":\"adddeleteShield\",\"uid\":\"" + StaticUtil.uid + "\",\"auid\":\"" + auid + "\"}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            shieldCallback.del()
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })

    }


}