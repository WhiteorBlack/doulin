package com.lixin.amuseadjacent.app.ui.service.request

import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * 所有的余额支付
 * Created by Slingge on 2018/9/14
 */
object RechargePay_154 {


    interface RechargePayCallBack{
        fun pay(orderNum: String)
    }

    /**
     * @param amount 金额
     * */
    fun pay(amount: String,rechargePayCallBack: RechargePayCallBack) {
        val json = "{\"cmd\":\"topUp\",\"uid\":\"" + StaticUtil.uid + "\",\"amount\":\"" + amount + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    rechargePayCallBack.pay(obj.getString("orderNum"))
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })

    }


}