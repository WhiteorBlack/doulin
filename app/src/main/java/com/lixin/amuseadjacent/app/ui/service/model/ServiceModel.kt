package com.lixin.amuseadjacent.app.ui.service.model

import java.io.Serializable

/**
 * Created by Slingge on 2018/9/18
 */
class ServiceModel :Serializable{

    var result = ""
    var resultNote = ""

    var bannerList = ArrayList<bannerModel>()
    var dataList = ArrayList<dataModel>()

    class bannerModel {
        var topImgUrl = ""//服务首页顶部图片
        var topImgDetailUrl = ""
    }


    class dataModel :Serializable{
        var optimizationId = ""//优选分类id
        var optimizationName = ""//优选分类名称

        var optimizationImg = ""//优选分类图标
        var optimizationImgs = ""//优选分类顶部图片
        var optimizationDesc = ""//优选分类简介
    }

}