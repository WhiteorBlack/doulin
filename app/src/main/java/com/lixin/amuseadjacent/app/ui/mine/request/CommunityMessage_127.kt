package com.lixin.amuseadjacent.app.ui.mine.request

import android.app.Activity
import android.content.Context
import com.lixin.amuseadjacent.app.ui.entrance.model.UnityModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * 更换社区
 * Created by Slingge on 2018/9/13
 */
object CommunityMessage_127 {

    fun community(context: Activity,content:String,model: UnityModel.unitModel) {
        val json = "{\"cmd\":\"editCommunityMessage\",\"uid\":\"" + StaticUtil.uid + "\",\"communityId\":\"" + model.communityId +
                "\",\"unitId\":\"" + model.unitId + "\",\"doorNumber\":\"" + model.num + "\",\"reason\":\"" + content + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
               val obj=JSONObject(response)
                if(obj.getString("result")=="0"){
                    ToastUtil.showToast("更换社区信息成功")
                    context.finish()
                }else{
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


}