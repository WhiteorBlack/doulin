package com.lixin.amuseadjacent.app.ui.mine.activity

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.CustomTagsDialog
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.model.LabelListModel
import com.lixin.amuseadjacent.app.ui.mine.request.LabelList_115116
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_add_label.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 添加标签
 * Created by Slingge on 2018/9/2.
 */
class AddLabelActivity : BaseActivity(), CustomTagsDialog.CustomTagsCallBack {

    private var flag = -1//1运动，2音乐，3美食，4电影，5书籍,6其他
    private var type = ""

    private var labelList = ArrayList<String>()//标签id
    private var otherLabelList = ArrayList<String>()//自定义标签

    private var labelTypeList = ArrayList<LabelListModel.labelListModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_label)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("添加标签")
        StatusBarWhiteColor()

        flag = intent.getIntExtra("flag", -1)

        tv_right.text = "保存"
        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener { v ->
            ProgressDialog.showDialog(this)
            LabelList_115116.addLabel(this, flag.toString(), labelList, otherLabelList)
        }

        ProgressDialog.showDialog(this)
        LabelList_115116.userInfo(flag.toString())


        val tv = LayoutInflater.from(this).inflate(
                R.layout.layout_flow_talent_type, ll_main, false) as TextView
        tv.text = "自定义"
        tv.setOnClickListener { v ->
            CustomTagsDialog.communityDialog(this, this)
        }
        AbStrUtil.setDrawableLeft(this, R.drawable.ic_add4, tv, 10)
        tv.setTextColor(resources.getColor(R.color.white))
        tv.setBackgroundResource(R.drawable.bg_gray3)
        fl_customLabel.addView(tv)
    }

    override fun tag(tag: String) {
        val tagTv = LayoutInflater.from(this).inflate(
                R.layout.layout_flow_talent_type, ll_main, false) as TextView

        tagTv.text = tag
        tagTv.setTextColor(resources.getColor(R.color.white))
        tagTv.setBackgroundResource(R.drawable.bg_bule3)
        otherLabelList.add(tag)
        tagTv.setOnClickListener { v ->
           if(otherLabelList.contains(tag)){//已经包含，移除之
               otherLabelList.remove(tag)
               tagTv.setTextColor(Color.parseColor("#333333"))
               tagTv.setBackgroundResource(R.drawable.bg_gray3)
           }else{
               otherLabelList.add(tag)
               tagTv.setTextColor(resources.getColor(R.color.white))
               tagTv.setBackgroundResource(R.drawable.bg_bule3)
           }
        }
        fl_customLabel.addView(tagTv)
    }


    @Subscribe
    fun onEvent(model: LabelListModel) {
        labelTypeList = model.labelList
        for (i in 0 until labelTypeList.size) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.text = labelTypeList[i].laberName

            if (labelTypeList[i].state == "1") {//已选中
                tv.setTextColor(resources.getColor(R.color.white))
                tv.setBackgroundResource(R.drawable.bg_bule3)

                labelList.add(labelTypeList[i].labelId)
            } else {
                tv.setTextColor(Color.parseColor("#333333"))
                tv.setBackgroundResource(R.drawable.bg_gray3)
            }

            tv.setOnClickListener { v ->
                CleatStat(tv, i)
            }
            fl_label.addView(tv)
        }


        for (i in 0 until model.otherList.size) {
            val tv = LayoutInflater.from(this).inflate(
                    R.layout.layout_flow_talent_type, ll_main, false) as TextView
            tv.text = model.otherList[i].laberName
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_bule3)
            fl_customLabel.addView(tv)

            otherLabelList.add( model.otherList[i].laberName)

            tv.setOnClickListener { v ->
                if(otherLabelList.contains( model.otherList[i].laberName)){//已经包含，移除之
                    otherLabelList.remove( model.otherList[i].laberName)
                    tv.setTextColor(Color.parseColor("#333333"))
                    tv.setBackgroundResource(R.drawable.bg_gray3)
                }else{
                    otherLabelList.add( model.otherList[i].laberName)
                    tv.setTextColor(resources.getColor(R.color.white))
                    tv.setBackgroundResource(R.drawable.bg_bule3)
                }
            }
        }


    }


    private fun CleatStat(tv: TextView, i: Int) {
        if (labelTypeList[i].state == "1") {//已选中
            tv.setTextColor(Color.parseColor("#333333"))
            tv.setBackgroundResource(R.drawable.bg_gray3)

            labelTypeList[i].state = "0"

            labelList.remove(labelTypeList[i].labelId)
        } else {
            tv.setTextColor(resources.getColor(R.color.white))
            tv.setBackgroundResource(R.drawable.bg_bule3)

            labelTypeList[i].state = "1"

            labelList.add(labelTypeList[i].labelId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CustomTagsDialog.dismiss()
        EventBus.getDefault().unregister(this)
    }

}