package com.lixin.amuseadjacent.app.ui.message.model

import java.io.Serializable

/**
 * 消息列表
 * Created by Slingge on 2018/9/14
 */
class MsgListModel {


    var result = ""
    var resultNote = ""
    var communityId = ""
    var communityName = ""

    var dataList = ArrayList<msgModel>()

    class msgModel : Serializable {
        var messageId = ""
        var messageTitle = ""
        var messageTime = ""
        var messagenum = ""
        var type = ""//0系统消息 1订单信息 2评论信息
    }

}