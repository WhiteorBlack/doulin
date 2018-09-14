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
object BalancePay_154 {


    interface BalancePayCallBack{
        fun pay()
    }

    /**
     * @param orderNum 订单号
     * @param amount 金额
     * */
    fun pay(orderNum: String, amount: String,balancePayCallBack: BalancePayCallBack) {
        val json = "{\"cmd\":\"payOrder\",\"uid\":\"" + StaticUtil.uid + "\",\"orderNum\":\"" + orderNum + "\",\"amount\":\"" + amount + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    balancePayCallBack.pay()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })

    }


}