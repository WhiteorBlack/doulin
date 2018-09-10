package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.service.adapter.ShopLeftAdapter
import com.lixin.amuseadjacent.app.ui.service.adapter.ShopRightAdapter
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lxkj.huaihuatransit.app.util.ControlWidthHeight
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.include_basetop.*

/**
 * 店铺
 * Created by Slingge on 2018/8/30
 */
class OfficialShopActivity : BaseActivity(), View.OnClickListener {

    private var shopLeftAdapter: ShopLeftAdapter? = null

    private var rightAdapter: ShopRightAdapter? = null
    private var mSuspensionHeight: Int = 0//悬浮头高度
    private var mCurrentPosition = 0
    private var linearLayoutManager2:LinearLayoutManager?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        init()
    }


    private fun init() {
        inittitle("店铺")
        StatusBarWhiteColor()
        view_staus.visibility = View.GONE

        tv_right.visibility = View.VISIBLE
        tv_right.text = "详情"
        tv_right.setOnClickListener(this)

        tv_settlement.setOnClickListener(this)

        var linearLayoutManager1 = LinearLayoutManager(this)
        linearLayoutManager1.orientation = LinearLayoutManager.VERTICAL
        rv_left.layoutManager = linearLayoutManager1

        shopLeftAdapter = ShopLeftAdapter(this)
        rv_left.adapter = shopLeftAdapter

        var controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        rv_left.layoutAnimation = controller
        shopLeftAdapter!!.notifyDataSetChanged()
        rv_left.scheduleLayoutAnimation()

        rv_left.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_left) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                ToastUtil.showToast(i.toString())
                if (i < 0) {
                    return
                }
                smoothMoveToPosition(linearLayoutManager2!!, i)
                shopLeftAdapter!!.setSelect(i)
            }
        })

        linearLayoutManager2 = LinearLayoutManager(this)
        linearLayoutManager2!!.orientation = LinearLayoutManager.VERTICAL
        rv_right.layoutManager = linearLayoutManager2

        rightAdapter = ShopRightAdapter(this)
        rv_right.adapter = rightAdapter
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_right -> {
                MyApplication.openActivity(this, OfficialShopDetailsActivity::class.java)
            }
            R.id.tv_settlement -> {
                MyApplication.openActivity(this, SubmissionOrderActivity::class.java)
            }
        }
    }


    //平滑滚动到指定位置
    private fun smoothMoveToPosition(mLinearLayoutManager: LinearLayoutManager, n: Int) {

        val firstItem = mLinearLayoutManager.findFirstVisibleItemPosition()
        val lastItem = mLinearLayoutManager.findLastVisibleItemPosition()
        if (n <= firstItem) {
            rv_right.smoothScrollToPosition(n)
        } else if (n <= lastItem) {
            val top = rv_right.getChildAt(n - firstItem).top
            rv_right.smoothScrollBy(0, top)
        } else {
            mLinearLayoutManager.scrollToPositionWithOffset(n, 0)
            mLinearLayoutManager.stackFromEnd = true
        }
    }


}