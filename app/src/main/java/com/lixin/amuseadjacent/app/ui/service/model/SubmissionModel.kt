package com.lixin.amuseadjacent.app.ui.service.model

import com.lixin.amuseadjacent.app.util.StaticUtil

/**
 * 提交订单内容
 * Created by Slingge on 2018/9/20
 */
class SubmissionModel {

    val cmd = "generateOrder"
    val uid = StaticUtil.uid

    var type = ""//0新鲜果蔬 1洗衣洗鞋 2超市便利
    var allprice = ""
    var payprice = ""//实付总价格(减去优惠券后)
    var securitiesid = ""
    var addressId = ""
    var message = ""

    var goodsList=ArrayList<goodsModel>()

    class goodsModel{
        var goodsId = ""
        var count = ""
    }

}