package com.lixin.amuseadjacent.app.ui.service.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.service.adapter.LaundryAdapter
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.ui.service.request.OfficialShopGoodsList_35
import com.lxkj.linxintechnologylibrary.app.util.ProgressDialogUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_laundry.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 洗衣洗鞋
 * Created by Slingge on 2018/8/31
 */
class LaundryFrament : BaseFragment() {

    private var laundryAdapter: LaundryAdapter? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var goodList = ArrayList<ShopGoodsModel.dataModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_laundry, container, false)
        EventBus.getDefault().register(this)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_clothes.layoutManager = gridLayoutManager

        laundryAdapter = LaundryAdapter(activity!!,goodList)
        rv_clothes.adapter = laundryAdapter


    }

    private fun init() {
        gridLayoutManager = GridLayoutManager(activity!!, 3)
    }

    @Subscribe
    fun onEvent(model: ShopGoodsModel) {
        if (goodList.isNotEmpty()) {
            goodList.clear()
            laundryAdapter!!.notifyDataSetChanged()
        }
        goodList.addAll(model.dataList)
        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        rv_clothes.layoutAnimation = controller
        laundryAdapter!!.notifyDataSetChanged()
        rv_clothes.scheduleLayoutAnimation()
    }

    //二级分类id
    @Subscribe
    fun onEvent(secondCategoryId: String) {
        ProgressDialog.showDialog(activity!!)
        OfficialShopGoodsList_35.ShopGoods("1", secondCategoryId, "")
    }


    override fun loadData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}