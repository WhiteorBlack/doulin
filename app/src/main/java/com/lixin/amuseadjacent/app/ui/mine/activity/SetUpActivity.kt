package com.lixin.amuseadjacent.app.ui.mine.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import cn.jpush.android.api.JPushInterface
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.MainActivity
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.entrance.SginInActivity
import com.lixin.amuseadjacent.app.ui.entrance.VerificationPasswordActivity
import com.lixin.amuseadjacent.app.ui.mine.request.VersionUpData_153
import com.lixin.amuseadjacent.app.util.*
import kotlinx.android.synthetic.main.activity_setup.*

/**
 * Created by Slingge on 2018/9/4
 */
class SetUpActivity : BaseActivity(), View.OnClickListener {

    private var updataUrl = ""
    private var updataName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
        init()
    }

    override fun onStart() {
        super.onStart()
        VersionUpData_153.updata(this, object : VersionUpData_153.UpdataCallBack {
            override fun updata(updataUrl: String, versionName: String) {
                view_updata.visibility = View.VISIBLE
                this@SetUpActivity.updataUrl = updataUrl
                updataName = versionName
            }
        })
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("设置")

        try {
            tv_eliminate.text = DataCleanManager.getTotalCacheSize(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (SpUtil.get("isOn", true) as Boolean) {
            sv_message.setOpened(true)
        } else {
            sv_message.setOpened(false)
        }

        shield.setOnClickListener(this)
        change_pass.setOnClickListener(this)
        tv_address.setOnClickListener(this)
        tv_eliminate.setOnClickListener(this)
        tv_exitlogon.setOnClickListener(this)

        tv_updata.setOnClickListener(this)
        sv_message.setOnClickListener(this)
        sv_message.isOpened=SpUtil.get("isOn", true) as Boolean
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.shield -> {
                MyApplication.openActivity(this, ShieldActivity::class.java)
            }
            R.id.change_pass -> {
                MyApplication.openActivity(this, ChangePassActivity::class.java)
            }
            R.id.tv_address -> {
                MyApplication.openActivity(this, AddressActivity::class.java)
            }
            R.id.tv_eliminate -> {
                DataCleanManager.clearAllCache(this)
                tv_eliminate.text = "0.0MB"
            }
            R.id.tv_exitlogon -> {
                val sp = getSharedPreferences(SharedPreferencesUtil.NAME, 0)
                sp.edit().putString(SharedPreferencesUtil.Phone, "")
                        .putString(SharedPreferencesUtil.Pass, "")
                        .putString(SharedPreferencesUtil.uid, "")
                        .putString(SharedPreferencesUtil.communityName, "")
                        .putString(SharedPreferencesUtil.communityId, "")
                        .commit()
                StaticUtil.uid = ""
                StaticUtil.communityId = ""
                AppManager.finishAllActivity()
                MyApplication.openActivity(this, VerificationPasswordActivity::class.java)
            }
            R.id.tv_updata -> {
                if (TextUtils.isEmpty(updataUrl)) {
                    return
                }
                val uri = Uri.parse(updataUrl)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = uri
                startActivity(intent)
            }
            R.id.sv_message -> {
                if(sv_message.isOpened){
                    JPushInterface.resumePush(this@SetUpActivity)
                }else{
                    JPushInterface.stopPush(this@SetUpActivity)
                }
                SpUtil.put("isOn", sv_message.isOpened)
            }
        }

    }


}