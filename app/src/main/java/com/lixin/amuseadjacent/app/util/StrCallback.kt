package com.lxkj.huaihuatransit.app.util


import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call

/**
 * okhttpString信息返回重写
 * Created by Slingge on 2017/2/14 0014.
 */

abstract class StrCallback : StringCallback() {
    override fun onError(call: Call, e: Exception, id: Int) {
        abLog.e2(e.toString())
        ToastUtil.showToast("网络错误")
        ProgressDialog.dissDialog()
    }

    override fun onResponse(response: String, id: Int) {
        ProgressDialog.dissDialog()
        abLog.e2(response)
    }

}
