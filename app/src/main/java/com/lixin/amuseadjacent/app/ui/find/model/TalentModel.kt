package com.lixin.amuseadjacent.app.ui.find.model

/**
 * 达人
 * Created by Slingge on 2018/9/17
 */
class TalentModel {
    var result = ""
    var resultNote = ""
    var totalPage = 1

    var topImgUrl = ""
    var topImgDetailUrl = ""
    var topImgDetailUrlState = ""//图片点击详情链接状态 0 有效 1无效
    var isApply = ""//0未申请达人 1达人审核中 2审核通过 3申请达人失败

    var dataList=ArrayList<dataModel>()

    class dataModel{
        var userId = ""
        var userImg = ""
        var userName = ""
        var userlabel = ""

        var userEffectNum = ""
        var userDesc = ""
        var isAttention = ""// 0未关注 1已关注
    }


}