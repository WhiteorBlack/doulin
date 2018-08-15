package com.lixin.amuseadjacent.app.ui.message.model

import java.util.ArrayList

/**
 * Created by Slingge on 2018/8/15
 */
class MyMsgListModel {
    var result = ""
    var resultNote = ""
    var dataList=ArrayList<dataModel>()

    class dataModel {
        //返回各自最新的一条(新用户是空)
        var messageId = ""//消息id
        var messageTitle = ""//消息标题

        var messageTime = ""//消息时间
        var type = ""//0系统消息 1订单信息 2评论信息

    }


}