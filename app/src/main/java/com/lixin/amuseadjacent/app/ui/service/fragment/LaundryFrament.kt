package com.lixin.amuseadjacent.app.ui.service.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseFragment
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.service.adapter.LaundryAdapter
import com.lixin.amuseadjacent.app.ui.service.model.CarModel
import com.lixin.amuseadjacent.app.ui.service.model.ShopCarModel
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.ui.service.model.TempIdModel
import com.lixin.amuseadjacent.app.ui.service.request.OfficialShopGoodsList_35
import com.lixin.amuseadjacent.app.util.DoubleCalculationUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 洗衣洗鞋
 * Created by Slingge on 2018/8/31
 */
class LaundryFrament : BaseFragment(), LaundryAdapter.AddShopCar {

    private var laundryAdapter: LaundryAdapter? = null
    private var goodList = ArrayList<ShopGoodsModel.dataModel>()

    private var tempList = ArrayList<ShopGoodsModel.dataModel>()
    private var secondCategoryId = ""//当前二级id

    private var rv_clothes: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_laundry, container, false)
        EventBus.getDefault().register(this)
        init()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_clothes = view!!.findViewById(R.id.rv_clothes)
        val gridLayoutManager = GridLayoutManager(activity!!, 3)
        rv_clothes!!.layoutManager = gridLayoutManager

        laundryAdapter = LaundryAdapter(activity!!, goodList)
        laundryAdapter!!.setAddShopCar(this)
        rv_clothes!!.adapter = laundryAdapter
    }

    private fun init() {

    }


    //添加到购物车
    override fun addCar(position: Int) {
        if (goodList[position].isSelect) {
            goodList[position].goodsNum = goodList[position].goodsNum + 1
            goodList[position].money = DoubleCalculationUtil.mul(goodList[position].goodsNum.toDouble(), goodList[position].UnitPrice)
            laundryAdapter!!.notifyItemChanged(position)
            val model = CarModel.editModel()
            model.goodModel = goodList[position]
            model.flag = 1
            model.position = position
            EventBus.getDefault().post(model)
            return
        }
        goodList[position].isSelect = true
        goodList[position].isAdd = true
        goodList[position].goodsNum = 1
        laundryAdapter!!.notifyItemChanged(position)

        val model = CarModel.editModel()
        model.goodModel = goodList[position]
        model.flag = 0
        model.position = position
        EventBus.getDefault().post(model)
    }

    //购物车数量增加
    fun plusCar(goodId: String, num: Int, money: Double) {
        for (i in 0 until goodList.size) {
            if (goodList[i].goodsId == goodId) {
                goodList[i].goodsNum = num
                goodList[i].isSelect = true
                goodList[i].money = money
                laundryAdapter!!.notifyItemChanged(i)
                return
            }
        }
    }

    //购物车数量减少
    fun reduceCar(goodId: String, num: Int, money: Double) {
        for (i in 0 until goodList.size) {
            if (goodList[i].goodsId == goodId) {
                goodList[i].goodsNum = num
                goodList[i].isSelect = true
                goodList[i].money = money
                laundryAdapter!!.notifyItemChanged(i)
                return
            }
        }
    }

    //购物车删除
    fun delCar(goodId: String) {
        for (i in 0 until goodList.size) {
            if (goodId == goodList[i].goodsId) {
                goodList[i].isSelect = false
                goodList[i].goodsNum = 0
                laundryAdapter!!.notifyItemChanged(i)
                return
            }
        }
    }

    //清空
    fun clearCar() {
        for (i in 0 until goodList.size) {
            if (goodList[i].isSelect) {
                goodList[i].isSelect = false
                goodList[i].goodsNum = 0
            }
        }
        laundryAdapter!!.notifyDataSetChanged()
    }


    //二级分类id
    @Subscribe
    fun onEvent(tempModel: TempIdModel) {
        this.secondCategoryId = tempModel.firstId + tempModel.secondId
        /*if (goodMap.isNotEmpty()) {
            for (i in 0 until goodMap.size) {
                goodMap[i].secondCategoryId = secondCategoryId
                setDate(goodMap[i].good)
                return
            }
        }*/
        ProgressDialog.showDialog(activity!!)
        OfficialShopGoodsList_35.ShopGoods("1", tempModel.secondId, "", object : OfficialShopGoodsList_35.ShopGoodsCallback {
            override fun GoodList(List: java.util.ArrayList<ShopGoodsModel.dataModel>) {
                /*  val mapModel = LaundryMapModel()//储存数据，不在二次获取，保存选中状态
                  mapModel.secondCategoryId = secondCategoryId
                  mapModel.good = List
                  goodMap.add(mapModel)*/
                setDate(List)
            }
        })
    }


    private fun setDate(List: ArrayList<ShopGoodsModel.dataModel>) {
        if (goodList.isNotEmpty()) {
            goodList.clear()
//            laundryAdapter!!.notifyDataSetChanged()
        }
        tempList = List
        goodList.addAll(List)
        if (!shopCartList.isEmpty() && !goodList.isEmpty()) {
            for (i in 0 until shopCartList.size) {
                for (j in 0 until goodList.size) {
                    if (TextUtils.equals(shopCartList[i].goodsId, goodList[j].goodsId)) {
                        goodList[j].goodsNum = shopCartList[i].count.toInt()
                        goodList[j].isSelect=true
                        break
                    }
                }
            }
        }

        val controller = AnimationUtils.loadLayoutAnimation(activity!!, R.anim.layout_animation_from_bottom)
        rv_clothes!!.layoutAnimation = controller
        laundryAdapter!!.notifyDataSetChanged()
        rv_clothes!!.scheduleLayoutAnimation()
    }

    var shopCartList = ArrayList<ShopCarModel.carModel>()


    fun setShopCarData(model: ShopCarModel){
        shopCartList.addAll(model.clothesList)
    }

    override fun loadData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}