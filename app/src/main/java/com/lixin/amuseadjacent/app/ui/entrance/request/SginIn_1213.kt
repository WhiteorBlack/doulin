package com.lixin.amuseadjacent.app.ui.entrance.request

import android.app.Activity
import android.widget.Toast
import cn.jpush.android.api.JPushInterface
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.MainActivity
import com.lixin.amuseadjacent.app.ui.contacts.DemoCache
import com.lixin.amuseadjacent.app.ui.contacts.Preferences
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.entrance.PersonalImageActivity
import com.lixin.amuseadjacent.app.util.AppManager
import com.lixin.amuseadjacent.app.util.Md5Util
import com.lixin.amuseadjacent.app.util.SharedPreferencesUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nim.uikit.common.util.log.LogUtil
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.auth.LoginInfo
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
                            StaticUtil.CcommunityId = obj.getString("communityId")
                            sp.edit().putString(SharedPreferencesUtil.communityId, StaticUtil.CcommunityId).commit()
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
                            StaticUtil.CcommunityId = obj.getString("communityId")
                            sp.edit().putString(SharedPreferencesUtil.communityId, StaticUtil.CcommunityId).commit()

                            NimUIKit.login(LoginInfo(obj.getString("uid"), obj.getString("rytoken")), object : RequestCallback<LoginInfo> {
                                override fun onSuccess(param: LoginInfo) {
                                    LogUtil.i("NimUIKit", "login success")
                                    DemoCache.setAccount(obj.getString("uid"))
                                    Preferences.saveUserAccount(obj.getString("uid"))
                                    Preferences.saveUserToken(obj.getString("rytoken"))
                                }

                                override fun onFailed(code: Int) {
                                    if (code == 302 || code == 404) {
                                        Toast.makeText(context, "帐号或密码错误", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "登录失败: $code", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onException(exception: Throwable) {
                                    Toast.makeText(context,"无效输入", Toast.LENGTH_LONG).show()
                                }
                            })
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