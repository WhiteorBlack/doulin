package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.TransactionAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.BalanceDetailsModel
import com.lixin.amuseadjacent.app.ui.mine.request.Wallet_119121
import kotlinx.android.synthetic.main.include_basetop.*
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 交易明细
 * Created by Slingge on 2018/9/2.
 */
class TransactionDetailsActivity : BaseActivity() {

    private var detailsList = ArrayList<BalanceDetailsModel.detailsModel>()
    private var transactionAdapter: TransactionAdapter? = null

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("交易明细")
        StatusBarWhiteColor()

        iv_right.visibility = View.VISIBLE
        iv_right.setImageResource(R.drawable.ic_description)
        iv_right.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putInt("flag", 5)
            MyApplication.openActivity(this, WebViewActivity::class.java,bundle)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        transactionAdapter = TransactionAdapter(this, detailsList)
        xrecyclerview.adapter = transactionAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        transactionAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (!detailsList.isEmpty()) {
                    detailsList.clear()
                    transactionAdapter!!.notifyDataSetChanged()
                }
                Wallet_119121.BalanceDetails(nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage > totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                Wallet_119121.BalanceDetails(nowPage)
            }
        })

        ProgressDialog.showDialog(this)
        Wallet_119121.BalanceDetails(nowPage)
    }


    @Subscribe
    fun onEvent(model: BalanceDetailsModel) {
        totalPage = model.totalPage

        detailsList.addAll(model.dataList)

        if (totalPage <= 1) {
            if (detailsList.isEmpty()) {
                xrecyclerview.setNullData(this)
            }
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }

        transactionAdapter!!.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}