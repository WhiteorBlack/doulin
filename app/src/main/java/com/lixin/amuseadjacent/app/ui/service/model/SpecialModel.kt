package com.lixin.amuseadjacent.app.ui.service.model


/**
 * Created by Slingge on 2018/9/18
 */
class SpecialModel {


    var result = ""
    var resultNote = ""

    var dataList = ArrayList<dataModel>()

    class dataModel {
        var goodsId = ""// id
        var goodsName = ""//商品名称

        var goodsImg = ""//商品图片
        var goodsPrice = ""//商品价格
        var goodsCuprice = ""//促销价格

        var goodsSallnum = ""//商品销量
        var goodsStock = ""//商品库存
        var goodsType = ""//商品类型 0新鲜果蔬 1洗衣洗鞋 2超市便利

        var isSelect=false
    }

}