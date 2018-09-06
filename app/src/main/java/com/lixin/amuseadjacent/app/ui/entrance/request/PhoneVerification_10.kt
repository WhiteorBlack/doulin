package com.lixin.amuseadjacent.app.ui.entrance.request

import android.app.Activity
import android.os.Bundle
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.entrance.RegisterActivity
import com.lixin.amuseadjacent.app.ui.entrance.VerificationPasswordActivity
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * 验手机号
 * Created by Slingge on 2018/9/6
 */
object PhoneVerification_10 {


    fun verification(context: Activity, phone: String) {
        ProgressDialog.showDialog(context)

        val json = "{\"cmd\":\"phoneVerification\",\"phone\":\"$phone\"}";
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                val bundle = Bundle()
                bundle.putString("phone", phone)
                if (obj.getString("result") == "0") {//0未注册 1已注册
                    MyApplication.openActivity(context, RegisterActivity::class.java, bundle)
                } else if (obj.getString("result") == "1") {
                    MyApplication.openActivity(context, VerificationPasswordActivity::class.java, bundle)
                }
                context.finish()
            }
        })

    }


}