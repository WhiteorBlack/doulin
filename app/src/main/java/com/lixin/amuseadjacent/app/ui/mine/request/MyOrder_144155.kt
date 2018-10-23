package com.lixin.amuseadjacent.app.ui.mine.request

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraDevice
import android.os.Bundle
import com.google.gson.Gson
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.mine.model.MyOrderModel
import com.lixin.amuseadjacent.app.ui.mine.model.OrderDetailsModel
import com.lixin.amuseadjacent.app.ui.service.activity.ShopCarActivity
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.util.ArrayList

/**
 * Created by Slingge on 2018/9/21
 */
object MyOrder_144155 {

    interface OrderEditCallBack {
        fun cancel()
    }


    //我的订单列表
    fun MyOrderList(nowPage: Int) {
        val json = "{\"cmd\":\"getOrderInfo\",\"uid\":\"" + StaticUtil.uid +
                "\",\"nowPage\":\"" + nowPage + "\",\"pageCount\":\"" + "15" + "\"}"
        abLog.e("我的订单.................", json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, MyOrderModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }

    //订单详情
    fun orderDetails(orderNum: String) {
        val json = "{\"cmd\":\"orderDetailInfo\",\"uid\":\"" + StaticUtil.uid +
                "\",\"orderNum\":\"" + orderNum + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model = Gson().fromJson(response, OrderDetailsModel::class.java)
                if (model.result == "0") {
                    EventBus.getDefault().post(model)
                } else {
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


    //    评价订单
    fun evaluateOrder(context: Activity, orderNum: String, content: String, star: String, position: Int) {
        val json = "{\"cmd\":\"evaluateOrder\",\"uid\":\"" + StaticUtil.uid +
                "\",\"orderNum\":\"" + orderNum + "\",\"content\":\"" + content + "\",\"star\":\"" + star + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    ToastUtil.showToast("评价成功")
                    val intent = Intent()
                    intent.putExtra("position", position)
                    context.setResult(0, intent)
                    context.finish()
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


    //再来一单
    fun againOrder(context: Context, orderNum: String,type:Int,orderIdList:ArrayList<MyOrderModel.orderModel>) {
        val json = "{\"cmd\":\"oneMoreOrder\",\"uid\":\"" + StaticUtil.uid +
                "\",\"orderNum\":\"" + orderNum + "\"}"
        abLog.e("再来一单",json)
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    val bundle=Bundle()
                    bundle.putInt("type",type)
                    bundle.putSerializable("list",orderIdList)
                    MyApplication.openActivity(context, ShopCarActivity::class.java,bundle)
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }

    //取消订单
    fun CancelOrder(context: Activity?, orderNum: String, position: Int, cancelOrderCallBack: OrderEditCallBack?) {
        val json = "{\"cmd\":\"deleteOrder\",\"uid\":\"" + StaticUtil.uid +
                "\",\"reason\":\"" + "" + "\",\"orderNum\":\"" + orderNum + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    ToastUtil.showToast("取消订单成功")
                    if (context == null) {
                        cancelOrderCallBack!!.cancel()
                    } else {//从详情取消
                        val intent = Intent()
                        intent.putExtra("position", position)
                        context.setResult(0, intent)
                        context.finish()
                    }
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }

    //确认收货
    fun ConfirmOrder(context: Activity?, orderNum: String, position: Int, confirmOrderCallBack: OrderEditCallBack?) {

        val json = "{\"cmd\":\"confirmOrder\",\"uid\":\"" + StaticUtil.uid +
                "\",\"orderNum\":\"" + orderNum + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    ToastUtil.showToast("收货成功")
                    if (context == null) {
                        confirmOrderCallBack!!.cancel()
                    } else {//从详情取消
                        val intent = Intent()
                        intent.putExtra("position", position)
                        context.setResult(0, intent)
                        context.finish()
                    }
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }

    //洗衣洗鞋取件
    fun TakepartLaundry(context: Activity?, orderNum: String, position: Int, confirmOrderCallBack: OrderEditCallBack?) {
        val json = "{\"cmd\":\"pickupOrder\",\"uid\":\"" + StaticUtil.uid +
                "\",\"orderNum\":\"" + orderNum + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    ToastUtil.showToast("已确认取件")
                    if (context == null) {
                        confirmOrderCallBack!!.cancel()
                    } else {//从详情取消
                        val intent = Intent()
                        intent.putExtra("position", position)
                        context.setResult(0, intent)
                        context.finish()
                    }
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


    //送还洗衣洗鞋
    fun ReturnLaundry(context: Activity?, orderNum: String, position: Int, confirmOrderCallBack: OrderEditCallBack?) {
        val json = "{\"cmd\":\"returnOrder\",\"uid\":\"" + StaticUtil.uid +
                "\",\"reason\":\"" + "" + "\",\"orderNum\":\"" + orderNum + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object : StrCallback() {
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj = JSONObject(response)
                if (obj.getString("result") == "0") {
                    ToastUtil.showToast("开始送还")
                    if (context == null) {
                        confirmOrderCallBack!!.cancel()
                    } else {//从详情取消
                        val intent = Intent()
                        intent.putExtra("position", position)
                        context.setResult(0, intent)
                        context.finish()
                    }
                } else {
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


    //删除订单
    fun delOrder(orderNum:String,orderEditCallBack:OrderEditCallBack){
        val json = "{\"cmd\":\"realdeleteorder\",\"uid\":\"" + StaticUtil.uid +
              "\",\"orderNum\":\"" + orderNum + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json",json).build().execute(object :StrCallback(){
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj=JSONObject(response)
                if(obj.getString("result")=="0"){
                    orderEditCallBack.cancel()
                }else{
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }

}