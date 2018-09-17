package com.lixin.amuseadjacent.app.ui.find.model

/**
 * 一级评论
 * Created by Slingge on 2018/9/15
 */
class CommentModel1 {


    var result = ""
    var resultNote = ""

    var dataList=ArrayList<commModel>()

    class commModel {

        var commentId = ""//评论id
        var commentUid = ""//评论人id
        var commentIcon = ""//评论人头像
        var commentName = ""//评论人昵称
        var commentContent = ""//评论内容
        var commentTime = ""//评论时间

        var zanNum = ""//评论点赞数量
        var secondNum = ""///二级评论数量
    }

}