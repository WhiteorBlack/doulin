package com.lixin.amuseadjacent.app.ui.message.model

import java.io.Serializable

/**
 * Created by Slingge on 2018/9/14
 */
class CommentNewModel {

    var result = ""
    var resultNote = ""
    var totalPage = 1
    var dataList = ArrayList<msgModel>()

    class msgModel : Serializable {
        var messageId = ""
        var messageTitle = ""
        var messageTime = ""
        var userId = ""//评论人id
        var userIcon = ""//评论人头像

        var type = ""// 1动态帮帮 2活动 3话题
        var objid = ""//动态id或帮帮id或活动id 或话题id
        var state = ""//动态是否已经隐藏 0未隐藏 1已隐藏

        var isread = ""///状态 0未读 1已读
    }

}