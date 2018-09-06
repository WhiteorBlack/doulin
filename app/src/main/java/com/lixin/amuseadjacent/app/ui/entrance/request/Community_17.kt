package com.lixin.amuseadjacent.app.ui.entrance.request

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.entrance.model.CommunityModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 获取社区、单元
 * Created by Slingge on 2018/9/5
 */
object Community_17 {


    //获取社区
    fun getCommunity(context:Activity) {

        ProgressDialog.showDialog(context)
        val json = "{\"cmd\":\"getCommunityList\"" + "}";
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, CommunityModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })

    }




}