package com.lixin.amuseadjacent.app.ui.mine.request

import android.app.Activity
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject
import java.io.File

/**
 * 实名认证
 * Created by Slingge on 2018/9/13
 */
object RealnameAuthentication_126 {

    fun authentication(context: Activity, realName: String, idCard: String, phone: String, justPath: String, backPath: String) {

        val justFile = File(justPath)
        val backFile = File(backPath)

        OkHttpUtils.post().url(StaticUtil.AuthenticationUrl).addParams("uid", StaticUtil.uid)
                .addParams("realName", realName).addParams("idCard", idCard).addParams("phone", phone)
                .addFile("file1", justFile.name, justFile).addFile("file2", backFile.name, backFile)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            ToastUtil.showToast("申请已提交，等待审核")
                            context.finish()
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })

    }


}