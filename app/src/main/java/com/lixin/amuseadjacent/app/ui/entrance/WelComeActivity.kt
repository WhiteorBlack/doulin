package com.lixin.amuseadjacent.app.ui.entrance

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.WindowManager
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.entrance.adapter.MyFragmentPagerAdapter
import com.lixin.amuseadjacent.app.ui.entrance.fagment.WelFragment0
import com.lixin.amuseadjacent.app.ui.entrance.fagment.WelFragment1
import com.lixin.amuseadjacent.app.ui.entrance.fagment.WelFragment2
import com.lixin.amuseadjacent.app.util.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_welcome.*
import java.util.ArrayList

/**
 * Created by Slingge on 2018/9/28
 */
class WelComeActivity : BaseActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_welcome)
        init()
    }

    private var flag: Boolean = false
    private fun init() {
        val list = ArrayList<Fragment>()

        val f0 = WelFragment0()
        list.add(f0)
        val f1 = WelFragment1()
        list.add(f1)
        val f2 = WelFragment2()
        list.add(f2)

        val adapter = MyFragmentPagerAdapter(supportFragmentManager, list)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager.SCROLL_STATE_DRAGGING -> flag = true
                    ViewPager.SCROLL_STATE_SETTLING -> flag = false
                    ViewPager.SCROLL_STATE_IDLE -> {
                        /**
                         * 判断是不是最后一页，同时是不是拖的状态
                         */
                        if (viewPager.currentItem == adapter.count - 1 && flag) {
                            SharedPreferencesUtil.putSharePre(this@WelComeActivity, "isFirst",true)
                            MyApplication.openActivity(this@WelComeActivity, SginInActivity::class.java)
                            finish()
                        }
                        flag = true
                    }
                }//拖的时候才进入下一页
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    indicator.setImageResource(R.drawable.ic_indicator1)
                } else if (position == 1) {
                    indicator.setImageResource(R.drawable.ic_indicator2)
                } else if (position == 2) {
                    indicator.setImageResource(R.drawable.ic_indicator3)
                }
            }

        })
    }


}