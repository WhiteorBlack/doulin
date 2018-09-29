package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.dialog.QianDaoDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.SignCalendarAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.SginInModel
import com.lixin.amuseadjacent.app.ui.mine.request.SignIn_117118
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lixin.amuseadjacent.app.util.abLog
import com.lixin.amuseadjacent.app.util.getDateTime
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_qiaodao.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.DecimalFormat
import java.util.ArrayList

/**
 * Created by Slingge on 2018/9/2.
 */
class QianDaoActivity : BaseActivity(), View.OnClickListener {

    private var calendarAdapter: SignCalendarAdapter? = null
    private var dateList = ArrayList<String>()
    private var dayList = ArrayList<Int>()
    private var week = 0

    private var year = 0//单前年份
    private var month = 0//当前月份
    private var day = 0//当天

    private var decimalFormat: DecimalFormat? = null

    private var todaySign = "-1"///0今天未签到，1今天已签到


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qiaodao)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("签到")
        StatusBarWhiteColor()

        year = getDateTime.getYear()
        month = getDateTime.getMonth()
        day = getDateTime.getNowDay()

        iv_left.setOnClickListener(this)
        iv_rightt.setOnClickListener(this)
        tv_eeffect.setOnClickListener(this)
        tv_date.text = year.toString() + "-" + format(month)

        val linearLayout = GridLayoutManager(this, 7)
        recyclerView.layoutManager = linearLayout

        getSgin(year.toString() + "-" + format(month))
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
                getSgin(year.toString() + "-" + format(month))
            }
            R.id.iv_rightt -> {
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
                getSgin(year.toString() + "-" + format(month))
            }
            R.id.tv_eeffect -> {
                ProgressDialog.showDialog(this@QianDaoActivity)
                SignIn_117118.sgin(object : SignIn_117118.SginCallBack {
                    override fun sginScore(score: String) {
                        dateList.add(dateList.size - 1, getDateTime.ymd())
                        calendarAdapter = SignCalendarAdapter(this@QianDaoActivity, dayList, week, dateList, day)
                        recyclerView!!.adapter = calendarAdapter
                        QianDaoDialog.communityDialog(this@QianDaoActivity, score)
                    }
                })
            }
        }
    }

    @Subscribe
    fun onEvent(model: SginInModel) {
        tv_eeffect.text = "签到即可获得" + model.effectNum + "点影响力"
        todaySign = model.todaySign

        week = getDateTime.getWeek(year, month)
        dayList = getDateTime.getMonthDay(year, month)

        dateList = model.dataList
        calendarAdapter = SignCalendarAdapter(this, dayList, week, dateList, day)
        recyclerView!!.adapter = calendarAdapter
    }


    private fun format(i: Int): String {
        if (decimalFormat == null) {
            decimalFormat = DecimalFormat("00")
        }
        return decimalFormat!!.format(i)
    }

    private fun getSgin(date: String) {
        ProgressDialog.showDialog(this)
        SignIn_117118.getSginList(date)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        QianDaoDialog.builder = null
    }

}