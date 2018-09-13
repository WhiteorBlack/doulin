package com.lixin.amuseadjacent.app.ui.service.model

/**
 * Created by Slingge on 2018/9/13
 */
 class CouponModel{

    var result = ""
    var resultNote = ""

    var dataList = ArrayList<couponModel>()

    class couponModel {
        var securitiesid = ""//优惠券id
        var securitiesPrice = ""//优惠券金额

        var securitiesName = ""//优惠券名称
        var securitiesImg = ""//优惠券图片
        var securitiesEndTime = ""//优惠券截止时间

        var isSelect=false//是否选中
    }



}