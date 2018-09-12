package com.lixin.amuseadjacent.app.ui.mine.request

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.mine.model.BankModel
import com.lixin.amuseadjacent.app.ui.mine.model.MyBankModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 银行卡
 * Created by Slingge on 2018/9/12
 */
object Bank_155156157158164 {

    interface DelbankCallack {
        fun delBank()
    }

    fun myBankList() {
        val json = "{\"cmd\":\"mybankcard\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + "0" + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val mode = Gson().fromJson(response, MyBankModel::class.java)
                if (mode.result == "0") {
                    EventBus.getDefault().post(mode)
                } else {
                    ToastUtil.showToast(mode.resultNote)
                }
            }
        })
    }

    //获取银行
    fun getBank() {
        val json = "{\"cmd\":\"mybanklist\"" + "}"

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val mode = Gson().fromJson(response, BankModel::class.java)
                if (mode.result == "0") {
                    EventBus.getDefault().post(mode)
                } else {
                    ToastUtil.showToast(mode.resultNote)
                }
            }
        })
    }


    //编辑银行卡,type,0添加，1编辑
    fun addBank(context: Activity, type: Int, cardName: String, cardNum: String, cardUsername: String) {
        var json = ""
        if (type == 0) {
            json = "{\"cmd\":\"addbankcard\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + "0" +
                    "\",\"cardName\":\"" + cardName + "\",\"cardNum\":\"" + cardNum + "\",\"cardUsername\":\"" + cardUsername + "\"}"
        } else {

        }
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    if (type == 0) {//添加银行卡
                        val model = MyBankModel.detailsModel()
                        model.cardName = cardName
                        model.cardNum = cardNum
                        val intent = Intent()
                        val bundle = Bundle()
                        bundle.putSerializable("model", model)
                        intent.putExtras(bundle)
                        context.setResult(0, intent)
                        context.finish()
                    }
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })

    }


    //删除银行卡
    fun delBank(cardId: String,  delbankCallack: DelbankCallack) {
        val json = "{\"cmd\":\"deletebankcard\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + "0" + "\",\"cardId\":\"" + cardId + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    delbankCallack.delBank()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


}