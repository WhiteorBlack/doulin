package com.lixin.amuseadjacent.app.ui.message.request

import com.google.gson.Gson
import com.lixin.amuseadjacent.app.ui.entrance.model.CommunityModel
import com.lixin.amuseadjacent.app.ui.message.model.CommunityUserModel
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.huaihuatransit.app.util.StrCallback
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.zhy.http.okhttp.OkHttpUtils
import org.greenrobot.eventbus.EventBus

/**
 * 社区用户
 * Created by Slingge on 2018/9/14
 */
 object CommunityUser_20{


    fun user(sex:String,content: String,nowPage:Int){
        val json = "{\"cmd\":\"getCommunityUser\",\"uid\":\"" + StaticUtil.uid + "\",\"communityId\":\"" + StaticUtil.CcommunityId +
                "\",\"sex\":\"" + sex + "\",\"content\":\"" + content + "\",\"pageCount\":\"" + "20" +"\",\"nowPage\":\"" + nowPage + "\"}"

        abLog.e("获取用户.................",json)

        OkHttpUtils.post().url(StaticUtil.Url).addParams("json",json).build().execute(object :StrCallback(){
            override fun onResponse(response: String, id: Int) {
                super.onResponse(response, id)
                val model=Gson().fromJson(response, CommunityUserModel::class.java)
                if(model.result=="0"){
                    EventBus.getDefault().post(model)
                }else{
                    ToastUtil.showToast(model.resultNote)
                }
            }
        })
    }


}