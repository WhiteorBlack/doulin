package com.lixin.amuseadjacent.app.ui.find.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.find.model.FindModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 发现首页
 * Created by Slingge on 2018/9/14
 */
object Find_26 {


    fun find(sex: String, content: String, nowPage: Int) {
        val json = "{\"cmd\":\"findList\",\"uid\":\"" + StaticUtil.uid + "\",\"communityId\":\"" + StaticUtil.CcommunityId + "\"}"

        abLog.e("首页.................", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, FindModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

}