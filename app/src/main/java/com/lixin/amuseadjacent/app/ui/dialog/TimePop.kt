package com.lixin.amuseadjacent.app.ui.dialog

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView

import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.util.GetDateTimeUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.weigan.loopview.LoopView
import java.util.*

/**
 * 选择日期，时分
 * Created by Slingge on 2018/1/17 0017.
 */

class TimePop(val context: Context, val wheelViewCallBack: WheelViewCallBack2) : PopupWindow(context) {

    private var msxOneYear = -1//0只显示今年


    fun TimePop(msxOneYear: Int) {
        this.msxOneYear = msxOneYear
    }


    internal var position = 0
    internal var position2 = 0
    internal var position3 = 0
    internal var position4 = 0//时
    internal var position5 = 0//分

    private val yearList = ArrayList<String>()
    private val monthList = ArrayList<String>()
    private val dayList = ArrayList<String>()
    private val hourList = ArrayList<String>()
    private val minList = ArrayList<String>()

    private var month = 0//当前月份
    private var day = 0//当天

    interface WheelViewCallBack2 {
        fun position(position1: String, position2: String, position3: String, position4: String, position5: String)
    }


    fun init() {
        val v = LayoutInflater.from(context).inflate(R.layout.popup_address, null)
        val loopview = v.findViewById<View>(R.id.loopView) as LoopView
        val loopview2 = v.findViewById<View>(R.id.loopView2) as LoopView
        val loopview3 = v.findViewById<View>(R.id.loopView3) as LoopView
        val loopview4 = v.findViewById<View>(R.id.loopView4) as LoopView
        loopview4.visibility = View.VISIBLE
        val loopview5 = v.findViewById<View>(R.id.loopView5) as LoopView
        loopview5.visibility = View.VISIBLE
        loopview.setTextSize(16f)
        loopview2.setTextSize(16f)
        loopview3.setTextSize(16f)
        loopview4.setTextSize(16f)
        loopview5.setTextSize(16f)

        v.findViewById<View>(R.id.year).visibility = View.VISIBLE
        v.findViewById<View>(R.id.month).visibility = View.VISIBLE
        v.findViewById<View>(R.id.day).visibility = View.VISIBLE
        v.findViewById<View>(R.id.hour).visibility = View.VISIBLE
        v.findViewById<View>(R.id.min).visibility = View.VISIBLE

        //设置是否循环播放
        //  loopView.setNotLoop();
        //滚动监听
        //设置原始数据

        if (yearList.isEmpty()) {
            getYear()
            getMonth()
            val c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)// 获取当前年份
            month = c.get(Calendar.MONTH) + 1// 获取当前月份
            day = c.get(Calendar.DAY_OF_MONTH)
            getDay(year.toString(), month.toString())
            getHour()
            getMin()
        }

        loopview.setNotLoop()
        loopview.setItems(yearList)
        position = yearList.size - 1
        loopview.setCurrentPosition(position)

        loopview2.setNotLoop()
        loopview2.setItems(monthList)
        position2 = month - 1
        loopview2.setCurrentPosition(position2)

        loopview3.setNotLoop()
        loopview3.setItems(dayList)
        position3 = day - 1
        loopview3.setCurrentPosition(position3)

        loopview4.setNotLoop()
        loopview4.setItems(hourList)
        loopview4.setInitPosition(0)

        loopview5.setNotLoop()
        loopview5.setItems(minList)
        loopview5.setInitPosition(0)
        wheelViewCallBack.position(yearList[yearList.size - 1], monthList[month - 1], dayList[day - 1], hourList[0], minList[0])

        loopview.setListener { index ->
            position = index

            position2 = 0
            loopview2.setItems(monthList)
            loopview2.setInitPosition(0)

            position3 = 0
            getDay(yearList[position], monthList[position2])
            loopview3.setItems(dayList)
            loopview3.setInitPosition(position3)

            position4 = 0
            loopview4.setCurrentPosition(0)
            position5 = 0
            loopview5.setCurrentPosition(0)

            wheelViewCallBack.position(yearList[position], monthList[position2], dayList[position3], hourList[position4], minList[position5])
        }
        loopview2.setListener { index ->
            position2 = index

            position3 = 0
            getDay(yearList[position], monthList[position2])
            loopview3.setItems(dayList)
            loopview3.setInitPosition(0)

            position4 = 0
            loopview4.setCurrentPosition(0)
            position5 = 0
            loopview5.setCurrentPosition(0)

            wheelViewCallBack.position(yearList[position], monthList[position2], dayList[position3], hourList[position4], minList[position5])
        }
        loopview3.setListener { index ->
            position3 = index

            position4 = 0
            loopview4.setCurrentPosition(0)
            position5 = 0
            loopview5.setCurrentPosition(0)

            wheelViewCallBack.position(yearList[position], monthList[position2], dayList[position3], hourList[position4], minList[position5])
        }

        loopview4.setListener { index ->
            position4 = index
            position5 = 0
            loopview5.setCurrentPosition(0)

            wheelViewCallBack.position(yearList[position], monthList[position2], dayList[position3], hourList[position4], minList[position5])
        }

        loopview5.setListener { index ->
            position5 = index
            wheelViewCallBack.position(yearList[position], monthList[position2], dayList[position3], hourList[position4], minList[position5])
        }

        val tv_enter = v.findViewById<View>(R.id.tv_enter) as TextView
        tv_enter.setOnClickListener { v1 -> this@TimePop.dismiss() }

        //设置初始位置
        this.contentView = v
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体的高
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.animationStyle = android.R.style.Animation_InputMethod
        this.isFocusable = true
        //		this.setOutsideTouchable(true);
        this.setBackgroundDrawable(BitmapDrawable())
        this.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    }


    private fun getYear() {
        yearList.clear()
        if(msxOneYear==0){
            val c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)// 获取当前年份
            yearList.add(year.toString())
        }else{
            for (i in 1900..2018) {
                yearList.add(i.toString())
            }
        }

    }

    private fun getMonth() {
        monthList.clear()
        for (i in 1..12) {
            if (i < 10) {
                monthList.add("0" + i.toString())
            } else {
                monthList.add(i.toString())
            }
        }
    }

    private fun getDay(year: String, month: String) {
        val max = GetDateTimeUtil.MaxDayFromDay_OF_MONTH(year.toInt(), month.toInt())
        dayList.clear()
        for (i in 1..max) {
            if (i < 10) {
                dayList.add("0" + i.toString())
            } else {
                dayList.add(i.toString())
            }
        }
    }

    private fun getHour() {
        hourList.clear()
        for (i in 0..23) {
            if (i < 10) {
                hourList.add("0" + i.toString())
            } else {
                hourList.add(i.toString())
            }
        }
    }

    private fun getMin() {
        minList.clear()
        for (i in 0..59) {
            if (i < 10) {
                minList.add("0" + i.toString())
            } else {
                minList.add(i.toString())
            }
        }
    }

}
