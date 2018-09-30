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
import com.lixin.amuseadjacent.app.ui.mine.activity.*
import com.lixin.amuseadjacent.app.ui.mine.activity.order.OrderActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.MineAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.UserInfoModel
import com.lixin.amuseadjacent.app.ui.mine.request.UserInfo_19
import com.lixin.amuseadjacent.app.ui.service.activity.ShopCarActivity
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.StatusBarUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2018/8/15
 */
class MineFragment : BaseFragment(), View.OnClickListener {

    private var mineAdapter: MineAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        EventBus.getDefault().register(this)
        init()
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.setStutaViewHeight(activity, view_staus2)
            StatusBarUtil.setStutaViewHeight(activity, view_staus)
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setColorNoTranslucent(activity, resources.getColor(R.color.colorTheme))
        }

        val gridLayoutManager = GridLayoutManager(activity, 4)
        val gridLayoutManager2 = GridLayoutManager(activity, 4)
        rv_used.layoutManager = gridLayoutManager
        rv_more.layoutManager = gridLayoutManager2

        mineAdapter = MineAdapter(activity!!, 0)
        rv_used.adapter = mineAdapter
        mineAdapter = MineAdapter(activity!!, 1)
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
                        MyApplication.openActivity(activity, CouponMyActivity::class.java)
                    }
                    2 -> {//购物车
                        MyApplication.openActivity(activity, ShopCarActivity::class.java)
                    }
                    3 -> {//订单
                        MyApplication.openActivity(activity, OrderActivity::class.java)
                    }
                    4 -> {//收藏
                        MyApplication.openActivity(activity, CollectionActivity::class.java)
                    }
                    5 -> {//实名认证
                        if (userModel!!.authentication == "1") {// 0未认证 1认证审核中 2认证成功 3审核拒绝
                            ToastUtil.showToast("您的认证正在审核..")
                            return
                        }
                        if (userModel!!.authentication == "2") {// 0未认证 1认证审核中 2认证成功 3审核拒绝
                            ToastUtil.showToast("您已认证完成")
                            return
                        }
                        MyApplication.openActivity(activity, RealNameAuthenticationActivity::class.java)
                    }
                }
            }
        })

        rv_more.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_more) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                if (i < 0) {
                    return
                }
                when (i) {
                    0 -> {//帮助
                        MyApplication.openActivity(activity, HelpActivity::class.java)
                    }
                    1 -> {//邀请好友
                        MyApplication.openActivity(activity, InvitingFriendsAcivity::class.java)
                    }
                    2 -> {//更换社区
                        MyApplication.openActivity(activity, ReplaceCommunityActivity::class.java)
                    }
                    3 -> {//意见反馈
                        MyApplication.openActivity(activity, FeedbackActivity::class.java)
                    }
                    4 -> {//关于逗邻
                        val bundle = Bundle()
                        bundle.putInt("flag", 2)
                        MyApplication.openActivity(activity, WebViewActivity::class.java, bundle)
                    }
                }
            }
        })


        iv_heaser.setOnClickListener(this)
        iv_setting.setOnClickListener(this)
        iv_code.setOnClickListener(this)
        tv_effect.setOnClickListener(this)
        tv_qiandao.setOnClickListener(this)

        tv_dynamic.setOnClickListener(this)
        tv_follow.setOnClickListener(this)
        tv_fans.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        UserInfo_19.userInfo(activity!!)
    }


    private fun init() {

    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_heaser -> {//个人中心
                val bundle = Bundle()
                bundle.putString("auid", StaticUtil.uid)
                MyApplication.openActivity(activity, PersonalHomePageActivity::class.java, bundle)
            }
            R.id.iv_code -> {//我的二维码
                MyApplication.openActivity(activity, MyQRActivity::class.java)
            }
            R.id.iv_setting -> {//设置
                MyApplication.openActivity(activity, SetUpActivity::class.java)
            }
            R.id.tv_qiandao -> {//签到
                MyApplication.openActivity(activity, QianDaoActivity::class.java)
            }
            R.id.tv_effect -> {//影响力
                MyApplication.openActivity(activity, EffectCommunityActivity::class.java)
            }
            R.id.tv_dynamic -> {//动态
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                bundle.putString("auid", StaticUtil.uid)
                bundle.putInt("position", 1)
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


    private var userModel: UserInfoModel? = null
    @Subscribe
    fun onEvent(model: UserInfoModel) {
        userModel = model
        StaticUtil.headerUrl = model.icon
        StaticUtil.nickName = model.nickname
        StaticUtil.shareurl = model.shareurl
        StaticUtil.inviteCode = model.inviteCode

        ImageLoader.getInstance().displayImage(model.icon, iv_heaser)
        tv_id.text = model.nickname
        tv_effect.text = "影响力" + model.effectNum

        tv_follow.text = "关注" + model.attenNum
        tv_dynamic.text = "动态" + model.dynamicNum
        tv_fans.text = "粉丝" + model.fansNum
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(activity)
    }

}