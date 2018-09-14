package com.lixin.amuseadjacent.app.ui.mine.model

import java.io.Serializable

/**
 * Created by Slingge on 2018/9/14
 */
class ShieldModel{


    var result = ""
    var resultNote = ""
    var totalPage = 1
    var dataList = ArrayList<shieldModel>()

    class shieldModel : Serializable {
        var userId = ""
        var userName = ""

        var userIcon = ""//0女 1男
        var userSex = ""

        var userAge = ""
        var userAutograph = ""//用户签名
    }



}