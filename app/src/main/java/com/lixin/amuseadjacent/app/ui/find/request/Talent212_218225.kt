package com.lixin.amuseadjacent.app.ui.find.request

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.model.AddTalenExperienceModel
import com.lixin.amuseadjacent.app.ui.find.model.TalenExperienceModel
import com.lixin.amuseadjacent.app.ui.find.model.TalentLableModel
import com.lixin.amuseadjacent.app.ui.find.model.TalentModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * 2.12到2.18
 * Created by Slingge on 2018/9/17
 */
object Talent212_218225 {


    interface ApplyTalenCallBack {
        fun Apply()
    }

    interface DelTalenExperienceCallBack {
        fun del()
    }


    //0技能类 1职业类 2商业类
    fun talent(type: Int, nowPage: Int) {
        val json = "{\"cmd\":\"getCommunityMan\",\"uid\":\"" + StaticUtil.uid + "\",\"communityId\":\"" + StaticUtil.communityId +
                "\",\"type\":\"" + type + "\",\"nowPage\":\"" + nowPage + "\",\"pageCount\":\"" + "15" + "\"}"

        abLog.e("达人.................", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, TalentModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    //达人标签
    fun talentLable() {
        val json = "{\"cmd\":\"getCommunityManLabel\"" + "}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, TalentLableModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    /**
     * 达人申请
     * @param type 0技能类 1职业类 2商业类
     */
    fun applyTalen(type: String, realname: String, phone: String, userDesc: String, labelName: String
                   , applyTalenCallBack: ApplyTalenCallBack) {
        val json = "{\"cmd\":\"applyCommunityMan\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type +
                "\",\"realname\":\"" + realname + "\",\"phone\":\"" + phone + "\",\"userDesc\":\"" + userDesc +
                "\",\"userlabel\":\"" + labelName + "\"}"

        abLog.e("达人申请.........", json)

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    ToastUtil.showToast("达人认证成功")
                    applyTalenCallBack.Apply()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }

    //添加达人经历图片
    fun AddTalenExperienceImage(context: Activity, imageList: ArrayList<LocalMedia>, model: AddTalenExperienceModel) {
        val listfile = arrayListOf<File>() //map是无序集合尽量用list
        abLog.e("达人经历图片", Gson().toJson(imageList))

        var b=false
        for (i in 0 until imageList.size - 1) {//图片路径集合
            if (!imageList[i].path.contains("http://")) {
                val file = File(imageList[i].path)//保存压缩
                listfile.add(file)
                b=true
            }
        }
        if(!b){//都是网络图片
            EditExperience(context, model, imageList)
            return
        }


        OkHttpUtils.post().url(StaticUtil.TalenExperienceImage).files("file", listfile)
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            val array = JSONArray(obj.getString("object"))
                            for (i in 0 until array.length()) {
                                model.file.add(array[i].toString())
                            }
                            if (TextUtils.isEmpty(model.experienceId)) {//添加经历
                                AddTalenExperience(context, model)
                            } else {
                                EditExperience(context, model, imageList)
                            }
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })

    }

    //添加达人经历
    fun AddTalenExperience(context: Activity, model: AddTalenExperienceModel) {
        ProgressDialog.showDialog(context)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", Gson().toJson(model))
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            ToastUtil.showToast("添加成功")
                            val intent = Intent()
                            intent.putExtra("0", "0")
                            context.setResult(1, intent)
                            context.finish()
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })
    }


    //修改达人经历
    fun EditExperience(context: Activity, model: AddTalenExperienceModel, imageList: ArrayList<LocalMedia>) {
        ProgressDialog.showDialog(context)

        for (i in 0 until imageList.size - 1) {
            if (imageList[i].path.contains("http://")) {
                model.file.add(0, imageList[i].path)
            }
        }

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", Gson().toJson(model))
                .build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            ToastUtil.showToast("修改成功")
                            val intent = Intent()
                            intent.putExtra("model", model)
                            context.setResult(2, intent)
                            context.finish()
                        } else {
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })
    }


    //达人经历， 个人主页和达人证的经历都用
    fun TalenExperience(auid: String) {

        val json = "{\"cmd\":\"getManDetail\",\"uid\":\"" + StaticUtil.uid + "\",\"auid\":\"" + auid + "\"}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, TalenExperienceModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    //删除达人经历 experienceId经历id
    fun DelTalenExperience(experienceId: String, delTalenExperienceCallBack: DelTalenExperienceCallBack) {

        val json = "{\"cmd\":\"deleteExperience\",\"uid\":\"" + StaticUtil.uid + "\",\"experienceId\":\"" + experienceId + "\"}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result").equals("0")) {
                    delTalenExperienceCallBack.del()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


}