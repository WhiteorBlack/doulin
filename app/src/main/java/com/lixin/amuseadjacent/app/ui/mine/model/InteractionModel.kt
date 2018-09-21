package com.lixin.amuseadjacent.app.ui.mine.model

/**
 * 互动
 * Created by Slingge on 2018/9/21
 */
class InteractionModel {

    var result = ""
    var resultNote = ""
    var totalPage = 1

    var dataList = ArrayList<dataModel>()

    class dataModel {
        var type = ""//0帮帮 1活动
        var activityId = ""//活动id
        var activityName = ""//活动名称
        var activityImg = ""//活动封面

        var activityAddress = ""//活动地址
        var activityTime = ""//活动时间
        var activityAllnum = ""//活动人数
        var activityNownum = ""//活动当前人数
        var activityMoney=""

        var userId = ""//发布人id
        var userName = ""//发布人昵称
        var userIcon = ""//发布人头像
        var userEffectNum = ""//发布人影响力值

        var bangbangId = ""//帮帮id
        var bangbangContent = ""//帮帮内容
        var bangbangImageUrl = ""//视频封面连接
        var bangbangVideoUrl = ""//视频连接

        var bangbangImgUrl = ArrayList<String>()//图片链接
        var zanNum = ""//点赞数量
        var commentNum = ""//评论数量
        var time = ""//时间
    }


}