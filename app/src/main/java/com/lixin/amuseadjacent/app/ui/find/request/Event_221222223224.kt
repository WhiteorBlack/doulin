package com.lixin.amuseadjacent.app.ui.find.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclModel
import com.lixin.amuseadjacent.app.ui.find.model.EventModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 活动
 * Created by Slingge on 2018/9/17
 */
object Event_221222223224 {


    fun EventList(nowPage:Int) {


        val json = "{\"cmd\":\"findActivityList\",\"uid\":\"" + StaticUtil.uid + "\",\"communityId\":\"" + StaticUtil.communityId +
                "\",\"nowPage\":\"" + nowPage + "\",\"pageCount\":\"" + "10" + "\"}"

        abLog.e("活动.................", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, EventModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })

    }


}