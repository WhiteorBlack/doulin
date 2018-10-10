package com.lixin.amuseadjacent.app.ui.mine.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 我的动态
 * Created by Slingge on 2018/9/21
 */
object MyDynamic_132 {


    fun dynamic(nowPage: Int, auid: String) {
        val json = "{\"cmd\":\"myDynamicList\",\"uid\":\"" + auid + "\",\"communityId\":\"" + StaticUtil.communityId +
                "\",\"nowPage\":\"" + nowPage + "\",\"pageCount\":\"" + "15" + "\",\"myuid\":\"" + StaticUtil.uid + "\"}"

        abLog.e("动态、帮帮.................", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, DynamiclModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


}