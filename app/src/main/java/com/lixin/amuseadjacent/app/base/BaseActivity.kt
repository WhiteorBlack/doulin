package com.lixin.amuseadjacent.app.ui.base

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.util.AppManager
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil


/**
 * Created by Slingge on 2017/4/6 0006.
 */

abstract class BaseActivity : AppCompatActivity() {

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
        if (Build.VERSION.SDK_INT > 19) {
            val view_staus = findViewById<View>(R.id.view_staus)
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(this, view_staus)
            StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
            StatusBarBlackWordUtil.StatusBarLightMode(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ProgressDialog.dissDialog()
    }


}
