package com.lixin.amuseadjacent.app.ui.mine.model

/**
 * 余额明细
 * Created by Slingge on 2018/9/12
 */
class BalanceDetailsModel{

    var result=""
    var resultNote=""
    var totalPage=1
    var balance=""

    var dataList=ArrayList<detailsModel>()

    class detailsModel{
        var title=""
        var money=""
        var time=""
    }

}