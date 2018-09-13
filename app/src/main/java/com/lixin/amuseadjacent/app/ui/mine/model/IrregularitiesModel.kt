package com.lixin.amuseadjacent.app.ui.mine.model

/**
 * 违规
 * Created by Slingge on 2018/9/13
 */
class IrregularitiesModel {

    var result = ""
    var resultNote = ""
    var totalPage = 1

    var dataList=ArrayList<irreguModel>()

    class irreguModel {
        var larId = ""
        var larTitle = ""
        var larContent = ""
        var time = ""
    }

}