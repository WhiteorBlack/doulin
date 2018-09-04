package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.mine.fragment.ViolationReportFragment
import kotlinx.android.synthetic.main.activity_talent.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.util.ArrayList

/**
 * 违规举报
 * Created by Slingge on 2018/9/4
 */
class ViolationReportActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talent)
        init()
    }


    private fun init() {
        inittitle("违规举报")
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE
        include_banner.visibility= View.GONE

        val tabList = ArrayList<String>()
        tabList.add("我的违规")
        tabList.add("我的举报")

        val list = ArrayList<Fragment>()
        for (i in 0..1) {
            val fragment = ViolationReportFragment()
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