package com.lixin.amuseadjacent.app.ui.base

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.util.AppManager
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil


/**
 * Created by Slingge on 2017/4/6 0006.
 */

class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.addActivity(this)
    }


    protected fun inittitle(title: String) {
        val tv_title = findViewById<TextView>(R.id.tv_title)
        tv_title.text = title

        val iv_back = findViewById<ImageView>(R.id.iv_back)
        iv_back.setOnClickListener { view -> finish() }
    }

    //白色状态栏
    fun StatusBarWhiteColor() {
        val view_staus = findViewById<View>(R.id.view_staus)
        if (Build.VERSION.SDK_INT > 19) {
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setHeightAndPadding(this, view_staus)
            StatusBarUtil.transparentStatusBar(this)
            StatusBarBlackWordUtil.StatusBarLightMode(this)
        }
    }


}
