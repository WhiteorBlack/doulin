package com.lixin.amuseadjacent.app.ui.mine.request

import android.content.Context
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.mine.model.UserInfoModel
import com.lixin.amuseadjacent.app.util.SharedPreferencesUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2018/9/6
 */
object UserInfo_19 {


    fun userInfo(context: Context) {

        val json = "{\"cmd\":\"getUserInfo\",\"uid\":\"" + StaticUtil.uid + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, UserInfoModel::class.java)
                if (model.result == "0") {
                    StaticUtil.balance = model.balance
                    StaticUtil.nickName = model.nickname
                    StaticUtil.effectNum = model.effectNum
                    StaticUtil.communityId = model.communityId
                    StaticUtil.headerUrl = model.icon

                    val sp = context.getSharedPreferences(SharedPreferencesUtil.NAME, 0)
                    sp.edit().putString(SharedPreferencesUtil.headerUrl, model.icon).putString(SharedPreferencesUtil.nickName, model.nickname)
                            .commit()

                    EventBus.getDefault().post(StaticUtil.balance)
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

}