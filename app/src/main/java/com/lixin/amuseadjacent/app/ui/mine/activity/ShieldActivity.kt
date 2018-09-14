package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.ShieldAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.ShieldModel
import com.lixin.amuseadjacent.app.ui.mine.request.Shield_151152
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 我的屏蔽
 * Created by Slingge on 2018/9/4
 */
class ShieldActivity : BaseActivity() {

    private var shieldAdapter: ShieldAdapter? = null
    private var shieldList = ArrayList<ShieldModel.shieldModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        EventBus.getDefault().register(this)
        init()
    }

    override fun onStart() {
        super.onStart()
        ProgressDialog.showDialog(this)
        Shield_151152.shidle(nowPage)
    }


    private fun init() {
        inittitle("我的屏蔽")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        shieldAdapter = ShieldAdapter(this, shieldList)
        xrecyclerview.adapter = shieldAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        shieldAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()

        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (shieldList.isNotEmpty()) {
                    shieldList.clear()
                    shieldAdapter!!.notifyDataSetChanged()
                }
                Shield_151152.shidle(nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                Shield_151152.shidle(nowPage)
            }
        })
    }


    @Subscribe
    fun onEvent(model: ShieldModel) {
        totalPage = model.totalPage
        shieldList.addAll(model.dataList)

        if (totalPage <= 1) {
            if (shieldList.isEmpty()) {
                xrecyclerview.setNullData(this)
            } else {
                xrecyclerview.noMoreLoading()
            }
        }

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }
        shieldAdapter!!.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}