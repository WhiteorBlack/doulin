package com.lixin.amuseadjacent.app.ui.entrance.request

import android.app.Activity
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.entrance.PersonalImageActivity
import com.lixin.amuseadjacent.app.util.Md5Util
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.SharedPreferencesUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Slingge on 2018/9/5
 */
object Register_11 {


    fun register(context: Activity, phone: String, pass: String, userInviteCode: String) {

        val json = "{\"cmd\":\"userRegister\",\"phone\":\"" + phone + "\",\"password\":\"" + Md5Util.md5Encode(pass) +
                "\",\"userInviteCode\":\"" + userInviteCode + "\",\"token\":\"" + StaticUtil.getJpushToken(context) + "\"}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0" || obj.getString("resultNote") == "该手机号已注册") {
                    ToastUtil.showToast("注册成功")

                    StaticUtil.uid = obj.getString("uid")

                    StaticUtil.rytoken = obj.getString("rytoken")
                    StaticUtil.phone = phone
                    val sp = context.getSharedPreferences(SharedPreferencesUtil.NAME, 0)
                    sp.edit().putString(SharedPreferencesUtil.Phone, phone).putString(SharedPreferencesUtil.Pass, pass)
                            .putString(SharedPreferencesUtil.uid, StaticUtil.uid).putString(SharedPreferencesUtil.rytoken, StaticUtil.rytoken).commit()
                    MyApplication.openActivity(context, PersonalImageActivity::class.java)
                    context.finish()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


}