package com.lixin.amuseadjacent.app.util

import android.content.Context
import cn.jpush.android.api.JPushInterface

/**
 * Created by Slingge on 2018/9/5
 */
object StaticUtil {

    val Url = "http://39.107.106.122/wisdom/api/service?"//接口地址

    var uid=""// Id
    var phone=""//
    var CcommunityId=""//社区Id
    var nickName=""//
    var age=""//
    var headerUrl=""//头像

    fun getJpushToken(context: Context): String {
        return JPushInterface.getRegistrationID(context)
    }

}