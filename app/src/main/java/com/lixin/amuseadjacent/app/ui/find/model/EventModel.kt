package com.lixin.amuseadjacent.app.ui.find.model

/**
 * 达人
 * Created by Slingge on 2018/9/17
 */
class EventModel {
    var result = ""
    var resultNote = ""
    var totalPage = 1

    var topImgUrl = ""
    var topImgDetailUrl = ""

    var dataList = ArrayList<dataModel>()

    class dataModel {
        var userid = ""//发布人id
        var userName = ""//发布人昵称
        var userIcon = ""//发布人头像
        var userEffectNum = ""//发布人影响力值

        var activityId = ""//活动id
        var activityName = ""//活动名称
        var activityImg = ""//活动封面

        var activityAddress = ""//活动地址
        var activityTime = ""//活动开始时间
        var activityAllnum = ""//活动人数

        var activityNownum = ""//活动当前人数
        var activityMoney = ""//人均费用
        var activityState = ""//状态 0报名中 1进行中 2已结束

        var zanNum = ""//点赞数量
        var commentNum = ""//评论数量
        var time = ""//时间

        var iscang = ""//0未收藏 1已收藏
        var isZan = ""//是否已经赞过 0未赞过 1已赞过
        var isAttention = ""//是否已经关注 0未关注 1已关注

        var issignup = ""//是否已经报名 0未报名 1已报名
    }


}