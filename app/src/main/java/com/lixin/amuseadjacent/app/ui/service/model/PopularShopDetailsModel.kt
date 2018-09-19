package com.lixin.amuseadjacent.app.ui.service.model

import java.util.ArrayList

/**
 * 小区店铺
 * Created by Slingge on 2018/9/19
 * */
class PopularShopDetailsModel {


    var result = ""
    var resultNote = ""

    var dataList = java.util.ArrayList<dataModel>()
    var `object`=objectModel()

    class objectModel {
        var shopName = ""
        var shopImgUrl = ArrayList<String>() //图片点击详情链接

        var shopDesc = ""
        var shopPrice = ""
        var shopAddress = ""
        var shopPhone = ""

        var shopTime = ""
    }

    class dataModel {
        var serviceId = ""//服务id
        var serviceName = ""//服务名称
        var serviceImg = ""//服务图片
        var serviceDesc = ""//服务简介
        var servicePrice = ""//服务价格
    }


}