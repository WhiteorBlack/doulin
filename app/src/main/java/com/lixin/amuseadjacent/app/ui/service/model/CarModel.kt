package com.lixin.amuseadjacent.app.ui.service.model

/**
 * 洗衣洗鞋 小购物车数据传递
 * Created by Slingge on 2018/9/20
 */
class CarModel {

    var carNum = 0//小购物车商品总数
    var totalModey = 0.0//小购物车商品价格


    class editModel(){
        var goodModel = ShopGoodsModel.dataModel()
        var flag=0//0添加，1移除
        var position=0//下标
    }



}