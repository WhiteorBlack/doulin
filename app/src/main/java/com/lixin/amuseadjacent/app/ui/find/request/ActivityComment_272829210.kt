package com.lixin.amuseadjacent.app.ui.find.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.find.model.ActivityCommentModel1
import com.lixin.amuseadjacent.app.ui.find.model.TopicModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 活动/话题评论
 * 不是动态和帮帮
 * Created by Slingge on 2018/9/15
 */
object ActivityComment_272829210 {


    //获取一级评论,0活动 1话题
    fun getComment1(type: String, activityId: String, nowPage: Int) {
        val json = "{\"cmd\":\"activityDetailComment\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type +
                "\",\"activityId\":\"" + activityId + "\",\"nowPage\":\"" + nowPage + "\",\"pageCount\":\"" + 15 + "\"}"
        abLog.e("获取评论", json)
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

    //获取一级评论,0活动 1话题
    fun getComment2(type: String, activityId: String, nowPage: Int, firstCommentCallBack: DynaComment_133134.FirstCommentCallBack) {
        val json = "{\"cmd\":\"activityDetailComment\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type +
                "\",\"activityId\":\"" + activityId + "\",\"nowPage\":\"" + nowPage + "\",\"pageCount\":\"" + 15 + "\"}"
        abLog.e("获取评论", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, ActivityCommentModel1::class.java)
                if (model.result == "0") {
                    if (firstCommentCallBack != null) {
                        firstCommentCallBack.firstComment(model)
                    } else {
                        EventBus.getDefault().post(model)
                    }
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

    //获取二级评论,type0活动 1话题
    fun getComment1Second(type: String, activityId: String, commentId: String) {
        val json = "{\"cmd\":\"activityDetailCommentSecond\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type +
                "\",\"activityId\":\"" + activityId + "\",\"commentId\":\"" + commentId + "\"}"
        abLog.e("获取评论", json)
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


    interface CommentCallBack {
        fun commemt(commentId: String)
    }


    /**
     * @param type 0活动 1话题
     *  @param commentId 一级评论id(传空就是赞活动，不为空就是赞该一级评论)
     * */
    fun zan(type: String, activityId: String, commentId: String, zanCallback: Find_26.ZanCallback) {
        val json = "{\"cmd\":\"activityZan\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type +
                "\",\"activityId\":\"" + activityId + "\",\"commentId\":\"" + commentId + "\"}"

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
     * @param type 0活动 1话题
     *  @param commentId 一级评论id(传空就是赞活动，不为空就是赞该一级评论)
     *  @param content 评论
     * */
    fun comment(type: String, activityId: String, commentId: String, content: String, commentCallBack: ActivityComment_272829210.CommentCallBack) {
        val json = "{\"cmd\":\"activityComment\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type +
                "\",\"activityId\":\"" + activityId + "\",\"commentId\":\"" + commentId + "\",\"content\":\"" + content + "\"}"

        abLog.e("活动评论.................", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    commentCallBack.commemt(obj.getString("object"))
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


    //话题详情
    fun getthemedetail(themeId: String) {
        val json = "{\"cmd\":\"getthemedetail\",\"uid\":\"" + StaticUtil.uid + "\",\"themeId\":\"" + themeId + "\"}"
        abLog.e("获取话题详情", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, TopicModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model.`object`)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


}