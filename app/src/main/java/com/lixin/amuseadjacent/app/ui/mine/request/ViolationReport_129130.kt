package com.lixin.amuseadjacent.app.ui.mine.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.mine.model.IrregularitiesModel
import com.lixin.amuseadjacent.app.ui.mine.model.ReportModel
import com.lixin.amuseadjacent.app.ui.mine.model.SginInModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 违规举报
 * Created by Slingge on 2018/9/13
 */
object ViolationReport_129130 {

    //违规
    fun irregularities(nowPage: Int) {
        val json = "{\"cmd\":\"myIrregularities\",\"uid\":\"" + StaticUtil.uid + "\",\"nowPage\":\"" + nowPage + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, IrregularitiesModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })

    }

    //举报
    fun report(nowPage: Int) {
        val json = "{\"cmd\":\"myIrregularities\",\"uid\":\"" + StaticUtil.uid + "\",\"nowPage\":\"" + nowPage + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, ReportModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })

    }


}