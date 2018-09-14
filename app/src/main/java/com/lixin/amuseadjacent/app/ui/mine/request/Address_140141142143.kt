package com.lixin.amuseadjacent.app.ui.mine.request

import android.app.Activity
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.mine.model.AddressModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 地址
 * 地址列表，修改地址，添加地址，删除地址
 * Created by Slingge on 2018/9/14
 */
object Address_140141142143 {

    interface DelAddressCallback {
        fun del()
    }


    fun addressList(nowPage: Int) {
        val json = "{\"cmd\":\"getUserAddressList\",\"uid\":\"" + StaticUtil.uid + "\",\"nowPage\":\"" + nowPage + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, AddressModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    /**
     * @param flag 0新增地址，1修改地址
     * */
    fun editAddress(context: Activity, flag: Int, addressId: String, username: String, userPhone: String
                    , city: String, address: String, isdefault: String) {
        val json: String
        if (flag == 0) {//新增地址
            json = "{\"cmd\":\"addUserAddress\",\"uid\":\"" + StaticUtil.uid + "\",\"username\":\"" + username +
                    "\",\"userPhone\":\"" + userPhone + "\",\"city\":\"" + city + "\",\"address\":\"" + address +
                    "\",\"isdefault\":\"" + isdefault + "\"}"
        } else {
            json = "{\"cmd\":\"editUserAddress\",\"uid\":\"" + StaticUtil.uid + "\",\"addressId\":\"" + addressId +
                    "\",\"username\":\"" + username + "\",\"userPhone\":\"" + userPhone +
                    "\",\"city\":\"" + city + "\",\"address\":\"" + address + "\",\"isdefault\":\"" + isdefault + "\"}"
        }

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    if (flag == 0) {
                        ToastUtil.showToast("新增地址成功")
                    } else {
                        ToastUtil.showToast("修改地址成功")
                    }
                    context.finish()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })

    }


    //删除地址
    fun delAddress(addressId: String, delAddressCallback: DelAddressCallback) {
        val json = "{\"cmd\":\"delUserAddress\",\"uid\":\"" + StaticUtil.uid + "\",\"addressId\":\"" + addressId + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    delAddressCallback.del()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })

    }


}