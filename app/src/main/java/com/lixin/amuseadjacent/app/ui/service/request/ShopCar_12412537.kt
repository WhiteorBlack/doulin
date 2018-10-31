package com.lixin.amuseadjacent.app.ui.service.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.service.model.DelCarModel
import com.lixin.amuseadjacent.app.ui.service.model.ShopCarModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 加入购物车
 * Created by Slingge on 2018/9/20
 */
object ShopCar_12412537 {

    interface AddCarCallback {
        fun addCar()
    }

    interface DelCarCallback {
        fun delCar()
    }

    fun getCar() {
        val json = "{\"cmd\":\"myShopCar\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + "" + "\"}"
        abLog.e("cart",json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, ShopCarModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    //3.7 加入购物车.type0新鲜果蔬 1洗衣洗鞋 2超市便利
    fun addCar(type: String, goodsId: String, count: String, addCarCallback: AddCarCallback) {
        val json = "{\"cmd\":\"addShopCar\",\"uid\":\"" + StaticUtil.uid + "\",\"goodsId\":\"" + goodsId +
                "\",\"count\":\"" + count + "\",\"type\":\"" + type + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    addCarCallback.addCar()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })

    }


    //删除购物车
    fun DelCar(model: DelCarModel, delcall: DelCarCallback) {
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", Gson().toJson(model)).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    delcall.delCar()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }

}
