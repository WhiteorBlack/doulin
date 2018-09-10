package com.lixin.amuseadjacent.app.ui.mine.model

import com.lixin.amuseadjacent.app.util.StaticUtil

/**
 * Created by Slingge on 2018/9/10
 */
class AddLabelModel{

    var cmd="addLabel"
    var uid=StaticUtil.uid

    var type=""//1 运动 2音乐 3美食 4电影 5图书 6其它

    var labelList=ArrayList<String>()
    var otherList=ArrayList<String>()
}