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
import com.lixin.amuseadjacent.app.ui.mine.adapter.AddressAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.AddressModel
import com.lixin.amuseadjacent.app.ui.mine.request.Address_140141142143
import kotlinx.android.synthetic.main.xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 我的收货地址
 * Created by Slingge on 2018/9/4
 */
class AddressActivity : BaseActivity() {

    private var addressAdapter: AddressAdapter? = null
    private var addList = ArrayList<AddressModel.addModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    private var flag=0//0正常，1选择地址

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xrecyclerview)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("我的收货地址")
        StatusBarWhiteColor()

        tv_bottom2.visibility = View.VISIBLE
        tv_bottom2.setOnClickListener { v ->
            //新增收货地址
            val bundle = Bundle()
            bundle.putInt("flag", 0)
            MyApplication.openActivity(this, EditAddressActivity::class.java, bundle)
        }

        flag=intent.getIntExtra("flag",0)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecyclerview.layoutManager = linearLayoutManager

        addressAdapter = AddressAdapter(this, addList,flag)
        xrecyclerview.adapter = addressAdapter

        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
        xrecyclerview.layoutAnimation = controller
        addressAdapter!!.notifyDataSetChanged()
        xrecyclerview.scheduleLayoutAnimation()
        xrecyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                nowPage = 1
                onRefresh = 1
                if (addList.isNotEmpty()) {
                    addList.clear()
                    addressAdapter!!.notifyDataSetChanged()
                }
                Address_140141142143.addressList(nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    xrecyclerview.noMoreLoading()
                    return
                }
                onRefresh = 2
                Address_140141142143.addressList(nowPage)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        ProgressDialog.showDialog(this)
        if (addList.isNotEmpty()) {
            addList.clear()
            addressAdapter!!.notifyDataSetChanged()
        }
        Address_140141142143.addressList(nowPage)
    }


    @Subscribe
    fun onEvent(model: AddressModel) {
        totalPage = model.totalPage
        addList.addAll(model.dataList)

        if (onRefresh == 1) {
            xrecyclerview.refreshComplete()
        } else if (onRefresh == 2) {
            xrecyclerview.loadMoreComplete()
        }
        addressAdapter!!.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}