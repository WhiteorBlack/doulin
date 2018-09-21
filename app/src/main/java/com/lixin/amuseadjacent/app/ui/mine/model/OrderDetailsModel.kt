package com.lixin.amuseadjacent.app.ui.mine.model

/**
 * Created by Slingge on 2018/9/21
 */
class OrderDetailsModel {


    var result = ""
    var resultNote = ""
    var username = ""//收货人姓名
    var userPhone = ""//收货人电话

    var city = ""//收货人省市
    var address = ""//收货人街道
    var orderType = ""//0/新鲜果蔬 1洗衣洗鞋 2超市便利
    var oderAllPrice = ""//订单总价格

    var oderPayPrice = ""//订单实付总价格
    var orderState = ""//1待付款,2待送货,3待收货,4待取货,5清洗中,6待归还,7归还中,8退款中,9已退款,10待评价,11已完成 12已取消
    var payType = ""//支付方式,0零钱,1支付宝,2微信 3银行卡
    var message = ""////留言

    var securitiesPrice = ""//优惠券金额
    var orderNum = ""//订单号
    var reason = ""//退款原因
    var refundPics = ArrayList<String>()//申请退款的图片

    var adtime = ""//下单时间
    var payTime = ""//支付时间
    var sendTime = ""//发货时间

    var getTime = ""//取货时间
    var cleanTime = ""//清洗完成时间
    var returnTime = ""//归还时间

    var endTime = ""//确认收货时间
    var refundTime = ""//退款时间
    var refundShenTime = ""//退款审核时间

    var orderCommodity = ArrayList<MyOrderModel.orderModel>()


}