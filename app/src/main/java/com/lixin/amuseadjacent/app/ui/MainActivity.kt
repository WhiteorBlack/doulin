package com.lixin.amuseadjacent.app.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import cn.jzvd.JZMediaManager
import cn.jzvd.Jzvd
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.fragment.FindFragment
import com.lixin.amuseadjacent.app.ui.message.fragment.RecentContactsFragment
import com.lixin.amuseadjacent.app.ui.mine.fragment.MineFragment
import com.lixin.amuseadjacent.app.ui.mine.request.UserInfo_19
import com.lixin.amuseadjacent.app.ui.service.fragment.ServiceFragment
import com.lixin.amuseadjacent.app.util.PermissionHelper
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.StatusBarNotificationConfig
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * */
class MainActivity : BaseActivity() {

    private var mFragment = Fragment()
    private var bFragment1: RecentContactsFragment? = null
    private var bFragment2: FindFragment? = null
    private var bFragment3: ServiceFragment? = null
    private var bFragment4: MineFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        tab_1.isChecked = true
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        }
        bFragment1 = RecentContactsFragment()
        switchFragment(bFragment1!!)
        UserInfo_19.userInfo(this)
        RadioG_Bottem.setOnCheckedChangeListener { radioGroup, i -> selectStyle(i) }
        checkPermission()
        initNIM()
    }

    /**
     * 控制聊天状态栏通知设置
     */
    private fun initNIM() {
        NIMClient.toggleNotification(true)
        val stateConfig = StatusBarNotificationConfig()
        stateConfig.ring = true
        stateConfig.vibrate = true
        stateConfig.notificationFolded = true
//        stateConfig.notificationEntrance=P2PMessageActivity
        NIMClient.updateStatusBarNotificationConfig(stateConfig)
    }


    /**
     * 检查权限
     */
    private fun checkPermission() {
        val helper = PermissionHelper(this)
        helper.requestPermissions(object : PermissionHelper.PermissionListener {
            override fun doAfterGrand(vararg permission: String?) {
//                initMap()
            }

            override fun doAfterDenied(vararg permission: String?) {
            }

        }, Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    //根据具体点击切换显示对应fragment
    private fun selectStyle(ID: Int) {
        Jzvd.releaseAllVideos()
        when (ID) {
            R.id.tab_1 -> {
                if (Build.VERSION.SDK_INT > 19) {
                    StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
                }
                bFragment1 = RecentContactsFragment()
                switchFragment(bFragment1!!)
            }
            R.id.tab_2 -> {
                if (Build.VERSION.SDK_INT > 19) {
                    StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
                }
                if (bFragment2 == null) {
                    bFragment2 = FindFragment()
                }
                switchFragment(bFragment2!!)
            }
            R.id.tab_3 -> {
                if (Build.VERSION.SDK_INT > 19) {
                    StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
                }
                if (bFragment3 == null) {
                    bFragment3 = ServiceFragment()
                }
                switchFragment(bFragment3!!)
            }
            R.id.tab_4 -> {
                if (Build.VERSION.SDK_INT > 19) {
                    StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorTheme))
                }
                if (bFragment4 == null) {
                    bFragment4 = MineFragment()
                }
                switchFragment(bFragment4!!)
            }
        }
    }


    /**
     * 选择加载的Fragment
     */
    private fun switchFragment(fragment: Fragment) {
        if (fragment !== mFragment) {
            val transaction = supportFragmentManager
                    .beginTransaction()
            if (!fragment.isAdded) { // 先判断是否被add过
                transaction.hide(mFragment).add(R.id.fragment, fragment).commit() // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mFragment).show(fragment).commit() // 隐藏当前的fragment，显示下一个
            }
            mFragment = fragment
        }
    }


    //保证findFramgent能获取到onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        bFragment2!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPause() {
        super.onPause()
        Runtime.getRuntime().gc()
        Jzvd.releaseAllVideos()
    }


    private var exitTime: Long = 0
    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showToast("再按一次退出程序")
            exitTime = System.currentTimeMillis()
            return
        }
        super.onBackPressed()
    }

}
