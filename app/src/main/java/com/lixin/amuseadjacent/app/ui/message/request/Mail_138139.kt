package com.lixin.amuseadjacent.app.ui.message.request

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lixin.amuseadjacent.app.ui.message.model.MailModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 0好友 1关注 2粉丝
 * Created by Slingge on 2018/9/14
 */
object Mail_138139 {

    interface FollowCallBack{
        fun follow()
    }

    interface  MailListCallBack{
        fun mailList(model:MailModel)
    }


    /**
     * @param type 0好友 1关注 2粉丝
     * @param content 搜索内容(昵称)
     * @param type
     * */
    fun mail(type: String, content: String, nowPage: Int,mailListCallBack: MailListCallBack) {
        val json = "{\"cmd\":\"myAttention\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type +
                "\",\"content\":\"" + content + "\",\"nowPage\":\"" + nowPage + "\",\"pageCount\":\"" + "20" + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, MailModel::class.java)
                if (model.result == "0") {
                    mailListCallBack.mailList(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    //关注、取消关注好友
    fun follow(auid:String,followCallBack: FollowCallBack) {
        val json = "{\"cmd\":\"userAttention\",\"uid\":\"" + StaticUtil.uid + "\",\"auid\":\"" + auid + "\"}"
        abLog.e("活动关注好友",json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
               val obj=JSONObject(response)
                if(obj.getString("result")=="0"){
                    followCallBack.follow()
                }else{
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }

}