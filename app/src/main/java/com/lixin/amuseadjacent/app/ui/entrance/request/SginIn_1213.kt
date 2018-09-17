package com.lixin.amuseadjacent.app.ui.entrance.request

import android.app.Activity
import cn.jpush.android.api.JPushInterface
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.MainActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.entrance.PersonalImageActivity
import com.lixin.amuseadjacent.app.util.AppManager
import com.lixin.amuseadjacent.app.util.Md5Util
import com.lixin.amuseadjacent.app.util.SharedPreferencesUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Slingge on 2018/9/6
 */
object SginIn_1213 {

    //短信登录
    fun smsSgin(context: Activity, phone: String) {
        ProgressDialog.showDialog(context)
        val json = "{\"cmd\":\"phoneLogin\",\"phone\":\"" + phone + "\",\"token\":\"" + StaticUtil.getJpushToken(context) + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    try {
                        StaticUtil.uid = obj.getString("uid")
                        val sp = context.getSharedPreferences(SharedPreferencesUtil.NAME, 0)

                        if (obj.getString("iswanshan") == "0") {//0未完善社区信息 1已完善社区信息
                            MyApplication.openActivity(context, PersonalImageActivity::class.java)
                        } else {
                            StaticUtil.communityId = obj.getString("communityId")
                            sp.edit().putString(SharedPreferencesUtil.communityId, StaticUtil.communityId).commit()
                            MyApplication.openActivity(context, MainActivity::class.java)
                        }
                        sp.edit().putString(SharedPreferencesUtil.Phone, phone).putString(SharedPreferencesUtil.uid, StaticUtil.uid)
                                .commit()
                        StaticUtil.phone = phone
                        AppManager.finishAllActivity()
                    } catch (e: JSONException) {
                    }

                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


    //密码登录
    fun passSgin(context: Activity, phone: String, pass: String) {
        ProgressDialog.showDialog(context)
        val json = "{\"cmd\":\"userLogin\",\"phone\":\"" + phone + "\",\"password\":\"" + Md5Util.md5Encode(pass) +
                "\",\"token\":\"" + StaticUtil.getJpushToken(context) + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    try {
                        StaticUtil.uid = obj.getString("uid")
                        val sp = context.getSharedPreferences(SharedPreferencesUtil.NAME, 0)
                        if (obj.getString("iswanshan") == "0") {//0未完善社区信息 1已完善社区信息
                            MyApplication.openActivity(context, PersonalImageActivity::class.java)
                        } else {
                            StaticUtil.communityId = obj.getString("communityId")
                            sp.edit().putString(SharedPreferencesUtil.communityId, StaticUtil.communityId).commit()
                            MyApplication.openActivity(context, MainActivity::class.java)
                        }
                        StaticUtil.phone = phone
                        sp.edit().putString(SharedPreferencesUtil.Phone, phone).putString(SharedPreferencesUtil.Pass, pass)
                                .putString(SharedPreferencesUtil.uid, StaticUtil.uid).commit()
                        AppManager.finishAllActivity()
                    } catch (e: JSONException) {
                    }
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


}