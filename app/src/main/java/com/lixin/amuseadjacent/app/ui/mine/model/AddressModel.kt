package com.lixin.amuseadjacent.app.ui.mine.model

import java.io.Serializable

/**
 * Created by Slingge on 2018/9/14
 */
class AddressModel : Serializable {

    var result = ""
    var resultNote = ""
    var totalPage = 1
    var dataList = ArrayList<addModel>()

    class addModel : Serializable {
        var addressId = ""
        var username = ""

        var userPhone = ""
        var city = ""

        var address = ""
        var isdefault = ""// 0非默认，1默认
    }
}