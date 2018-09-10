package com.lixin.amuseadjacent.app.ui.mine.model

/**
 * 标签
 * Created by Slingge on 2018/9/10
 */
 class LabelListModel{

    var result=""
    var resultNote=""

    var labelList=ArrayList<labelListModel>()
    var otherList=ArrayList<otherListModel>()

    class labelListModel{
        var labelId=""//标签id
        var laberName=""
        var state=""//0未选中 1已选中
    }

    class otherListModel{
      var  laberName=""//自定义标签内容
    }

}