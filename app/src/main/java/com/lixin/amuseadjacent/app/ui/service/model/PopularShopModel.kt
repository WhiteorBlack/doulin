package com.lixin.amuseadjacent.app.ui.service.model

/**
 * 小区店铺
 * Created by Slingge on 2018/9/19
 * */
class PopularShopModel {


    var result = ""
    var resultNote = ""
    var topImgUrl = ""//小区店铺顶部图片
    var topImgDetailUrl = ""

    var dataList = java.util.ArrayList<dataModel>()
    var bannerList = java.util.ArrayList<bannerModel>()

    class bannerModel {
        var topImgUrl = ""
        var topImgDetailUrl = ""//图片点击详情链接
    }

    class dataModel {
        var shopId = ""
        var shopName = ""//店铺名称
        var shopImg = ""//店铺图片
        var shopDesc = ""//店铺简介
        var shopPrice = ""//人均价格
    }


}