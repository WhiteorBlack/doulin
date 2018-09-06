package com.lixin.amuseadjacent.app.ui

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.find.fragment.FindFragment
import com.lixin.amuseadjacent.app.ui.message.fragment.MessageFragment
import com.lixin.amuseadjacent.app.ui.mine.fragment.MineFragment
import com.lixin.amuseadjacent.app.ui.service.fragment.ServiceFragment
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * */
class MainActivity : AppCompatActivity() {

    private var mFragment = Fragment()
    private var bFragment1: MessageFragment? = null
    private var bFragment2: FindFragment? = null
    private var bFragment3: ServiceFragment? = null
    private var bFragment4: MineFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        tab_1.isChecked = true
        bFragment1 = MessageFragment()
        switchFragment(bFragment1!!)

        RadioG_Bottem.setOnCheckedChangeListener { radioGroup, i -> selectStyle(i) }

    }


    //根据具体点击切换显示对应fragment
    private fun selectStyle(ID: Int) {
        when (ID) {
            R.id.tab_1 -> {
                if (Build.VERSION.SDK_INT > 19) {
                    StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
                }
                if (bFragment1 == null) {
                    bFragment1 = MessageFragment()
                }
                switchFragment(bFragment1!!)
            }
            R.id.tab_2 -> {
                if (Build.VERSION.SDK_INT > 19) {
                    StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
                }
                if (bFragment2 == null) {
                    bFragment2 = FindFragment()
                }
                switchFragment(bFragment2!!)
            }
            R.id.tab_3 -> {
                if (Build.VERSION.SDK_INT > 19) {
                    StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
                }
                if (bFragment3 == null) {
                    bFragment3 = ServiceFragment()
                }
                switchFragment(bFragment3!!)
            }
            R.id.tab_4 -> {
                if (Build.VERSION.SDK_INT > 19) {
                    StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorTheme))
                }

                if (bFragment4 == null) {
                    bFragment4 = MineFragment()
                }
                switchFragment(bFragment4!!)
            }
        }
    }


    /**
     * 选择加载的Fragment
     */
    private fun switchFragment(fragment: Fragment) {
        if (fragment !== mFragment) {
            val transaction = supportFragmentManager
                    .beginTransaction()
            if (!fragment.isAdded) { // 先判断是否被add过
                transaction.hide(mFragment).add(R.id.fragment, fragment).commit() // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mFragment).show(fragment).commit() // 隐藏当前的fragment，显示下一个
            }
            mFragment = fragment
        }
    }


    fun destroy() {
        finish()
    }

}
