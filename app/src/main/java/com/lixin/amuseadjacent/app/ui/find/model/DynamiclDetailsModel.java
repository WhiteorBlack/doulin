package com.lixin.amuseadjacent.app.ui.find.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slingge on 2018/9/15
 */
public class DynamiclDetailsModel {


    public String result = "";
    public String resultNote = "";

    public List<ActivityCommentModel1.commModel> dataList = new ArrayList();

    public objectModel object;

    public class objectModel {
        public String dynamicId = "";//动态id
        public String dynamicUid = "";//发布动态人id
        public String dynamicName = "";//发布动态人昵称

        public String dynamicIcon = "";//发布动态人头像
        public String dynamicContent = "";//动态内容
        public String dynamicImg = "";//动态封面图片

        public String dynamicVideo = "";//动态视频连接
        public ArrayList<String> dynamicImgList = new ArrayList();//动态多图片数组
        public String dynamicAddress = "";//发布动态地址

        public String type = "";//0动态 1帮帮
        public String state = "";//0正常显示 1隐藏
        public String userEffectNum = "";//发布人影响力值

        public String zanNum = "";//点赞数量
        public String commentNum = "";//评论数量
        public String time = "";//时间

        public String iscang = "";//0未收藏 1已收藏
        public String isZan = "";//是否已经赞过 0未赞过 1已赞过
        public String isAttention = "";//是否已经关注 0未关注 1已关注
    }




}
