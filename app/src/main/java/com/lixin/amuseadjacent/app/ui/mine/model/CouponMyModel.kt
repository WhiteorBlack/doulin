package com.lixin.amuseadjacent.app.ui.mine.model

import java.io.Serializable

/**
 * 我的优惠券列表
 * Created by Slingge on 2018/9/12
 */
class CouponMyModel : Serializable {

    var result = ""
    var resultNote = ""
    var totalPage = 1//总页数

    var dataList = ArrayList<couponModel>()

    class couponModel : Serializable {
        var securitiesid = ""//优惠券id
        var securitiesPrice = ""//优惠券金额

        var securitiesName = ""//优惠券名称
        var securitiesImg = ""//优惠券图片
        var securitiesEndTime = ""//优惠券截止时间
    }


}