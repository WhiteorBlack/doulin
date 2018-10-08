package com.lixin.amuseadjacent.app.ui.service.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import com.example.xrecyclerview.XRecyclerView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.service.adapter.OfficialShopEvaluateAdapter
import com.lixin.amuseadjacent.app.ui.service.model.OfficialShopDetailsModel
import com.lixin.amuseadjacent.app.ui.service.request.OfficialShopDetails_34
import kotlinx.android.synthetic.main.activity_official_shop_details.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 官方店铺详情
 * Created by Slingge on 2018/8/31
 */
class OfficialShopDetailsActivity : BaseActivity() {

    private var commentAdapter: OfficialShopEvaluateAdapter? = null
    private var commentList = ArrayList<OfficialShopDetailsModel.dataModel>()

    private var nowPage = 1
    private var totalPage = 1
    private var onRefresh = 0

    private var flag = 0//0新鲜果蔬 1洗衣洗鞋 2超市便利


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_official_shop_details)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("")
        StatusBarWhiteColor()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_comment.layoutManager = linearLayoutManager
        rv_comment.setPullRefreshEnabled(false)
        rv_comment.isFocusable = false

        commentAdapter = OfficialShopEvaluateAdapter(this, commentList)
        rv_comment.adapter = commentAdapter

        rv_comment.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                if (commentList.isNotEmpty()) {
                    commentList.clear()
                    commentAdapter!!.notifyDataSetChanged()
                }
                onRefresh = 1
                OfficialShopDetails_34.OfficialShopDetails(flag,nowPage)
            }

            override fun onLoadMore() {
                nowPage++
                if (nowPage >= totalPage) {
                    rv_comment.noMoreLoading()
                    return
                }
                onRefresh = 2
                OfficialShopDetails_34.OfficialShopDetails(flag,nowPage)
            }
        })

        flag=intent.getIntExtra("flag",0)

        ProgressDialog.showDialog(this)
        OfficialShopDetails_34.OfficialShopDetails(flag,nowPage)
    }

    @Subscribe
    fun onEvent(model: OfficialShopDetailsModel) {
        commentList.addAll(model.dataList)
        totalPage = model.totalPage

        if (onRefresh == 1) {
            rv_comment.refreshComplete()
        } else if (onRefresh == 2) {
            rv_comment.loadMoreComplete()
        }

        if (nowPage == 1) {
            val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
            rv_comment.layoutAnimation = controller
            commentAdapter!!.notifyDataSetChanged()
            rv_comment.scheduleLayoutAnimation()
        } else {
            commentAdapter!!.notifyDataSetChanged()
        }

        if (nowPage == 1) {
            inittitle(model.`object`.shopName)
            tv_time.text = model.`object`.shopTime
            tv_phone.text = model.`object`.shopPhone
            ratingBar.rating = model.`object`.shopStar.toFloat()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}