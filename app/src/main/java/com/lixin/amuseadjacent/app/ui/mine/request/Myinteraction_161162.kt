package com.lixin.amuseadjacent.app.ui.mine.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclModel
import com.lixin.amuseadjacent.app.ui.mine.model.InteractionModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 我的互动
 * Created by Slingge on 2018/9/21
 */
object Myinteraction_161162 {


    fun Interaction(nowPage: Int, auid: String) {
        val json = "{\"cmd\":\"getInteractionList\",\"uid\":\"" + StaticUtil.uid + "\",\"auid\":\"" + auid +
                "\",\"nowPage\":\"" + nowPage + "\",\"pageCount\":\"" + "15" + "\"}"

        abLog.e("我的互动.................", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, InteractionModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    interface DelInteractionCallBack{
        fun del()
    }

    /**
     * @param type 0帮帮 1活动 2动态
     * @param objid 帮帮id或活动id
     */
    fun DelInteraction(type: String, objid: String,delInteractionCallBack: DelInteractionCallBack) {
        val json = "{\"cmd\":\"deleteInteraction\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type +
                "\",\"objid\":\"" + objid + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
               val obj=JSONObject(response)
                if(obj.getString("result")=="0"){
                    delInteractionCallBack.del()
                }else{
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })

    }
}