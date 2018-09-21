package com.lixin.amuseadjacent.app.ui.service.request

import android.app.Activity
import android.os.Bundle
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.service.activity.PaymentActivity
import com.lixin.amuseadjacent.app.ui.service.model.SubmissionModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * 提交订单
 * Created by Slingge on 2018/9/20
 */
object SubmissionOrder_38 {


    fun submission(context: Activity, mode: SubmissionModel) {
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", Gson().toJson(mode))
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            val bundle = Bundle()
                            bundle.putString("oderNum", obj.getString("oderNum"))
                            bundle.putString("balance", obj.getString("balance"))
                            bundle.putString("payMoney", mode.payprice)
                            MyApplication.openActivity(context, PaymentActivity::class.java,bundle)
                            context.finish()
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })
    }


}