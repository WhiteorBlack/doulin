package com.lixin.amuseadjacent.app.ui.mine.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.mine.model.CollectModel
import com.lixin.amuseadjacent.app.ui.mine.model.CouponMyModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 收藏
 * Created by Slingge on 2018/9/13
 */
object CollectList_123 {

    fun collect(page: Int) {
        val json = "{\"cmd\":\"getCollectList\",\"uid\":\"" + StaticUtil.uid +
                "\",\"nowPage\":\"" + page + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, CollectModel::class.java)
                EventBus.getDefault().post(model)
            }
        })
    }


}