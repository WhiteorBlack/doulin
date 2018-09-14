package com.lixin.amuseadjacent.app.ui.message.model

import java.io.Serializable

/**
 * 订单消息
 * Created by Slingge on 2018/9/14
 */
class OrderlNewModel {


    var result = ""
    var resultNote = ""
    var totalPage = 1
    var dataList = ArrayList<msgModel>()

    class msgModel : Serializable {
        var messageId = ""
        var messageTitle = ""
        var messageTime = ""
        var orderNum = ""
        var isread = ""///状态 0未读 1已读
    }

}