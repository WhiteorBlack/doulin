package com.lixin.amuseadjacent.app.ui.mine.model

import java.io.Serializable

/**
 * 我的订单
 * Created by Slingge on 2018/9/21
 */
class MyOrderModel :Serializable{

    var result = ""
    var resultNote = ""
    var totalPage = 1

    var dataList = ArrayList<dataModel>()

    class dataModel :Serializable{
        var orderNum = ""//订单号
        var orderType = ""//0新鲜果蔬 1洗衣洗鞋 2超市便利

        var oderAllPrice = ""//订单总价格
        var oderPayPrice = ""//订单实付总价格

        var orderState = ""//1待付款,2待送货,3待收货,4待取货,5清洗中,6待归还,7归还中,8退款中,9已退款,10待评价,11已完成 12已取消
        var adtime = ""//下单时间
        var orderCommodity = ArrayList<orderModel>()
    }

    class orderModel :Serializable{
        var commodityid = ""//商品id
        var commodityPic = ""//商品图片

        var commodityTitle = ""//商品名称
        var commodityPrice = ""//商品价格

        var commodityBuyNum = ""//购买数量
    }

}