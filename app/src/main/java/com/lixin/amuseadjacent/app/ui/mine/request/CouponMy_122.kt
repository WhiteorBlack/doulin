package com.lixin.amuseadjacent.app.ui.mine.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.mine.model.CouponMyModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 我的优惠券列表
 * Created by Slingge on 2018/9/13
 */
object CouponMy_122 {


    /**
     * @param type //0未使用 1已使用 2已过期
     * */
    fun coupon(type: Int, page: Int) {
        val json = "{\"cmd\":\"securitiesList\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type +
                "\",\"nowPage\":\"" + page + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, CouponMyModel::class.java)
                EventBus.getDefault().post(model)
            }
        })
    }


}