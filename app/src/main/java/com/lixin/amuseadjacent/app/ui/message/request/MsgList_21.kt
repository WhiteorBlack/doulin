package com.lixin.amuseadjacent.app.ui.message.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.message.model.CommentNewModel
import com.lixin.amuseadjacent.app.ui.message.model.MsgListModel
import com.lixin.amuseadjacent.app.ui.message.model.OfficialNewModel
import com.lixin.amuseadjacent.app.ui.message.model.OrderlNewModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * Created by Slingge on 2018/9/14
 */
object MsgList_21 {

    interface DelMsgCallBack {
        fun delMsg()
    }

    //消息列表
    fun msgList() {
        val json = "{\"cmd\":\"myMessageList\",\"uid\":\"" + StaticUtil.uid + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, MsgListModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    /**
     * 删除消息
     *  @param type 0系统消息 1订单信息 2评论信息
     */
    fun delMsg(messageId: String, type: String, delMsgCallBack: DelMsgCallBack) {
        val json = "{\"cmd\":\"deleteMyMessage\",\"uid\":\"" + StaticUtil.uid + "\",\"messageId\":\"" + messageId +
                "\",\"type\":\"" + type + "\"}"
        abLog.e("删除消息.......", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    delMsgCallBack.delMsg()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


    //官方消息
    fun OfficialNews(nowPage: Int) {
        val json = "{\"cmd\":\"sysMessageList\",\"uid\":\"" + StaticUtil.uid + "\",\"nowPage\":\"" + nowPage +
                "\",\"pageCount\":\"" + "20" + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, OfficialNewModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

    //订单消息
    fun orderNew(nowPage: Int) {
        val json = "{\"cmd\":\"orderMessageList\",\"uid\":\"" + StaticUtil.uid + "\",\"nowPage\":\"" + nowPage +
                "\",\"pageCount\":\"" + "20" + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, OrderlNewModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

    //评论消息
    fun commentNew(nowPage: Int) {
        val json = "{\"cmd\":\"commMessageList\",\"uid\":\"" + StaticUtil.uid + "\",\"nowPage\":\"" + nowPage +
                "\",\"pageCount\":\"" + "20" + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, CommentNewModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

    //点赞消息
    fun zanNew(nowPage: Int) {
        val json = "{\"cmd\":\"zanMessageList\",\"uid\":\"" + StaticUtil.uid + "\",\"nowPage\":\"" + nowPage +
                "\",\"pageCount\":\"" + "20" + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, CommentNewModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

}