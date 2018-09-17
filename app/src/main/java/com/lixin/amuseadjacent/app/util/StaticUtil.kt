package com.lixin.amuseadjacent.app.util

import android.content.Context
import cn.jpush.android.api.JPushInterface

/**
 * Created by Slingge on 2018/9/5
 */
object StaticUtil {

    val Url = "http://39.107.106.122/wisdom/api/service?"//接口地址

    val AlbumsUrl="http://39.107.106.122/wisdom/api/addAlbums"//上传相册地址
    val AuthenticationUrl="http://39.107.106.122/wisdom/api/userAuthentication"//实名认证地址

    var uid = ""// Id
    var phone = ""//
    var communityId = ""//社区Id
    var nickName = ""//昵称
    var age = ""//
    var sex = ""

    var headerUrl = ""//头像

    var effectNum = ""//影响力值
    var constellation = ""//星座

    var communityName = ""//社区名称
    var unitName = ""//社区单元名称
    var doorNumber = ""//门牌号

    var balance =""//余额

    fun getJpushToken(context: Context): String {
        return JPushInterface.getRegistrationID(context)
    }

}