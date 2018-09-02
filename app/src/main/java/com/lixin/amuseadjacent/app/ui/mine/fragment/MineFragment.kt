package com.lixin.amuseadjacent.app.ui.mine.fragment

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.message.activity.MailActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.MyQRActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.PersonalHomePageActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.QianDaoActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.WalletActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.MineAdapter
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lixin.amuseadjacent.app.util.StatusBarBlackWordUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
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

        rv_used.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_used) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                if (i < 0) {
                    return
                }
                when (i) {
                    0 -> {//钱包
                        MyApplication.openActivity(activity, WalletActivity::class.java)
                    }
                    1 -> {//优惠券
                        MyApplication.openActivity(activity, WalletActivity::class.java)
                    }
                    2 -> {//购物车
                        MyApplication.openActivity(activity, WalletActivity::class.java)
                    }
                    3-> {//订单
                        MyApplication.openActivity(activity, WalletActivity::class.java)
                    }
                    4-> {//收藏
                        MyApplication.openActivity(activity, WalletActivity::class.java)
                    }
                    5 -> {//实名认证
                        MyApplication.openActivity(activity, WalletActivity::class.java)
                    }

                }
            }
        })

        rv_more.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_more) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                ToastUtil.showToast(i.toString())
                if (i < 0) {
                    return
                }
                when (i) {
                    0 -> {//钱包
//                        MyApplication.openActivity(activity, WalletActivity::class.java)
                    }
                }
            }
        })


        iv_heaser.setOnClickListener(this)
        iv_setting.setOnClickListener(this)
        iv_code.setOnClickListener(this)

        tv_qiandao.setOnClickListener(this)

        tv_dynamic.setOnClickListener(this)
        tv_follow.setOnClickListener(this)
        tv_fans.setOnClickListener(this)
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
            R.id.tv_qiandao -> {//签到
                MyApplication.openActivity(activity, QianDaoActivity::class.java)
            }
            R.id.tv_dynamic -> {//动态
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivity(activity, PersonalHomePageActivity::class.java, bundle)
            }
            R.id.tv_follow -> {//关注
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivity(activity, MailActivity::class.java, bundle)
            }
            R.id.tv_fans -> {//粉丝
                val bundle = Bundle()
                bundle.putInt("flag", 2)
                MyApplication.openActivity(activity, MailActivity::class.java, bundle)
            }
        }
    }


    override fun loadData() {

    }


}