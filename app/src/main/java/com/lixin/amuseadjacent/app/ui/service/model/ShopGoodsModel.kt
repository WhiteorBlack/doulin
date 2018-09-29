package com.lixin.amuseadjacent.app.ui.service.model

import java.io.Serializable

/**
 * Created by Slingge on 2018/9/18
 */
class ShopGoodsModel :Serializable{


    var result = ""
    var resultNote = ""

    var dataList = java.util.ArrayList<dataModel>()

    class dataModel :Serializable{
        var goodsId = ""//商品id
        var goodsName = ""//商品名称

        var goodsImg = ""//商品图片
        var goodsPrice = ""//商品价格

        var goodsCuprice = ""//促销价格
        var goodsSallnum = ""//商品销量

        var goodsStock = ""//商品库存
        var goodsType = ""//商品类型 0新鲜果蔬 1洗衣洗鞋 2超市便利


        var goodsNum=0//选中的数量
        var isSelect=false//是否添加到购物车
        var money=0.0//本商品所有数量支付价格
         var UnitPrice=0.0//

        var isAdd=false//是否加入购物车

    }

}