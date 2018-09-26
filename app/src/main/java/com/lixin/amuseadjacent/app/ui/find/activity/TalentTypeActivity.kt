package com.lixin.amuseadjacent.app.ui.find.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.find.model.TalentLableModel
import com.lixin.amuseadjacent.app.ui.find.request.Talent212_218225
import kotlinx.android.synthetic.main.activity_talent_type.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 达人类型选择
 * Created by Slingge on 2018/8/22
 */
class TalentTypeActivity : BaseActivity() {

    private var flag = ""//选中的类型
    private var skillTvList = ArrayList<TextView>()
    private var occupationTvList = ArrayList<TextView>()
    private var businessTvList = ArrayList<TextView>()

    private var lableId = ""
    private var lableName = ""
    private var lableType = ""//达人类型 商业职业等

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talent_type)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        inittitle("技能选择")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "保存"
        tv_right.setOnClickListener { v ->
            val intent = Intent()
            intent.putExtra("id", lableId)
            intent.putExtra("name", lableName)
            intent.putExtra("type", lableType)
            setResult(0, intent)
            finish()
        }

        ProgressDialog.showDialog(this)
        Talent212_218225.talentLable()
    }


    @Subscribe
    fun onEvent(mode: TalentLableModel) {

        for (i in 0 until mode.labelList1.size) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, fl_skill, false) as TextView
            tv.text = mode.labelList1[i].labelName
            tv.setOnClickListener {
                cleanTextColor1()
                cleanTextColor2()
                cleanTextColor3()

                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_them3)
                lableId = mode.labelList1[i].labelId
                lableName = mode.labelList1[i].labelName
                lableType = "0"
            }
            skillTvList.add(tv)
            fl_skill.addView(tv)
        }

        for (i in 0 until mode.labelList2.size) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, fl_occupation, false) as TextView
            tv.text = mode.labelList2[i].labelName
            tv.setOnClickListener {
                cleanTextColor1()
                cleanTextColor2()
                cleanTextColor3()

                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_them3)
                lableId = mode.labelList2[i].labelId
                lableName = mode.labelList2[i].labelName
                lableType = "1"
            }
            occupationTvList.add(tv)
            fl_occupation.addView(tv)
        }

        for (i in 0 until mode.labelList3.size) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, fl_business, false) as TextView
            tv.text = mode.labelList3[i].labelName
            tv.setOnClickListener {
                cleanTextColor1()
                cleanTextColor2()
                cleanTextColor3()
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_them3)

                lableId = mode.labelList3[i].labelId
                lableName = mode.labelList3[i].labelName
                lableType = "2"
            }
            businessTvList.add(tv)
            fl_business.addView(tv)
        }
    }


    private fun cleanTextColor1() {
        for (i in 0 until skillTvList.size) {
            skillTvList[i].setTextColor(Color.parseColor("#ff333333"))
            skillTvList[i].setBackgroundResource(R.drawable.bg_gray3)
        }
    }

    private fun cleanTextColor2() {
        for (i in 0 until occupationTvList.size) {
            occupationTvList[i].setTextColor(Color.parseColor("#ff333333"))
            occupationTvList[i].setBackgroundResource(R.drawable.bg_gray3)
        }
    }

    private fun cleanTextColor3() {
        for (i in 0 until businessTvList.size) {
            businessTvList[i].setTextColor(Color.parseColor("#ff333333"))
            businessTvList[i].setBackgroundResource(R.drawable.bg_gray3)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}