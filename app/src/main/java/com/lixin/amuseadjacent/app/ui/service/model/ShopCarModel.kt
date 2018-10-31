package com.lixin.amuseadjacent.app.ui.service.model

/**
 * Created by Slingge on 2018/9/20
 */
class ShopCarModel {


    var result = ""
    var resultNote = ""

    var marketList = ArrayList<carModel>()//超市便利
    var clothesList = ArrayList<carModel>()//洗衣洗鞋
    var fruitsList = ArrayList<carModel>()//新鲜果蔬

    class carModel {
        var optimizationid=""
        var cartId = ""
        var goodsId = ""
        var goodsCuprice=""
        var goodsTitle = ""
        var goodsImage = ""

        var goodsPrice = ""
        var count = ""

        var isSelect = false

    }

}