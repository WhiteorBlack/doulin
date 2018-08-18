package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.mine.fragment.DataFragment
import com.lixin.amuseadjacent.app.ui.mine.fragment.DesignerFragment
import com.lixin.amuseadjacent.app.ui.mine.fragment.DynamicFragment
import com.lixin.amuseadjacent.app.ui.mine.fragment.InteractionFragment
import kotlinx.android.synthetic.main.activity_personal_home_page.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.util.ArrayList

/**
 * Created by Slingge on 2018/8/16
 */
class PersonalHomePageActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_home_page)
        init()
    }


    private fun init() {
        inittitle("个人主页")
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE

        val tabList = ArrayList<String>()
        tabList.add("资料")
        tabList.add("动态")
        tabList.add("达人")
        tabList.add("互动")

        val list = ArrayList<Fragment>()
        val fragment = DataFragment()
        list.add(fragment)

        val fragment2 = DynamicFragment()
        list.add(fragment2)

        val fragment3 = DesignerFragment()
        list.add(fragment3)

        val fragment4 = InteractionFragment()
        list.add(fragment4)


        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)
    }

}