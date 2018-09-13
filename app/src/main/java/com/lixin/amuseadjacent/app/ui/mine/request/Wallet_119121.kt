package com.lixin.amuseadjacent.app.ui.mine.request

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.lixin.amuseadjacent.app.ui.mine.model.BalanceDetailsModel
import com.lixin.amuseadjacent.app.ui.mine.model.SginInModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**余额明细
 *
 * Created by Slingge on 2018/9/12
 */
object Wallet_119121 {

    interface WithdrawCallBack {
        fun withdraw()
    }

    //零钱明细
    fun BalanceDetails(nowPage: Int) {
        val json = "{\"cmd\":\"getUserBalanceList\",\"uid\":\"" + StaticUtil.uid +
                "\",\"nowPage\":\"" + nowPage + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, BalanceDetailsModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

    //提现
    fun withdraw(cardId: String, amount: String, withdrawCallBack: WithdrawCallBack) {
        val json = "{\"cmd\":\"withdrawCash\",\"uid\":\"" + StaticUtil.uid +
                "\",\"amount\":\"" + amount + "\",\"cardId\":\"" + cardId + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    StaticUtil.balance = obj.getString("balance")
                    withdrawCallBack.withdraw()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


}