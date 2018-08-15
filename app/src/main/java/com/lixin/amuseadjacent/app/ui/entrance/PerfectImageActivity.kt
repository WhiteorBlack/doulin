package com.lixin.amuseadjacent.app.ui.entrance

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.CommunityDialog
import kotlinx.android.synthetic.main.activity_perfect_image.*

/**
 * 完善个人形象
 * Created by Slingge on 2018/8/15
 */
class PerfectImageActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfect_image)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        iv_back.setOnClickListener(this)
        rl_community.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.rl_community -> {
                CommunityDialog.communityDialog(this)
            }
        }
    }


}