package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_talent_type.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 达人类型选择
 * Created by Slingge on 2018/8/22
 */
class TalentTypeActivity : BaseActivity() {

    private var flag = ""//选中的类型
    private var skillTvList = ArrayList<TextView>()
    private var occupationTvList = ArrayList<TextView>()
    private var businessTvList = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talent_type)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("技能选择")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "保存"
        tv_right.setOnClickListener { v ->
            val intent = Intent()
            intent.putExtra("flag", flag)
            setResult(0, intent)
            finish()
        }


        for (i in 0 until 10) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, fl_skill, false) as TextView
            tv.text = "技能" + i.toString()
            tv.setOnClickListener {
                cleanTextColor1()
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_them3)
                flag= "技能$i"
            }
            skillTvList.add(tv)
            fl_skill.addView(tv)
        }

        for (i in 0 until 10) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, fl_occupation, false) as TextView
            tv.text = "职业" + i.toString()
            tv.setOnClickListener {
                cleanTextColor2()
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_them3)
                flag= "职业$i"
            }
            occupationTvList.add(tv)
            fl_occupation.addView(tv)
        }


        for (i in 0 until 10) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, fl_business, false) as TextView
            tv.text = "商业" + i.toString()
            tv.setOnClickListener {
                cleanTextColor3()
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_them3)
                flag= "商业$i"
            }
            businessTvList.add(tv)
            fl_business.addView(tv)
        }

    }


    private fun cleanTextColor1() {
        for (i in skillTvList.indices) {
            skillTvList[i].setTextColor(Color.parseColor("#ff333333"))
            skillTvList[i].setBackgroundResource(R.drawable.bg_gray3)
        }
    }

    private fun cleanTextColor2() {
        for (i in skillTvList.indices) {
            occupationTvList[i].setTextColor(Color.parseColor("#ff333333"))
            occupationTvList[i].setBackgroundResource(R.drawable.bg_gray3)
        }
    }

    private fun cleanTextColor3() {
        for (i in skillTvList.indices) {
            businessTvList[i].setTextColor(Color.parseColor("#ff333333"))
            businessTvList[i].setBackgroundResource(R.drawable.bg_gray3)
        }
    }

}