package com.lixin.amuseadjacent.app.ui.find.model

import java.io.Serializable

/**
 * 话题详情
 * Created by Slingge on 2018/10/12
 */
class TopicModel :Serializable{

    var result = ""
    var resultNote = ""

    var `object`=objectModel()

    class objectModel :Serializable {
        var themeId = ""
        var themeTitle = ""
        var themeDetailUrl = ""
        var zanNum = ""

        var commentNum = ""
        var isZan = ""
    }

}