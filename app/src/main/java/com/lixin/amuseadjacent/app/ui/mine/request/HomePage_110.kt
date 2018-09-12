package com.lixin.amuseadjacent.app.ui.mine.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.mine.model.HomePageModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2018/9/6
 */
object HomePage_110 {


    //auid 要查看的id
    fun userInfo(auid: String) {

        val json = "{\"cmd\":\"homepage\",\"uid\":\"" + StaticUtil.uid + "\",\"auid\":\"" + auid + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, HomePageModel::class.java)
                if (model.result == "0") {
                    StaticUtil.constellation = model.constellation
                    StaticUtil.age = model.age
                    StaticUtil.constellation
                    StaticUtil.communityName = model.communityName
                    StaticUtil.unitName=model.unitName
                    StaticUtil.doorNumber=model.doorNumber

                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

}