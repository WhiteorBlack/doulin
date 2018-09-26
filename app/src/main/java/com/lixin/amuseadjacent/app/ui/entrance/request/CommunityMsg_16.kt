package com.lixin.amuseadjacent.app.ui.entrance.request

import android.app.Activity
import android.graphics.Bitmap
import android.widget.Toast
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.MainActivity
import com.lixin.amuseadjacent.app.ui.contacts.DemoCache
import com.lixin.amuseadjacent.app.ui.contacts.Preferences
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.entrance.model.UnityModel
import com.lixin.amuseadjacent.app.util.AppManager
import com.lixin.amuseadjacent.app.util.ImageFileUtil
import com.lixin.amuseadjacent.app.util.SharedPreferencesUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nim.uikit.common.util.log.LogUtil
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.auth.LoginInfo
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * 完善社区信息
 * Created by Slingge on 2018/9/5
 */
object CommunityMsg_16 {


    /**
     * sex 0女 1男
     * */
    fun communitMsg(context: Activity, nickname: String, icon: Bitmap, sex: Int, birthday: String, model: UnityModel.unitModel,communityName:String) {
        ProgressDialog.showDialog(context)
        val json = "{\"cmd\":\"addCommunityMessage\",\"uid\":\"" + StaticUtil.uid + "\",\"nickname\":\"" + nickname +
                "\",\"icon\":\"" + ImageFileUtil.bitmaptoString(icon) + "\",\"sex\":\"" + sex +
                "\",\"birthday\":\"" + birthday + "\",\"communityId\":\"" + model.communityId +
                "\",\"unitId\":\"" + model.unitId + "\",\"doorNumber\":\"" + model.num + "\"}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    ToastUtil.showToast("社区信息完善成功")
                    val rytoken = SharedPreferencesUtil.getSharePreStr(context, SharedPreferencesUtil.rytoken)
                    NimUIKit.login(LoginInfo(StaticUtil.uid, rytoken), object : RequestCallback<LoginInfo> {
                        override fun onSuccess(param: LoginInfo) {
                            LogUtil.i("NimUIKit", "login success")
                            DemoCache.setAccount(StaticUtil.uid)
                            Preferences.saveUserAccount(StaticUtil.uid)
                            Preferences.saveUserToken(rytoken)
                        }

                        override fun onFailed(code: Int) {
                            if (code == 302 || code == 404) {
//                                Toast.makeText(context, "帐号或密码错误", Toast.LENGTH_SHORT).show()
                            } else {
//                                Toast.makeText(context, "登录失败: $code", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onException(exception: Throwable) {
//                            Toast.makeText(context,"无效输入", Toast.LENGTH_LONG).show()
                        }
                    })

                    StaticUtil.communityId = model.communityId
                    StaticUtil.communityName = communityName
                    val sp = context.getSharedPreferences(SharedPreferencesUtil.NAME, 0)
                    sp.edit().putString(SharedPreferencesUtil.communityId, StaticUtil.communityId)
                            .putString(SharedPreferencesUtil.communityName, StaticUtil.communityName).commit()
                    MyApplication.openActivity(context, MainActivity::class.java)
                    context.finish()
                    AppManager.finishAllActivity()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })

    }


}