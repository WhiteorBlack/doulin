package com.lixin.amuseadjacent.app.ui.find.model

import java.io.Serializable

/**
 * 达人经历
 * Created by Slingge on 2018/9/17
 */
class TalenExperienceModel : Serializable {

    var result = ""
    var resultNote = ""
    var type = ""//0技能类 1职业类 2商业类

    var realname = ""
    var userDesc = ""//达人简介
    var phone = ""//达人电话

    var idcard = ""
    var userlabel = ""
    var isAttention = ""

    var dataList = ArrayList<dataModel>()

    class dataModel : Serializable {
        var experienceId = ""
        var startTime = ""
        var endTime = ""
        var title = ""

        var content = ""
        var userDesc = ""
        var imgurl = ArrayList<String>()// 0未关注 1已关注
    }


}