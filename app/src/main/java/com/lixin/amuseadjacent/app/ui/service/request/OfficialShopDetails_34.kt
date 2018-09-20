package com.lixin.amuseadjacent.app.ui.service.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.service.model.OfficialShopDetailsModel
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2018/9/19
 */
object OfficialShopDetails_34 {

    // type 0新鲜果蔬 1洗衣洗鞋 2超市便利
    fun OfficialShopDetails(type: Int, nowPage: Int) {

        val json = "{\"cmd\":\"getServiceComment\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type +
                "\",\"communityId\":\"" + StaticUtil.communityId + "\",\"nowPage\":\"" + nowPage +
                "\",\"pageCount\":\"" + "15" + "\"}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, OfficialShopDetailsModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

}