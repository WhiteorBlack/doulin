package com.lixin.amuseadjacent.app.ui.mine.model

import com.lixin.amuseadjacent.app.util.StaticUtil

/**
 * Created by Slingge on 2018/9/7
 */
class UserMessageModel {

    var cmd = "editUserMessage"// (传哪个改哪个,可单可多)
    var uid = StaticUtil.uid
    var nickname = ""
    var sex = ""//性别
    var birthday = ""//生日(年月日)
    var autograph = ""//个人签名

    var occupation = ""//职业


}