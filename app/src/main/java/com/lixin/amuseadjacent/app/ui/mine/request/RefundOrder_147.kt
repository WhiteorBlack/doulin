package com.lixin.amuseadjacent.app.ui.mine.request

import android.app.Activity
import android.content.Intent
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject
import java.io.File

/**
 * 订单退款
 * Created by Slingge on 2018/9/21
 */
object RefundOrder_147 {


    fun refund(context: Activity, orderNum: String, reason: String, imageList: ArrayList<LocalMedia>, position: Int
    ) {

        val fileList = ArrayList<File>()

        for (i in 0 until imageList.size - 1) {
            val file = File(imageList[i].path)
            fileList.add(file)
        }

        OkHttpUtils.post().url(StaticUtil.RefundOrder).addParams("uid", StaticUtil.uid)
                .addParams("orderNum", orderNum).addParams("reason", reason)
                .files("file", fileList).build().execute(object : StrCallback() {
                    override fun onResponse(response: String, id: Int) {
                        super.onResponse(response, id)
                        val obj = JSONObject(response)
                        if (obj.getString("result") == "0") {
                            ToastUtil.showToast("申请退款成功")
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


}