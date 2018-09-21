package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.message.adapter.FragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.mine.fragment.CouponMyFragment
import kotlinx.android.synthetic.main.activity_personal_home_page.*
import kotlinx.android.synthetic.main.include_basetop.*
import java.util.ArrayList

/**
 * Created by Slingge on 2018/9/3
 */
class CouponMyActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)
        init()
    }


    private fun init() {
        inittitle("我的优惠券")
        StatusBarWhiteColor()

        val type = intent.getIntExtra("type", 0)//type1 选择优惠券

        tv_right.visibility = View.VISIBLE
        tv_right.text = "优惠券说明"
        tv_right.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putInt("flag", 0)
            MyApplication.openActivity(this, WebViewActivity::class.java, bundle)
        }

        val tabList = ArrayList<String>()
        tabList.add("未使用")
        tabList.add("已使用")
        tabList.add("已过期")

        val list = ArrayList<Fragment>()
        for (i in 0..2) {
            val fragment = CouponMyFragment()
            val bundle = Bundle()
            bundle.putInt("flag", i)
            bundle.putInt("type", type)
            fragment.arguments = bundle
            list.add(fragment)
        }

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        tab.setupWithViewPager(viewPager)

    }


}