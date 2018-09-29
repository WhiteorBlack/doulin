package com.lixin.amuseadjacent.app.util

import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.TimeZone


/**
 * 获取要选择的时间
 * Created by Administrator on 2016/10/14 0014.
 */

object getDateTime {

    /**
     * 获取格式年月日
     */
    //获取当前时间
    fun ymd(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val curDate = Date(System.currentTimeMillis())
        return formatter.format(curDate)
    }


    /**
     * 获取格式年月日时分秒
     */
    //获取当前时间
    val ymdhms: String
        get() {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val curDate = Date(System.currentTimeMillis())
            return formatter.format(curDate)
        }


    /**
     * 获取格式年月日时分秒
     */
    //获取当前时间
    fun getYMD(l: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val curDate = Date(l)
        return formatter.format(curDate)
    }


    /**
     * 获取格式月日时分秒
     */
    fun getmdhms(): String {
        val formatter = SimpleDateFormat("MM-dd HH:mm:ss")
        val curDate = Date(System.currentTimeMillis())
        return formatter.format(curDate)
    }

    /**
     * 获取分钟
     */
    val min: List<String>
        get() {
            val list = ArrayList<String>()
            for (i in 0..59) {
                if ((i.toString() + "").length == 1) {
                    list.add("0$i")
                } else {
                    list.add(i.toString() + "")
                }
            }
            return list
        }

    /**
     * 获取未来num天日期
     */
    fun getSevendate(num: Int): List<String> {
        val dates = ArrayList<String>()
        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("GMT+8:00")
        var year = c.get(Calendar.YEAR)// 获取当前年份
        var mMonth = c.get(Calendar.MONTH) + 1// 获取当前月份
        val day = MaxDayFromDay_OF_MONTH(year, mMonth)//当月最大日期
        var mDay = c.get(Calendar.DAY_OF_MONTH)// 获取当前日份的日期号码

        for (i in 0 until num) {
            if (mDay > day) {
                if (mMonth == 12) {
                    mMonth = 1
                    year = year + 1
                } else {
                    mMonth = mMonth + 1
                }
                mDay = 1
            }

            val date = year.toString() + "/" + mMonth + "/" + mDay
            dates.add(date)
            mDay++
        }
        return dates
    }


    fun getYear(): Int {
        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("GMT+8:00")
        val year = c.get(Calendar.YEAR)// 获取当前年份
        return year
    }

    fun getMonth(): Int {
        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("GMT+8:00")
        val mMonth = c.get(Calendar.MONTH) + 1// 获取当前月份
        return mMonth
    }

    fun getNowDay(): Int {
        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("GMT+8:00")
        val day = c.get(Calendar.DAY_OF_MONTH)// 获取当日期
        return day
    }

    fun getDay(): Int {
        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("GMT+8:00")
        val year = c.get(Calendar.YEAR)// 获取当前年份
        val mMonth = c.get(Calendar.MONTH) + 1// 获取当前月份
        val day = MaxDayFromDay_OF_MONTH(year, mMonth)//当月最大日期
        return day
    }

    fun getHour(): Int {
        val formatter = SimpleDateFormat("HH")
        val curDate = Date(System.currentTimeMillis())
        return formatter.format(curDate).toInt()
    }

    fun getMinute(): Int {
        val formatter = SimpleDateFormat("mm")
        val curDate = Date(System.currentTimeMillis())
        return formatter.format(curDate).toInt()
    }

    /**
     * 得到当年当月的最大日期
     */
    fun MaxDayFromDay_OF_MONTH(years: Int, months: Int): Int {
        val time = Calendar.getInstance()
        time.clear()
        time.set(Calendar.YEAR, years)
        time.set(Calendar.MONTH, months - 1)//注意,Calendar对象默认一月为0
        return time.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    /**
     * 返回当月天数加上
     */
    fun getMonthDay(year: Int, month: Int): ArrayList<Int> {
        val list = ArrayList<Int>()

        val maxDay = MaxDayFromDay_OF_MONTH(year, month)
        for (i in 0 until maxDay + getWeek(year, month) - 1) {
            list.add(i + 1)
        }
        return list
    }


    /**
     * 当月第一天周几
     */
    fun getWeek(year: Int, month: Int): Int {
        val format = SimpleDateFormat("yyyy.MM.dd")
        val c = Calendar.getInstance()
        try {
            c.time = format.parse(year.toString() + "." + month + "." + "01")
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        var week = 0
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            week = 7
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            week = 1
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            week = 2
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            week = 3
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            week = 4
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            week = 5
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            week = 6
        }
        return week
    }


    fun getMonthMaxDay(year: Int, month: Int): List<String> {

        val list = ArrayList<String>()
        val maxDay = MaxDayFromDay_OF_MONTH(year, month)
        for (i in 1..maxDay) {
            list.add(i.toString())
        }
        return list
    }


    /**
     * 获可选择的营业时间
     */
    fun getSevenTime(st: Int, en: Int): List<String> {
        var st = st
        val times = ArrayList<String>()

        for (i in 0..99) {
            if (st == en) {
                break
            }
            times.add(st.toString() + ":" + "00")
            times.add(st.toString() + ":" + "30")
            st++
        }
        return times
    }


    /**
     * 获取当天可选时间
     */
    fun getBusinessTime(num: Int): List<String> {
        val times = ArrayList<String>()
        val mCalendar = Calendar.getInstance()
        val time = System.currentTimeMillis()
        mCalendar.timeInMillis = time
        var mHour = 0
        if (num == 0) {//当天的日期选择当前时间一小时之后
            mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
        }
        for (i in 0 until 24 - mHour) {
            if (mHour + 1 + i == 24) {
                times.add("00")
            } else {
                times.add((mHour + 1 + i).toString() + "")
            }
        }
        return times
    }


    private var decimalFormat: DecimalFormat? = null
    fun secondTotime(second: Int): String {
        var h = 0
        var min = 0
        var s = 0

        h = second / 3600
        min = (second % 3600) / 60
        s = (second % 3600) % 60


        if (decimalFormat == null) {
            decimalFormat = DecimalFormat("00")
        }

        return decimalFormat!!.format(h) + ":" + decimalFormat!!.format(min) + ":" + decimalFormat!!.format(s)
    }


}
