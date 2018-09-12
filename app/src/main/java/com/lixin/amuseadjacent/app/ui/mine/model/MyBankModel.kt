package com.lixin.amuseadjacent.app.ui.mine.model

import java.io.Serializable

/**
 * 我的银行卡
 * Created by Slingge on 2018/9/12
 */
 class MyBankModel:Serializable{


    var result=""
    var resultNote=""

    var dataList=ArrayList<detailsModel>()

    class detailsModel:Serializable{
        var cardId=""
        var cardName=""
        var cardNum=""
        var cardUsername=""
    }


}