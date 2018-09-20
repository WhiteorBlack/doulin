package com.lixin.amuseadjacent.app.ui.service.model

/**
 * 官方店铺详情
 * Created by Slingge on 2018/9/19
 */
class OfficialShopDetailsModel {


    var result = ""
    var resultNote = ""
    var totalPage = 1
    var dataList = java.util.ArrayList<dataModel>()
    var `object` = objectModel()

    class objectModel {
        var shopName = ""
        var shopTime = ""
        var shopPhone = ""
        var shopStar = ""
    }

    class dataModel {
        var commentId = ""//评论id
        var commentUid = ""//评论人id
        var commentIcon = ""//评论人头像
        var commentName = ""//评论人昵称

        var commentContent = ""//评论内容
        var commentTime = ""//评论时间
        var commentStar = ""//评分
    }


}