package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.QianDaoDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.SignCalendarAdapter
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lixin.amuseadjacent.app.util.abLog
import com.lixin.amuseadjacent.app.util.getDateTime
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_qiaodao.*
import java.text.DecimalFormat
import java.util.ArrayList

/**
 * Created by Slingge on 2018/9/2.
 */
class QianDaoActivity : BaseActivity(), View.OnClickListener {

    private var calendarAdapter: SignCalendarAdapter? = null
    private var dayList = ArrayList<Int>()
    private var week = 0

    private var year = 0//单前年份
    private var month = 0//当前月份

    private var decimalFormat: DecimalFormat? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qiaodao)
        init()
    }


    private fun init() {
        inittitle("签到")
        StatusBarWhiteColor()

        year = getDateTime.getYear()
        month = getDateTime.getMonth()

        iv_left.setOnClickListener(this)
        iv_rightt.setOnClickListener(this)
        tv_date.text = year.toString() + "-" + format(month)

        val linearLayout = GridLayoutManager(this, 7)
        recyclerView.layoutManager = linearLayout

        recyclerView.addOnItemTouchListener(object : RecyclerItemTouchListener(recyclerView) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                if (i < 0) {
                    return
                }
                abLog.e("actiivty", i.toString())
                calendarAdapter!!.setFlag(i)
                QianDaoDialog.communityDialog(this@QianDaoActivity)
            }
        })

        Refresh()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_left -> {
                if (month == 1) {
                    month = 12
                    year--
                } else {
                    month--
                }
                tv_date.text = year.toString() + "-" + format(month)

                Refresh()
            }
            R.id.iv_rightt -> {
                ToastUtil.showToast("右")
                if (year < getDateTime.getYear()) {
                    if (month == 12) {
                        month = 1
                        year++
                    } else {
                        month++
                    }
                } else if (year >= getDateTime.getYear()) {
                    if (month <= getDateTime.getMonth() - 1) {
                        if (month == 12) {
                            month = 1
                            year++
                        } else {
                            month++
                        }
                    } else {
                        return
                    }
                }

                tv_date.text = year.toString() + "-" + format(month)
                Refresh()
            }
        }
    }


    private fun Refresh() {

        week = getDateTime.getWeek(year, month)
        dayList = getDateTime.getMonthDay(year, month)

        calendarAdapter = SignCalendarAdapter(this, dayList, week)
        recyclerView!!.adapter = calendarAdapter


    }

    private fun format(i: Int): String {
        if (decimalFormat == null) {
            decimalFormat = DecimalFormat("00")
        }
        return decimalFormat!!.format(i)
    }

    override fun onDestroy() {
        super.onDestroy()
        QianDaoDialog.builder = null
    }

}