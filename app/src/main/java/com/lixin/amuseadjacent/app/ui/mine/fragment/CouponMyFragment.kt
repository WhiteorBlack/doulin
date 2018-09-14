package com.lixin.amuseadjacent.app.ui.mine.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.CouponMyAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.CouponMyModel
import com.lixin.amuseadjacent.app.ui.mine.request.CouponMy_122
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 我的优惠券
 * Created by Slingge on 2018/9/3
 */
class CouponMyFragment : BaseFragment() {

    private var flag = -1//0未使用，1已使用，2已过期

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    private var linearLayoutManager: LinearLayoutManager? = null
    private var couponAdapter: CouponMyAdapter? = null
    private var couponList = ArrayList<CouponMyModel.couponModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.xrecyclerview, container, false)
        EventBus.getDefault().register(this)
        init()
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        include.visibility = View.GONE
        xrecyclerview.layoutManager = linearLayoutManager

        couponAdapter = CouponMyAdapter(activity!!, couponList, flag)
        xrecyclerview.adapter = couponAdapter

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                if (couponList.isNotEmpty()) {
                    couponList.clear()
                    couponAdapter!!.notifyDataSetChanged()
                }
                onRefresh = 1
                CouponMy_122.coupon(flag, nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                CouponMy_122.coupon(flag, nowPage)
            }
        })
    }


    private fun init() {
        flag = arguments!!.getInt("flag")

        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager!!.orientation = LinearLayoutManager.VERTICAL

    }

    override fun loadData() {
        ProgressDialog.showDialog(activity!!)
        if (couponList.isNotEmpty()) {
            couponList.clear()
            couponAdapter!!.notifyDataSetChanged()
        }
        CouponMy_122.coupon(flag, nowPage)
    }


    @Subscribe
    fun onEvent(model: CouponMyModel) {
        totalPage = model.totalPage

        couponList.addAll(model.dataList)

        if (totalPage <= 1) {
            if (couponList.isEmpty()) {
                xrecyclerview.setNullDataFragment(activity)
            } else {
                xrecyclerview.noMoreLoading()
            }
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }

        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        couponAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}