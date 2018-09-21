package com.lixin.amuseadjacent.app.ui.mine.activity.order

import android.os.Bundle
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.request.MyOrder_144155
import com.lixin.amuseadjacent.app.util.AbStrUtil
import kotlinx.android.synthetic.main.activity_evaluate.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * Created by Slingge on 2018/9/4
 */
class EvaluateActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("评价")
        tv_title.visibility = View.INVISIBLE
        tv_right.visibility = View.VISIBLE
        tv_right.text = "发表"
        tv_right.setOnClickListener { v ->
            ProgressDialog.showDialog(this)
            val content = AbStrUtil.etTostr(et_reason)
            val star = ratingBar.numStars.toString()
            MyOrder_144155.evaluateOrder(this, intent.getStringExtra("num"), content, star, intent.getIntExtra("position", -1))
        }
    }


}