package com.lixin.amuseadjacent.app.ui.find.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.find.fragment.RedManFragment
import com.lixin.amuseadjacent.app.ui.find.fragment.TalentFragment
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_redman_list.*
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

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)

    }


}