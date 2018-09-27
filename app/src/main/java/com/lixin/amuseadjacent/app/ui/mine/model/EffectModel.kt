package com.lixin.amuseadjacent.app.ui.mine.model

/**
 * Created by Slingge on 2018/9/27
 */
class EffectModel{


    var result=""
    var resultNote=""
    var allEffectNum=""
    var dayEffectNum=""
    var perfect=""//资料完整度
    var dataList=ArrayList<dataModel>()

    class dataModel{

        var taskId=""//任务id  1每日签到 ,2邀请好友 3发布活动  4发布动态 5完善资料 6认证用户
        var taskTitle=""//任务名称
        var isFinishTask=""//是否完成,0未,1过(邀请好友没有完不完成状态，该字段返回的是已邀请数量)
        var effectNum=""//送多少影响力

    }

}