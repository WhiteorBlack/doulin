package com.lixin.amuseadjacent.app.ui.service.model

import com.lixin.amuseadjacent.app.util.StaticUtil

/**
 * 领取优惠券
 * Created by Slingge on 2018/9/13
 */
class ReceiveCouponModel {

    var cmd = "receiveSecurities"
    var uid = StaticUtil.uid
    var securitiesid = ArrayList<String>()
}