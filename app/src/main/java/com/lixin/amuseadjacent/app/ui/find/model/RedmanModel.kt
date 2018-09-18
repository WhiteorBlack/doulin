package com.lixin.amuseadjacent.app.ui.find.model

/**
 * Created by Slingge on 2018/9/17
 */
class RedmanModel{


    var result = ""
    var resultNote = ""

    var dataList=ArrayList<dataModel>()

    class dataModel{
        var userId = ""//用户id
        var userImg = ""
        var userName = ""//用户昵称

        var userEffectNum = ""//发布人影响力值
        var isAttention = ""//是否已经关注 0未关注 1已关注
        var rankValue = ""//排名
    }

}