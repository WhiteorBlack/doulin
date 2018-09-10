package com.lixin.amuseadjacent.app.ui.mine.request

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lixin.amuseadjacent.app.ui.mine.model.AddLabelModel
import com.lixin.amuseadjacent.app.ui.mine.model.LabelListModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 获取兴趣标签
 * Created by Slingge on 2018/9/10
 */
object LabelList_115116 {


    fun userInfo(type: String) {
        val json = "{\"cmd\":\"getLabelList\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + type + "\"}";
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, LabelListModel::class.java)
                EventBus.getDefault().post(model)
            }
        })
    }

    fun addLabel(context:Activity,type: String, labelList: ArrayList<String>, otherLabelList: ArrayList<String>) {

        val model = AddLabelModel()
        model.type = type
        model.labelList = labelList
        model.otherList = otherLabelList

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", Gson().toJson(model)).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj=JSONObject(response)
                if(obj.getString("result")=="0"){
                    ToastUtil.showToast("保存成功")
                    context.finish()
                }else{
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


}