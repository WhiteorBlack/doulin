package com.lixin.amuseadjacent.app.ui.find.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 活动详情
 * Created by Slingge on 2018/9/18
 */
public class EventDetailsModel implements Serializable {


    public String result = "";
    public String resultNote = "";


    public objectModel object = new objectModel();

    public ArrayList<signModel> signList;

    public ArrayList<ActivityCommentModel1.commModel> commList;


    public class objectModel {
        public String activityId = "";//活动id
        public String activityName = "";//活动名称
        public String activityDesc = "";//活动简介
        public String activityStarttime = "";//活动开始时间

        public String activityEndtime = "";//活动结束时间
        public String activitySignEndtime = "";//活动报名截止时间
        public String activityAddress = "";//活动地址
        public String activityPhone = "";//联系方式

        public String userid = "";//发布人id
        public String userName = "";//发布人昵称
        public String activityAllnum = "";//活动人数
        public String activityNownum = "";//活动当前人数

        public String activityMoney = "";//人均费用
        public String activityState = "";//状态 0报名中 1进行中 2已结束
        public ArrayList<String> activityImg = new ArrayList();//图片路径数组
        public String zanNum = "";//点赞数量

        public String commentNum = "";//评论数量
        public String time = "";//时间
        public String iscang = "";//0未收藏 1已收藏
        public String isZan = "";//是否已经赞过 0未赞过 1已赞过

        public String isAttention = "";//是否已经关注 0未关注 1已关注
        public String issignup = "";//是否已经报名 0未报名 1已报名
    }


    public class signModel implements Serializable {//报名列表
        public String userId = "";//用户id
        public String userImg = "";//用户头像
        public String userName = "";//用户昵称
        public String userSex = "";//用户性别

        public String userAge = "";//用户年龄
        public String userPhone = "";//用户手机号
        public String signNum = "";//报名人数
    }


}
