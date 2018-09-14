package com.lixin.amuseadjacent.app.ui.message.model

import java.io.Serializable

/**
 * Created by Slingge on 2018/9/14
 */
class CommunityUserModel{


    var result = ""
    var resultNote = ""
    var totalPage = 1
    var allnum = ""//总人数
    var dataList = ArrayList<userModel>()

    class userModel : Serializable {
        var userId = ""
        var userName = ""

        var userIcon = ""
        var userSex = ""

        var userAge = ""//用户年龄
        var userAutograph = ""//用户签名

        var effectNum = ""//影响力值
        var constellation = ""//星座
        var isAttention = ""//0未关注 1已关注
    }


}