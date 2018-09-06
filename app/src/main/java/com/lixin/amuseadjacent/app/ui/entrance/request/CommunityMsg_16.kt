package com.lixin.amuseadjacent.app.ui.entrance.request

import android.app.Activity
import android.graphics.Bitmap
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.MainActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.entrance.model.UnityModel
import com.lixin.amuseadjacent.app.util.AppManager
import com.lixin.amuseadjacent.app.util.ImageFileUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
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
    fun communitMsg(context: Activity, nickname: String, icon: Bitmap, sex: Int, birthday: String, model: UnityModel.unitModel) {
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