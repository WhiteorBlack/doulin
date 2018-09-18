package com.lixin.amuseadjacent.app.ui.find.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.fragment.RedManFragment
import com.lixin.amuseadjacent.app.ui.find.fragment.TalentFragment
import com.lixin.amuseadjacent.app.ui.find.model.RedmanModel
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_redman_list.*
import kotlinx.android.synthetic.main.fragment_redman_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 红人榜
 * Created by Slingge on 2018/8/30
 */
class RedManListActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redman_list)
        init()
    }


    private fun init() {
        inittitle("红人榜")
        StatusBarWhiteColor()

        val tabList = ArrayList<String>()
        tabList.add("日榜")
        tabList.add("月榜")

        val list = ArrayList<Fragment>()
        for (i in 0..1) {
            val fragment = RedManFragment()
            val bundle = Bundle()
            bundle.putInt("flag", i)
            fragment.arguments = bundle
            list.add(fragment)
        }

        tv_day.setOnClickListener { v ->
            tv_day.setTextColor(resources.getColor(R.color.colorTheme))
            line_day.visibility = View.VISIBLE

            tv_month.setTextColor(Color.parseColor("#999999"))
            line_month.visibility = View.INVISIBLE

            viewPager.currentItem = 0
        }

        tv_month.setOnClickListener { v ->
            tv_month.setTextColor(resources.getColor(R.color.colorTheme))
            line_month.visibility = View.VISIBLE

            tv_day.setTextColor(Color.parseColor("#999999"))
            line_day.visibility = View.INVISIBLE

            viewPager.currentItem = 1
        }

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)
    }



}