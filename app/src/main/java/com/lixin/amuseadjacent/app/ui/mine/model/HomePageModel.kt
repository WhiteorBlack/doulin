package com.lixin.amuseadjacent.app.ui.mine.model

import java.io.Serializable
import java.util.ArrayList

/**
 * Created by Slingge on 2018/9/6
 */
class HomePageModel : Serializable {

    var result = ""
    var resultNote = ""
    var icon = ""
    var nickname = ""
    var sex = ""
    var age = ""
    var effectNum = ""//影响力
    var constellation = ""//星座
    var communityId = ""//社区单元id
    var communityName = ""//社区名称
    var unitId = ""//社区单元id
    var unitName = ""//社区单元名称
    var doorNumber = ""//门牌号

    var remarks=""//备注

    var autograph = ""//个人签名
    var inviteCode = ""//邀请码
    var occupation = ""//职业

    var albumList = ArrayList<albumModel>()

    var sportList = ArrayList<String>()
    var musicList = ArrayList<String>()
    var foodsList = ArrayList<String>()
    var movieList = ArrayList<String>()
    var booksList = ArrayList<String>()
    var otherList = ArrayList<String>()


    class albumModel : Serializable {
        var imgId = ""//
        var imgUrl = ""//
    }
}
