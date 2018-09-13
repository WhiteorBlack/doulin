package com.lixin.amuseadjacent.app.ui.mine.model

/**
 * Created by Slingge on 2018/9/13
 */
class HelpModel {

    var result = "" //0成功1失败
    var resultNote = ""//
    var phone = ""//客服电话

    var dataList=ArrayList<problemModel>()

    class problemModel {

        var title = "" //
        var content = ""//

    }

}