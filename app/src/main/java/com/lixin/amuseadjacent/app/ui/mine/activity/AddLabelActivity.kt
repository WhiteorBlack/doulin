package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.AbStrUtil
import kotlinx.android.synthetic.main.activity_add_label.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 添加标签
 * Created by Slingge on 2018/9/2.
 */
class AddLabelActivity : BaseActivity() {

    private var flag = -1//0运动，1音乐，2美食，3电影，4书籍


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_label)
        init()
    }


    private fun init() {
        inittitle("添加标签")
        StatusBarWhiteColor()

        tv_right.text = "保存"
        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener { v ->

        }

        for (i in 0 until 1) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.text = "运动" + i.toString()
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_sport)
//            tv.visibility= View.INVISIBLE
            fl_label.addView(tv)
        }

        val tv = LayoutInflater.from(this).inflate(
                R.layout.layout_flow_talent_type, ll_main, false) as TextView
        tv.text = "自定义"
        AbStrUtil.setDrawableLeft(this, R.drawable.ic_add4, tv, 10)
        tv.setTextColor(resources.getColor(R.color.white))
        tv.setBackgroundResource(R.drawable.bg_gray3)
//            tv.visibility= View.INVISIBLE
        fl_customLabel.addView(tv)

        for (i in 0 until 1) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.text = "音乐" + i.toString()
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_hobby_music)
//            tv.visibility= View.INVISIBLE
            fl_customLabel.addView(tv)
        }

    }

}