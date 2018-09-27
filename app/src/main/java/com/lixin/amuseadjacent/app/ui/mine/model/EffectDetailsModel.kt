package com.lixin.amuseadjacent.app.ui.mine.model

/**
 * 影响力明细
 * Created by Slingge on 2018/9/27
 */
 class EffectDetailsModel{


    var result=""
    var resultNote=""
    var dataList=ArrayList<dataModel>()

    var totalPage=1

    class dataModel{

        var effectNumId=""//明细id
        var effectNum=""//影响力
        var effectNumTitle=""//标题
        var effectNumTime=""//时间

    }


}