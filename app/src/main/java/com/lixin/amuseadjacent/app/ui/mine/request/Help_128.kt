package com.lixin.amuseadjacent.app.ui.mine.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.mine.model.HelpModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 帮助
 * Created by Slingge on 2018/9/13
 */
object Help_128 {


    fun help() {
        val json = "{\"cmd\":\"getHelp\"" + "}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, HelpModel::class.java)
                        if (model.result == "0") {
                            EventBus.getDefault().post(model)
                        } else {
                            ToastUtil.showToast(model.resultNote)
                        }
                    }
                })

    }


}