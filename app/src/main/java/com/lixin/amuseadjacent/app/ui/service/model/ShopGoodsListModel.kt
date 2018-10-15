package com.lixin.amuseadjacent.app.ui.service.model

/**
 * Created by Slingge on 2018/9/18
 */
class ShopGoodsListModel {


    var result = ""
    var resultNote = ""

    var bannerList =  ArrayList<bannerModel>()
    class bannerModel {
        var topImgUrl = ""// id
        var topImgDetailUrl = ""//
        var topImgDetailUrlState = ""//
    }

    var dataList = java.util.ArrayList<dataModel>()

    class dataModel {
        var firstCategoryId = ""// id
        var firstCategoryName = ""//商品名称
        var secondList = java.util.ArrayList<secondModel>()
    }

    class secondModel {
        var secondCategoryId = ""// id
        var secondCategoryName = ""//商品名称
    }

}