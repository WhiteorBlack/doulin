package com.lixin.amuseadjacent.app.ui.message.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.message.fragment.MailFragment
import kotlinx.android.synthetic.main.activity_mail.*
import java.util.ArrayList

/**
 * Created by Slingge on 2018/8/16
 */
class PersonalHomePageActivity:BaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_home_page)
        init()
    }


    private fun init(){
        inittitle("个人主页")
        StatusBarWhiteColor()

        val tabList = ArrayList<String>()
        tabList.add("资料")
        tabList.add("动态")
        tabList.add("达人")
        tabList.add("互动")

        val list = ArrayList<Fragment>()
        for (i in 0..3) {
            val fragment = MailFragment()
            val bundle = Bundle()
            bundle.putInt("flag", i)
            fragment.arguments = bundle
            list.add(fragment)
        }

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
//        viewPager.adapter = adapter
//        tab.setupWithViewPager(viewPager)


    }

}