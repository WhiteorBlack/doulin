package com.lixin.amuseadjacent.app.ui.message.model

import java.io.Serializable

/**
 * Created by Slingge on 2018/9/14
 */
class OfficialNewModel {


    var result = ""
    var resultNote = ""
    var totalPage = 1
    var dataList = ArrayList<msgModel>()

    class msgModel : Serializable {
        var messageId = ""
        var messageTitle = ""
        var messageImage = ""
        var messageTime = ""
        var messageUrl = ""
        var isread = ""///状态 0未读 1已读
    }

}