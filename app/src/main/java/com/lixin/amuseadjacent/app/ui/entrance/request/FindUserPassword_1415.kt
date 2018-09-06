package com.lixin.amuseadjacent.app.ui.entrance.request

import android.app.Activity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.util.Md5Util
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * Created by Slingge on 2018/9/6
 */
object FindUserPassword_1415 {

    //忘记密码
    fun FindUserPassword(context: Activity, phone: String, pass: String) {
        ProgressDialog.showDialog(context)
        val json = "{\"cmd\":\"findUserPassword\",\"phone\":\"" + phone +
                "\",\"password\":\"" + Md5Util.md5Encode(pass) + "\"}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    ToastUtil.showToast("密码修改成功")
                    context.finish()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })

    }


    //修改密码
    fun ModifyPassword(context: Activity, oldPass: String, newPass: String) {
        ProgressDialog.showDialog(context)
        val json = "{\"cmd\":\"editUserPassword\",\"uid\":\"" + StaticUtil.uid +
                "\",\"oldPassword\":\"" + Md5Util.md5Encode(oldPass) +
                "\",\"newPassword\":\"" + Md5Util.md5Encode(newPass) +"\"}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    ToastUtil.showToast("密码修改成功")
                    context.finish()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })

    }

}