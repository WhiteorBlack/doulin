package com.lixin.amuseadjacent.app.ui.find.request

import android.app.Activity
import android.content.Intent
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.find.model.EventDetailsModel
import com.lixin.amuseadjacent.app.ui.find.model.EventModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.io.File

/**
 * 活动
 * Created by Slingge on 2018/9/17
 */
object Event_221222223224 {


    interface CollectCallBack {
        fun collect()
    }


    fun EventList(nowPage: Int) {


        val json = "{\"cmd\":\"findActivityList\",\"uid\":\"" + StaticUtil.uid + "\",\"communityId\":\"" + StaticUtil.communityId +
                "\",\"nowPage\":\"" + nowPage + "\",\"pageCount\":\"" + "10" + "\"}"

        abLog.e("活动.................", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, EventModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    //活动详情
    fun EventDetails(activityId: String) {

        val json = "{\"cmd\":\"findActivityDetail\",\"uid\":\"" + StaticUtil.uid +
                "\",\"activityId\":\"" + activityId + "\"}"

        abLog.e("活动详情.................", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, EventDetailsModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    //活动报名
    fun EventSgin(context: Activity, activityId: String, name: String, phone: String, count: String) {
        val json = "{\"cmd\":\"signupActivity\",\"uid\":\"" + StaticUtil.uid +
                "\",\"activityId\":\"" + activityId + "\",\"name\":\"" + name +
                "\",\"phone\":\"" + phone + "\",\"count\":\"" + count + "\"}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    ToastUtil.showToast("报名成功")
                    val intent = Intent()
                    intent.putExtra("0", "0")
                    context.setResult(0, intent)
                    context.finish()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


    /**
     * 收藏、取消收藏
     * @param type 0帮帮 1活动
     * @param objid /对应id
     */
    fun EventCollect(type: String, objid: String, collectCallBack: CollectCallBack) {
        val json = "{\"cmd\":\"addCollect\",\"uid\":\"" + StaticUtil.uid +
                "\",\"type\":\"" + type + "\",\"objid\":\"" + objid + "\"}"
        abLog.e("收藏。。。。。。。", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    collectCallBack.collect()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


    //创建活动
    fun  EventEstablish(context: Activity, activityName: String, activityStarttime: String, activityEndtime: String, activitySignEndtime: String,
                       activityAddress: String, activityPhone: String, activityAllnum: String, activityMoney: String,
                       activityDesc: String, imageList: ArrayList<LocalMedia>) {

        val fileList = ArrayList<File>()
        for (i in 0 until imageList.size - 1) {//图片路径集合
            val file = File(imageList[i].path)//保存压缩
            fileList.add(file)
        }
        OkHttpUtils.post().url(StaticUtil.EventEstablish).addParams("uid", StaticUtil.uid)
                .addParams("activityName", activityName).addParams("activityStarttime", activityStarttime)
                .addParams("activityEndtime", activityEndtime).addParams("activitySignEndtime", activitySignEndtime)
                .addParams("activityAddress", activityAddress)
                .addParams("activityPhone", activityPhone).addParams("activityAllnum", activityAllnum)
                .addParams("activityDesc", activityDesc).addParams("activityMoney", activityMoney)
                .files("file", fileList).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            val intent = Intent()
                            intent.putExtra("0", "0")
                            context.setResult(0, intent)
                            context.finish()
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })
    }





}