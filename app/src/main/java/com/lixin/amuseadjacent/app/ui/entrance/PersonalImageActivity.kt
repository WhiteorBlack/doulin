package com.lixin.amuseadjacent.app.ui.entrance

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_personal_image.*

/**
 * 个人形象
 * Created by Slingge on 2018/8/15
 */
class PersonalImageActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_image)
        init()
    }


    private fun init() {
        iv_back.setOnClickListener(this)
        tv_perfect.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_perfect -> {
                MyApplication.openActivity(this, PerfectImageActivity::class.java)
            }
        }
    }


}