package com.lixin.amuseadjacent.app.ui.mine.model

/**
 * 举报
 * Created by Slingge on 2018/9/13
 */
class ReportModel {

    var result = ""
    var resultNote = ""
    var totalPage = 1

    var dataList=ArrayList<irreguModel>()

    class irreguModel {
        var larId = ""//违规id
        var larTitle = ""//违规标题
        var larContent = ""//内容
        var userId = ""//被举报人id

        var userName = ""///被举报人昵称
        var userIcon = ""//被举报人头像
        var state = ""//0待审核 1已举报 2举报失败
        var time = ""//时间
    }

}