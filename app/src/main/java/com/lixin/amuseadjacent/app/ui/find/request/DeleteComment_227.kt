package com.lixin.amuseadjacent.app.ui.find.request

import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.json.JSONObject

/**
 * 评论删除
 * 所有的评论删除都走这个接口
 * Created by Slingge on 2018/9/27
 */
object DeleteComment_227 {

interface DelCommentCallBack{
    fun delComment()
}

    fun del(commentId: String,delCommentCallBack: DelCommentCallBack) {
        val json = "{\"cmd\":\"deleteComment\",\"uid\":\"" + StaticUtil.uid + "\",\"commentId\":\"" + commentId + "\"}"
        OkHttpUtils.post().url(StaticUtil.Url).addParams("json", json).build().execute(object :StrCallback(){
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val obj=JSONObject(response)
                if(obj.getString("result")=="0"){
                    delCommentCallBack.delComment()
                }else{
                    ToastUtil.showToast(obj.getString("resultNote"))
                }
            }
        })
    }


}