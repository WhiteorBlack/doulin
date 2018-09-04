package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.DataCleanManager
import kotlinx.android.synthetic.main.activity_setup.*

/**
 * Created by Slingge on 2018/9/4
 */
class SetUpActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("设置")

        try {
            tv_eliminate.text = DataCleanManager.getTotalCacheSize(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        shield.setOnClickListener(this)
        change_pass.setOnClickListener(this)
        tv_address.setOnClickListener(this)
        tv_eliminate.setOnClickListener(this)
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
        }

    }


}