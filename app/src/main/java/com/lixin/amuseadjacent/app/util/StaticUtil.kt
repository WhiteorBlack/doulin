package com.lixin.amuseadjacent.app.util

import android.content.Context
import android.os.Environment
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
    val TalenExperienceImage = "http://39.107.106.122/wisdom/api/addimgs"//上传达人经历
    val EventEstablish = "http://39.107.106.122/wisdom/api/AddActivity"//创建活动
    val RefundOrder = "http://39.107.106.122/wisdom/api/refundOrder"//订单退款

    val WebViewUrl="http://39.107.106.122/wisdom/api/about/displayContent?"//webview

    var uid = ""// Id
    var phone = ""//
    var communityId = ""//社区Id
    var communityName = ""//社区名称
    var unitName = ""//社区单元名称
    var doorNumber = ""//门牌号

    var nickName = ""//昵称
    var age = ""//
    var sex = ""

    var rytoken = ""//用户网易云token

    var headerUrl = ""//头像

    var effectNum = ""//影响力值
    var constellation = ""//星座

    var Beecloud_Appid = "03feacbf-5f79-4f2c-83de-92910cc6e7b4"
    var Beecloud_AppSecret = "78cb9f6f-2021-4e56-b66e-f9c6cbb31e0b"
    var Weixin_Appid = "wxe73e5c8db33225ac"

    var balance = ""//余额

    var RefundResult = 2//订单状态退款操作
    var EvaluateResult = 3//订单状态评价操作
    var OrderDetailsResult = 3//订单详情中的操作

    var shareurl = ""//分享链接
    var inviteCode = ""//邀请码

    val DownImagePath= Environment.getExternalStorageDirectory().path +"/Pictures/"//保存图片的路径

    fun getJpushToken(context: Context): String {
        return JPushInterface.getRegistrationID(context)
    }

}