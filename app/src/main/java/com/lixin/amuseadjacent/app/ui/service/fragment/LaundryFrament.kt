package com.lixin.amuseadjacent.app.ui.service.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.service.activity.SubmissionOrderActivity
import com.lixin.amuseadjacent.app.ui.service.adapter.LaundryAdapter
import com.lixin.amuseadjacent.app.ui.service.model.CarModel
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.ui.service.request.OfficialShopGoodsList_35
import com.lixin.amuseadjacent.app.util.DoubleCalculationUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_laundry.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 洗衣洗鞋
 * Created by Slingge on 2018/8/31
 */
class LaundryFrament : BaseFragment(), LaundryAdapter.AddShopCar {

    private var laundryAdapter: LaundryAdapter? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var goodList = ArrayList<ShopGoodsModel.dataModel>()
    private   var carList = ArrayList<ShopGoodsModel.dataModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_laundry, container, false)
        EventBus.getDefault().register(this)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_clothes.layoutManager = gridLayoutManager

        laundryAdapter = LaundryAdapter(activity!!, goodList, this)
        rv_clothes.adapter = laundryAdapter
    }

    private fun init() {
        gridLayoutManager = GridLayoutManager(activity!!, 3)
    }


    //添加到购物车
    override fun addCar(position: Int) {
        if (goodList[position].isSelect) {
            return
        }
        goodList[position].isSelect = true
        goodList[position].goodsNum = 1
        laundryAdapter!!.notifyItemChanged(position)
        carList.add(goodList[position])

        val model=CarModel.editModel()
        model.goodModel=carList[carList.size-1]
        model.flag=0
        model.position=position
        EventBus.getDefault().post(model)
    }

    //购物车数量增加
    fun plusCar(position: Int, num: Int, money: Double) {
        goodList[position].goodsNum = num
        laundryAdapter!!.notifyItemChanged(position)

        carList[position].goodsNum = num
        carList[position].money = money
    }

    //购物车数量减少
    fun reduceCar(position: Int, num: Int, money: Double) {
        goodList[position].goodsNum = num
        goodList[position].money = money
        laundryAdapter!!.notifyItemChanged(position)

        carList[position].goodsNum = num
        carList[position].money = money
    }

    //购物车删除
    fun delCar(position: Int) {
        goodList[position].isSelect = false
        goodList[position].goodsNum = 0
        laundryAdapter!!.notifyItemChanged(position)

        carList.removeAt(position)
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