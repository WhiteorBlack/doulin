package com.lixin.amuseadjacent.app.ui.service.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.service.model.PopularShopDetailsModel
import com.lixin.amuseadjacent.app.ui.service.model.PopularShopModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2018/9/19
 */
object PopularShop_39310 {


    fun shop(content: String) {
        val json = "{\"cmd\":\"getCommunityShop\",\"uid\":\"" + StaticUtil.uid + "\",\"communityId\":\"" + StaticUtil.communityId +
                "\",\"content\":\"" + content + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, PopularShopModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    fun shopDetails(shopId: String) {
        val json = "{\"cmd\":\"getCommunityShopDetail\",\"uid\":\"" + StaticUtil.uid +
                "\",\"shopId\":\"" + shopId + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, PopularShopDetailsModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


}