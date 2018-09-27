package com.lixin.amuseadjacent.app.ui.mine.request

import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.zhy.http.okhttp.OkHttpUtils

/**
 * 设置备注
 * Created by Slingge on 2018/9/27
 */
object EditeNote_167 {


    fun note(auid: String, note: String) {
        val json = "{\"cmd\":\"addremarks\",\"uid\":\"" + StaticUtil.uid +
                "\",\"auid\":\"" + auid + "\",\"remarks\":\"" + note + "\"}"
        abLog.e("设置备注",json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
        })
    }

}