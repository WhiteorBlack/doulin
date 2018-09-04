package com.lixin.amuseadjacent.app.ui.entrance

import android.os.Bundle
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lxkj.linxintechnologylibrary.app.util.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_sginin.*

/**
 * 登录
 * Created by Slingge on 2018/8/13
 */
class SginInActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sginin)
        init()
    }

    private fun init() {
        et_phone.setOnClickListener { v ->
            MyApplication.openActivity(this, SginInActivity2::class.java)
        }
        ProgressDialog.showDialog(this)
    }


}