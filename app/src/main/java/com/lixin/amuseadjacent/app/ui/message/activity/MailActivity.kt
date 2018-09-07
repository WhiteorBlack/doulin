package com.lixin.amuseadjacent.app.ui.message.activity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.message.fragment.MailFragment
import com.lixin.amuseadjacent.app.util.TabLayoutUtil
import kotlinx.android.synthetic.main.activity_mail.*
import java.util.ArrayList

/**
 * Created by Slingge on 2018/8/15
 */
class MailActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()

        tv_back.setOnClickListener { v -> finish() }

        tv_friend.setOnClickListener(this)
        tv_follow.setOnClickListener(this)
        tv_fans.setOnClickListener(this)

        val tabList = ArrayList<String>()
        tabList.add("好友")
        tabList.add("关注")
        tabList.add("粉丝")

        val list = ArrayList<Fragment>()
        for (i in 0..2) {
            val fragment = MailFragment()
            val bundle = Bundle()
            bundle.putInt("flag", i)
            fragment.arguments = bundle
            list.add(fragment)
        }

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)
        TabLayoutUtil.reflex(tab)

        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0) {
                    tv_back.text = "好友（）"

                } else if (tab.position == 1) {
                    tv_back.text = "关注（）"
                } else if (tab.position == 2) {
                    tv_back.text = "粉丝（）"
                }
            }
        })

        if (intent != null) {
            viewPager.currentItem = intent.getIntExtra("flag", 0)

        }
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_follow -> {
                tv_follow.setTextColor(resources.getColor(R.color.colorTheme))
                tv_friend.setTextColor(Color.parseColor("#999999"))
                tv_fans.setTextColor(Color.parseColor("#999999"))

                line_follow.visibility = View.VISIBLE
                line_friend.visibility = View.GONE
                line_fans.visibility = View.GONE

                viewPager.currentItem = 0
            }
            R.id.tv_friend -> {
                tv_follow.setTextColor(Color.parseColor("#999999"))
                tv_friend.setTextColor(resources.getColor(R.color.colorTheme))
                tv_fans.setTextColor(Color.parseColor("#999999"))

                line_follow.visibility = View.GONE
                line_friend.visibility = View.VISIBLE
                line_fans.visibility = View.GONE

                viewPager.currentItem = 1
            }
            R.id.tv_fans -> {
                tv_follow.setTextColor(Color.parseColor("#999999"))
                tv_friend.setTextColor(Color.parseColor("#999999"))
                tv_fans.setTextColor(resources.getColor(R.color.colorTheme))

                line_follow.visibility = View.GONE
                line_friend.visibility = View.GONE
                line_fans.visibility = View.VISIBLE

                viewPager.currentItem = 2
            }
        }
    }


}