package com.lixin.amuseadjacent.app.util

import android.content.Context
import cn.jpush.android.api.JPushInterface

/**
 * Created by Slingge on 2018/9/5
 */
object StaticUtil {

    val Url = "http://39.107.106.122/wisdom/api/service?"//接口地址
//    val Url = "http://192.168.3.142:8080/wisdom/api/service?"//本地地址

    val AlbumsUrl = "http://39.107.106.122/wisdom/api/addAlbums"//上传相册地址
    val AuthenticationUrl = "http://39.107.106.122/wisdom/api/userAuthentication"//实名认证地址
    val ReleaseDynamicBang = "http://39.107.106.122/wisdom/api/addDynamic"//发布动态帮帮地址
    //val ReleaseDynamicBang="http://192.168.3.142:8080/wisdom/api/addDynamic"//发布动态帮帮地址
    val TalenExperienceImage = "http://39.107.106.122/wisdom/api/addimgs"//上传达人经历
    val EventEstablish = "http://39.107.106.122/wisdom/api/AddActivity"//创建活动

    var uid = ""// Id
    var phone = ""//
    var communityId = ""//社区Id
    var nickName = ""//昵称
    var age = ""//
    var sex = ""

    var rytoken=""//用户网易云token

    var headerUrl = ""//头像

    var effectNum = ""//影响力值
    var constellation = ""//星座

    var communityName = ""//社区名称
    var unitName = ""//社区单元名称
    var doorNumber = ""//门牌号

    var Beecloud_Appid = "03feacbf-5f79-4f2c-83de-92910cc6e7b4"
    var Beecloud_AppSecret = "78cb9f6f-2021-4e56-b66e-f9c6cbb31e0b"
    var Weixin_Appid = "78cb9f6f-2021-4e56-b66e-f9c6cbb31e0b"

    var balance = ""//余额

    fun getJpushToken(context: Context): String {
        return JPushInterface.getRegistrationID(context)
    }

}