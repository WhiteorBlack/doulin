package com.lixin.amuseadjacent.app.ui.mine.model

import java.io.Serializable

/**
 * Created by Slingge on 2018/9/6
 */
class UserInfoModel :Serializable {

    var result = ""
    var resultNote = ""
    var communityId = ""//社区id
    var icon = ""//头像
    var nickname = ""//昵称
    var phone = ""//手机号
    var effectNum = ""//影响力值
    var dynamicNum = ""//动态数量
    var attenNum = ""//关注数量
    var fansNum = ""//粉丝数
    var inviteCode = ""//邀请码
    var authentication = ""//认证状态 0未认证 1认证审核中 2认证成功 3审核拒绝
    var balance = ""
    var shareurl = ""//分享链接
}