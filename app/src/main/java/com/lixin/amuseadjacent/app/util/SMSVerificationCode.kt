package com.lixin.amuseadjacent.app.util

import android.app.Activity
import android.content.Context

import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils

import org.json.JSONException
import org.json.JSONObject


/**
 * 发送短信
 * Created by Slingge on 2017/2/22 0022.
 */

object SMSVerificationCode {

    private val apKey = "6ade032e5230a353f34fe505a5ed2d62"
    private val url = "http://v.juhe.cn/sms/send?"

    fun sendSMS(context:Activity, phone: String, CODE: String) {
        ProgressDialog.showDialog(context)
        OkHttpUtils.post().url(url).addParams("mobile", phone)
                .addParams("tpl_id", "99242")
                .addParams("tpl_value", "%23code%23%3d$CODE")
                .addParams("key", apKey)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        try {
                            val obj = JSONObject(response)
                            if (obj.getString("error_code") == "0") {
                                ToastUtil.showToast("短信已发送，请注意查收")
                            } else {
                                ToastUtil.showToast(obj.getString("reason"))
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                })
    }

}
