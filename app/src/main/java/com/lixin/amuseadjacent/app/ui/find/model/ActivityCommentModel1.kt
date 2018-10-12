package com.lixin.amuseadjacent.app.ui.find.model

import java.io.Serializable

/**
 * 活动话题一级评论
 * Created by Slingge on 2018/9/15
 */
class ActivityCommentModel1 : Serializable {


    var result = ""
    var resultNote = ""

    var totalPage=1

    var dataList = ArrayList<commModel>()

    var `object` = commModel()

    class commModel : Serializable {

        //热门评论
        var commentId = ""//评论id
        var commentUid = ""//评论人id
        var commentIcon = ""//评论人头像
        var commentName = ""//评论人昵称

        var commentContent = ""//评论内容
        var commentTime = ""//评论时间
        var zanNum = ""//评论点赞数量

        var secondNum = ""//二级评论数量
        var isZan = ""//是否已经赞过 0未赞过 1已赞过

    }

}