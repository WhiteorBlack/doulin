package com.netease.nim.uikit.business.recent.adapter;

import java.io.Serializable;
import java.util.ArrayList;

public class MessListModel implements Serializable{

    private String result;
    private String resultNote;
    private ArrayList<msgModel> dataList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public ArrayList<msgModel> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<msgModel> dataList) {
        this.dataList = dataList;
    }

    public class msgModel implements Serializable{
        private String messageId ;
        private String messageTitle ;
        private String messageTime ;
        private String messagenum ;
        private String type ;  //0系统消息 1订单信息 2评论信息

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getMessageTitle() {
            return messageTitle;
        }

        public void setMessageTitle(String messageTitle) {
            this.messageTitle = messageTitle;
        }

        public String getMessageTime() {
            return messageTime;
        }

        public void setMessageTime(String messageTime) {
            this.messageTime = messageTime;
        }

        public String getMessagenum() {
            return messagenum;
        }

        public void setMessagenum(String messagenum) {
            this.messagenum = messagenum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
