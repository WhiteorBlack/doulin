package com.lixin.amuseadjacent.app.ui.entrance.model

/**
 * 社区
 * Created by Slingge on 2018/9/5
 */
class UnityModel {

    var result = ""
    var resultNote = ""
    var dataList = ArrayList<unitModel>()


    class unitModel() {
        var unitId = ""//社区单元id
        var unitName = ""

        var communityId = ""
        var communityName = ""

        var num=""//门牌号
    }
}