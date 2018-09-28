package com.lixin.amuseadjacent.app.ui.entrance

import android.os.Bundle
import android.text.TextUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.MainActivity
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.SharedPreferencesUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import java.util.*

/**
 * Created by Slingge on 2018/9/5
 */
class StartActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        init()
    }


    private fun init() {
        StatusBarUtil.transparentStatusBar(this)
        StatusBarBlackWordUtil.StatusBarLightMode(this)
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                val isFirst = SharedPreferencesUtil.getSharePreBoolean(this@StartActivity, "isFirst")
                if (!isFirst) {
                    MyApplication.openActivity(this@StartActivity, WelComeActivity::class.java)
                    finish()
                    return
                }

                val uid = SharedPreferencesUtil.getSharePreStr(this@StartActivity, SharedPreferencesUtil.uid)
                if (!TextUtils.isEmpty(uid)) {
                    StaticUtil.uid = uid
                    StaticUtil.phone=SharedPreferencesUtil.getSharePreStr(this@StartActivity, SharedPreferencesUtil.Phone)
                    MyApplication.openActivity(this@StartActivity, MainActivity::class.java)
                } else {
                    MyApplication.openActivity(this@StartActivity, SginInActivity::class.java)
                }
                finish()
            }
        }
        timer.schedule(task, 2000)//第一次执行前的毫秒延迟时间。在随后的执行之间毫秒内的时间

    }

}