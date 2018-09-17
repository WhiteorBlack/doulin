package com.lixin.amuseadjacent.app.ui.find.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.find.model.ActivityCommentModel1
import com.lixin.amuseadjacent.app.ui.find.model.DynamiclModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 活动、帮帮，详情、评论、回复、点赞
 * Created by Slingge on 2018/9/15
 */
object DynaComment_133134 {


    //帮帮、动态详情
    fun dynamicDetail(dynamicId: String) {
        val json = "{\"cmd\":\"dynamicDetail\",\"uid\":\"" + StaticUtil.uid + "\",\"dynamicId\":\"" + dynamicId + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, DynamiclModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    //获取二级评论
    fun commentSecond(dynamicId: String, commentId: String) {
        val json = "{\"cmd\":\"dynamicDetailCommentSecond\",\"uid\":\"" + StaticUtil.uid + "\",\"dynamicId\":\"" + dynamicId +
                "\",\"commentId\":\"" + commentId + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, ActivityCommentModel1::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    /**
     *  @param commentId 一级评论id(传空就是赞动态，不为空就是赞该一级评论)
     * */
    fun zan(dynamicId: String, commentId: String, zanCallback: Find_26.ZanCallback) {
        val json = "{\"cmd\":\"dynamicZan\",\"uid\":\"" + StaticUtil.uid +
                "\",\"dynamicId\":\"" + dynamicId + "\",\"commentId\":\"" + commentId + "\"}"

        abLog.e("首页.................", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    zanCallback.zan()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


    /**
     * 回复
     *  @param commentId 一级评论id(传空就是评论动态，不为空就是评论该一级评论)
     *  @param content 评论
     * */
    fun comment(dynamicId: String, commentId: String, content: String, commentCallBack: ActivityComment_272829210.CommentCallBack) {
        val json = "{\"cmd\":\"dynamicComment\",\"uid\":\"" + StaticUtil.uid +
                "\",\"dynamicId\":\"" + dynamicId + "\",\"commentId\":\"" + commentId + "\",\"content\":\"" + content + "\"}"

        abLog.e("评论.................", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    ToastUtil.showToast("评论成功")
                    commentCallBack.commemt()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


}