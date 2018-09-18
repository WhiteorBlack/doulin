package com.lixin.amuseadjacent.app.ui.find.model

import java.io.Serializable

/**
 * Created by Slingge on 2018/9/14
 */
class FindModel : Serializable {


    var result = ""
    var resultNote = ""
    var topImgUrl = ""//发现首页顶部图片
    var topImgDetailUrl = ""//图片点击详情链接

    var theme = themeModel()
    var danamicList = ArrayList<dynamicModel>()
    var activity = activityModel()
    var redmanList = ArrayList<redmanModel>()

    class redmanModel {
        //日榜前三名
        var userId = ""
        var userImg = ""
    }

    class themeModel {

        var themeId = ""
        var themeTitle = ""
        var themeDetailUrl = ""//详情url
        var zanNum = ""//图片点击详情链接
        var commentNum = ""//评论数量
        var isZan = ""//是否已经赞过 0未赞过 1已赞过
    }

    class dynamicModel : Serializable {

        var dynamicId = ""
        var dynamicUid = ""//发布动态人id
        var dynamicName = ""//发布动态人昵称
        var dynamicIcon = ""//发布动态人头像
        var dynamicContent = ""//动态内容
        var dynamicImg = ""//动态封面图片

        var dynamicVideo = ""//动态视频连接
        var dynamicImgList = ArrayList<String>()//动态多图片数组
        var dynamicAddress = ""//动态地址
        var userEffectNum = ""//发布人影响力值


        var zanNum = ""//点赞数量
        var commentNum = ""//评论数量

        var time = ""//时间
        var isZan = ""//是否已经赞过 0未赞过 1已赞过

        var isAttention = ""//是否已经关注 0未关注 1已关注
    }

    class activityModel {

        var activityId = ""//活动id
        var activityName = ""//活动名称
        var activityImg = ""//活动封面
        var activityAddress = ""//活动地址
        var activityTime = ""//活动时间
        var activityAllnum = ""//活动人数

        var activityNownum = ""//活动当前人数
        var   activityMoney=""//人均费用
        var activityState = ""//状态 0报名中 1进行中 2已结束
        var userId = ""//发布人id
        var userName = ""//发布人昵称


        var userIcon = ""//发布人头像
        var userEffectNum = ""//发布人影响力值

        var zanNum = ""//点赞数量
        var commentNum = ""//评论数量

        var time = ""//时间
        var isZan = ""//是否已经赞过 0未赞过 1已赞过
        var isAttention = ""//是否已经关注 0未关注 1已关注
        var issignup = ""//是否已经报名 0未报名 1已报名
    }

}