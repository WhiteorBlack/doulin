package com.lixin.amuseadjacent.app.ui.mine.fragment

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.mine.activity.MyQRActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.MineAdapter
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Created by Slingge on 2018/8/15
 */
class MineFragment : BaseFragment(), View.OnClickListener {


    private var mineAdapter: MineAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        init()
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (Build.VERSION.SDK_INT > 19) {
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(activity, view_staus)
            StatusBarUtil.transparentStatusBar(activity)
            StatusBarBlackWordUtil.StatusBarLightMode(activity)
        }

        val gridLayoutManager = GridLayoutManager(activity, 4)
        val gridLayoutManage2r = GridLayoutManager(activity, 4)
        rv_used.layoutManager = gridLayoutManager
        rv_more.layoutManager = gridLayoutManage2r

        mineAdapter = MineAdapter(activity!!, 0)
        rv_used.adapter = mineAdapter
        mineAdapter = MineAdapter(activity!!, 0)
        rv_more.adapter = mineAdapter


        iv_heaser.setOnClickListener(this)
        iv_setting.setOnClickListener(this)
        iv_code.setOnClickListener(this)
    }

    private fun init() {

    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_heaser -> {//个人中心
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivity(activity, PersonalHomePageActivity::class.java, bundle)
            }
            R.id.iv_code -> {//我的二维码
                MyApplication.openActivity(activity, MyQRActivity::class.java)
            }
            R.id.iv_setting -> {//设置

            }
        }
    }


    override fun loadData() {

    }


}