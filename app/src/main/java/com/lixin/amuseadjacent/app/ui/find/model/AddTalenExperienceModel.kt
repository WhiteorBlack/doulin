package com.lixin.amuseadjacent.app.ui.find.model

import com.lixin.amuseadjacent.app.util.StaticUtil
import java.io.Serializable
import java.util.ArrayList

/**
 * Created by Slingge on 2018/9/17
 */
class AddTalenExperienceModel :Serializable{

    var cmd = "addExperience"
    val uid = StaticUtil.uid

    var experienceId=""//达人经历编辑经历使用

    var startTime = ""
    var endTime = ""
    var title = ""
    var content = ""
    var file = ArrayList<String>()

}