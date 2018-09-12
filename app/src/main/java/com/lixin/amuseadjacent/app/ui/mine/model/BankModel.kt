package com.lixin.amuseadjacent.app.ui.mine.model

/**
 * 银行列表
 * Created by Slingge on 2018/9/12
 */
class BankModel {

    var result = ""
    var resultNote = ""

    var dataList = ArrayList<bankModel>()

    class bankModel {
        var bankId = ""
        var bankName = ""
    }


}