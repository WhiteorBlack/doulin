package com.lixin.amuseadjacent.app.ui.message.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.WindowManager
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.message.callBack.MailCallBack
import com.lixin.amuseadjacent.app.ui.message.fragment.MailFragment
import kotlinx.android.synthetic.main.activity_mail.*

/**
 * Created by Slingge on 2018/8/15
 */
class MailActivity : BaseActivity(), View.OnClickListener, MailCallBack {

    private var mFragment = Fragment()

    private var fragment0: MailFragment? = null
    private var fragment1: MailFragment? = null
    private var fragment2: MailFragment? = null

    private var flag = 0
    private var num0 = "0"
    private var num1 = "0"
    private var num2 = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)
        this.window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()

        tv_back.setOnClickListener { v -> finish() }

        tv_friend.setOnClickListener(this)
        tv_follow.setOnClickListener(this)
        tv_fans.setOnClickListener(this)


        fragment0 = MailFragment()
        val bundle = Bundle()
        bundle.putInt("flag", 0)
        fragment0!!.arguments = bundle
        fragment0!!.setMailCall(this)

        //个人中心跳转
        if (intent != null) {
            flag = intent.getIntExtra("flag", 0)
            select(flag)
        } else {
            switchFragment(fragment0!!)
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


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_follow -> {
                flag = 1
                select(1)
            }
            R.id.tv_friend -> {
                flag = 0
                select(0)
            }
            R.id.tv_fans -> {
                flag = 2
                select(2)
            }
        }
    }

    override fun num(num: String) {
        if (flag == 0) {
            num0 = num
            tv_back.text = "好友（$num0）"
        } else if (flag == 1) {
            num1 = num
            tv_back.text = "关注（$num1）"
        } else if (flag == 2) {
            num2 = num
            tv_back.text = "粉丝（$num2）"
        }
    }

    private fun select(i: Int) {

        when (i) {
            0 -> {
//                tv_back.text = "好友（$num0）"
                fragment0 = MailFragment()
                fragment0!!.setMailCall(this)
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                fragment0!!.arguments = bundle

                switchFragment(fragment0!!)

                tv_follow.setTextColor(Color.parseColor("#999999"))
                tv_friend.setTextColor(resources.getColor(R.color.colorTheme))
                tv_fans.setTextColor(Color.parseColor("#999999"))

                line_follow.visibility = View.INVISIBLE
                line_friend.visibility = View.VISIBLE
                line_fans.visibility = View.INVISIBLE
            }
            1 -> {
//                tv_back.text = "关注（$num1）"
                fragment1 = MailFragment()
                fragment1!!.setMailCall(this)
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                fragment1!!.arguments = bundle

                switchFragment(fragment1!!)

                tv_follow.setTextColor(resources.getColor(R.color.colorTheme))
                tv_friend.setTextColor(Color.parseColor("#999999"))
                tv_fans.setTextColor(Color.parseColor("#999999"))

                line_follow.visibility = View.VISIBLE
                line_friend.visibility = View.INVISIBLE
                line_fans.visibility = View.INVISIBLE
            }
            2 -> {
//                tv_back.text = "粉丝（$num2）"
                fragment2 = MailFragment()
                fragment2!!.setMailCall(this)
                val bundle = Bundle()
                bundle.putInt("flag", 2)
                fragment2!!.arguments = bundle

                switchFragment(fragment2!!)

                tv_follow.setTextColor(Color.parseColor("#999999"))
                tv_friend.setTextColor(Color.parseColor("#999999"))
                tv_fans.setTextColor(resources.getColor(R.color.colorTheme))

                line_follow.visibility = View.INVISIBLE
                line_friend.visibility = View.INVISIBLE
                line_fans.visibility = View.VISIBLE
            }
        }
    }


}