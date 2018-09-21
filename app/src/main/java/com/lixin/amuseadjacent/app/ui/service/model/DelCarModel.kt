package com.lixin.amuseadjacent.app.ui.service.model

import com.lixin.amuseadjacent.app.util.StaticUtil

/**
 * 删除购物车
 * Created by Slingge on 2018/9/20
 */
class DelCarModel {

    var cmd = "deleteMyShopCar"
    var uid = StaticUtil.uid

    var type = "1"//1普通删除 2清空所有购物车
    var objId = ArrayList<String>()

}