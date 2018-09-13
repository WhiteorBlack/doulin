package com.lixin.amuseadjacent.app.ui.service.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.dialog.CouponDialog
import com.lixin.amuseadjacent.app.ui.service.model.CouponModel
import com.lixin.amuseadjacent.app.ui.service.model.ReceiveCouponModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * Created by Slingge on 2018/9/13
 */
object Coupon_3132 {

    //获取优惠券
    fun getCoupon() {
        val json = "{\"cmd\":\"getSecuritiesList\",\"uid\":\"" + StaticUtil.uid + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(
                object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val model = Gson().fromJson(response, CouponModel::class.java)
                        EventBus.getDefault().post(model)
                    }
                })
    }


    //领取优惠券
    fun Receive(model: ReceiveCouponModel) {
        abLog.e("领取优惠券",Gson().toJson(model))
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", Gson().toJson(model)).build().execute(
                object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj=JSONObject(response)
                        if(obj.getString("result")=="0"){
                            CouponDialog.dissmis()
                            ToastUtil.showToast("领取优惠券成功")
                        }else{
                            ToastUtil.showToast(obj.getString("resultNote"))
                        }
                    }
                })
    }

}