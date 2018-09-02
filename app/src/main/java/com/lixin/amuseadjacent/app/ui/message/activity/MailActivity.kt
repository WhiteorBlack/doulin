package com.lixin.amuseadjacent.app.ui.message.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.message.fragment.MailFragment
import kotlinx.android.synthetic.main.activity_mail.*
import java.util.ArrayList

/**
 * Created by Slingge on 2018/8/15
 */
class MailActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()

        tv_back.setOnClickListener { v -> finish() }

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
}