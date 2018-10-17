package com.lixin.amuseadjacent.app.ui.service.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.service.adapter.ShopCarDetailsAdapter
import com.lixin.amuseadjacent.app.ui.service.model.DelCarModel
import com.lixin.amuseadjacent.app.ui.service.model.ShopCarModel
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.ui.service.request.ShopCar_12412537
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.DoubleCalculationUtil
import kotlinx.android.synthetic.main.activity_shop_car.*
import kotlinx.android.synthetic.main.include_basetop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 购物车
 * Created by Slingge on 2018/8/31
 */
class ShopCarActivity : BaseActivity(), View.OnClickListener, ShopCarDetailsAdapter.SelectCallBack
        , ShopCarDetailsAdapter.EditNumCallBack, ShopCarDetailsAdapter.DelCarCallBack {

    private var marketList = ArrayList<ShopCarModel.carModel>()//超市便利
    private var marketAdapter: ShopCarDetailsAdapter? = null
    private var clothesList = ArrayList<ShopCarModel.carModel>()//洗衣洗鞋
    private var clothesAdapter: ShopCarDetailsAdapter? = null
    private var fruitsList = ArrayList<ShopCarModel.carModel>()//新鲜果蔬
    private var fruitsAdapter: ShopCarDetailsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_car)
        EventBus.getDefault().register(this)
        init()
    }

    private fun init() {
        StatusBarWhiteColor()
        inittitle("购物车")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "编辑"
        tv_right.setOnClickListener(this)
        tv_del.setOnClickListener(this)

        rl_market.setOnClickListener(this)
        rl_clothes.setOnClickListener(this)
        rl_fruits.setOnClickListener(this)

        tv_settlement_market.setOnClickListener(this)
        tv_settlement_clothes.setOnClickListener(this)
        tv_settlement_fruits.setOnClickListener(this)

        ProgressDialog.showDialog(this)
        ShopCar_12412537.getCar()
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_right -> {
                if (tv_right.text == "编辑") {
                    marketAdapter!!.setEdite(true)
                    clothesAdapter!!.setEdite(true)
                    fruitsAdapter!!.setEdite(true)
                    tv_right.text = "完成"
                    tv_del.visibility = View.VISIBLE
                } else {
                    tv_right.text = "编辑"
                    marketAdapter!!.setEdite(false)
                    clothesAdapter!!.setEdite(false)
                    fruitsAdapter!!.setEdite(false)
                    tv_del.visibility = View.GONE
                }
            }
            R.id.tv_del -> {  //删除
                val idlist = ArrayList<String>()
                for (i in 0 until marketList.size) {
                    if (marketList[i].isSelect) {
                        idlist.add(marketList[i].cartId)
                    }
                }
                for (i in 0 until clothesList.size) {
                    if (clothesList[i].isSelect) {
                        idlist.add(clothesList[i].cartId)
                    }
                }
                for (i in 0 until fruitsList.size) {
                    if (fruitsList[i].isSelect) {
                        idlist.add(fruitsList[i].cartId)
                    }
                }

                DelCar(-1, 1, idlist)
            }
            R.id.rl_market -> {//超市便利
                if (marketList.isEmpty()) {
                    return
                }
                if (cb_market.isChecked) {
                    Calculation(0, false)
                } else {
                    Calculation(0, true)
                }
            }
            R.id.rl_clothes -> {//洗衣洗鞋
                if (clothesList.isEmpty()) {
                    return
                }
                if (cb_clothes.isChecked) {
                    Calculation(1, false)
                } else {
                    Calculation(1, true)
                }
            }
            R.id.rl_fruits -> {//新鲜果蔬
                if (fruitsList.isEmpty()) {
                    return
                }
                if (cb_fruits.isChecked) {
                    Calculation(2, false)
                } else {
                    Calculation(2, true)
                }
            }
            R.id.tv_settlement_market -> {//超市便利,结算
                if (AbStrUtil.tvTostr(tv_totalMoney_market) == "合计：￥0.0") {
                    return
                }
                ShiftingClause("2", marketList)
            }
            R.id.tv_settlement_clothes -> {//洗衣洗鞋,结算
                if (AbStrUtil.tvTostr(tv_totalMoney_clothes) == "合计：￥0.0") {
                    return
                }
                ShiftingClause("1", clothesList)
            }
            R.id.tv_settlement_fruits -> {//新鲜果蔬,结算
                if (AbStrUtil.tvTostr(tv_totalMoney_fruits) == "合计：￥0.0") {
                    return
                }
                ShiftingClause("0", fruitsList)
            }
        }
    }


    //适配器选中
    override fun select(flag: Int, i: Int, isSelect: Boolean) {
        if (flag == 0) {//超市便利
            marketList[i].isSelect = isSelect
        } else if (flag == 1) {//洗衣洗鞋
            clothesList[i].isSelect = isSelect
        } else if (flag == 2) {//新鲜果蔬
            fruitsList[i].isSelect = isSelect
        }

        Calculation(flag, null)
    }

    //适配器数量变化
    override fun num(flag: Int, i: Int, num: Int) {
        if (flag == 0) {//超市便利
            marketList[i].count = num.toString()
        } else if (flag == 1) {//洗衣洗鞋
            clothesList[i].count = num.toString()
        } else if (flag == 2) {//新鲜果蔬
            fruitsList[i].count = num.toString()
        }
        Calculation(flag, null)
    }

    //适配器删除
    override fun del(flag: Int, i: Int) {
        var carId = ""
        if (flag == 0) {//超市便利
            carId = marketList[i].cartId
        } else if (flag == 1) {//洗衣洗鞋
            carId = clothesList[i].cartId
        } else if (flag == 2) {//新鲜果蔬
            carId = fruitsList[i].cartId
        }
        val idList = ArrayList<String>()
        idList.add(carId)

        DelCar(flag, 0, idList)
    }


    //计算选中金额
    @SuppressLint("SetTextI18n")
    private fun Calculation(flag: Int, isAllselect: Boolean?) {
        var totalMoney = 0.0
        var isAllSelect = true
        if (flag == 0) {

            for (i in 0 until marketList.size) {
                if (isAllselect != null) {
                    marketList[i].isSelect = isAllselect
                }
                if (marketList[i].isSelect) {
                    totalMoney = DoubleCalculationUtil.add(totalMoney, DoubleCalculationUtil.mul(marketList[i].goodsPrice.toDouble(), marketList[i].count.toDouble()))
                } else {
                    isAllSelect = false
                }
            }
            if (marketList.isEmpty()) {
                isAllSelect = false
            }

            cb_market.isChecked = isAllSelect
            marketAdapter!!.notifyDataSetChanged()
            tv_totalMoney_market.text = "合计：￥$totalMoney"
        } else if (flag == 1) {
            for (i in 0 until clothesList.size) {
                if (isAllselect != null) {
                    clothesList[i].isSelect = isAllselect
                }
                if (clothesList[i].isSelect) {
                    totalMoney = DoubleCalculationUtil.add(totalMoney, DoubleCalculationUtil.mul(clothesList[i].goodsPrice.toDouble(), clothesList[i].count.toDouble()))
                } else {
                    isAllSelect = false
                }
            }
            if (clothesList.isEmpty()) {
                isAllSelect = false
            }

            cb_clothes.isChecked = isAllSelect
            clothesAdapter!!.notifyDataSetChanged()
            tv_totalMoney_clothes.text = "合计：￥$totalMoney"
        } else if (flag == 2) {

            for (i in 0 until fruitsList.size) {
                if (isAllselect != null) {
                    fruitsList[i].isSelect = isAllselect
                }
                if (fruitsList[i].isSelect) {
                    totalMoney = DoubleCalculationUtil.add(totalMoney, DoubleCalculationUtil.mul(fruitsList[i].goodsPrice.toDouble(), fruitsList[i].count.toDouble()))
                } else {
                    isAllSelect = false
                }
            }
            if (fruitsList.isEmpty()) {
                isAllSelect = false
            }

            cb_fruits.isChecked = isAllSelect
            fruitsAdapter!!.notifyDataSetChanged()
            tv_totalMoney_fruits.text = "合计：￥$totalMoney"
        }
    }


    //删除购物车,type 0适配器单个删除，1批量删除
    private fun DelCar(flag: Int, type: Int, carIdList: java.util.ArrayList<String>) {
        ProgressDialog.showDialog(this)
        val model = DelCarModel()
        model.objId = carIdList
        ShopCar_12412537.DelCar(model, object : ShopCar_12412537.DelCarCallback {
            override fun delCar() {
                if (flag == 0) {
                    RemoveCar(flag, type, carIdList[0], marketList)
                } else if (flag == 1) {
                    RemoveCar(flag, type, carIdList[0], clothesList)
                } else if (flag == 2) {
                    RemoveCar(flag, type, carIdList[0], fruitsList)
                } else {
                    RemoveCar(flag, type, "", null)//批量删除
                }
            }
        })
    }


    private fun RemoveCar(flag: Int, type: Int, id: String, carIdList: ArrayList<ShopCarModel.carModel>?) {
        if (type == 0) {
            for (i in 0 until carIdList!!.size) {
                if (carIdList[i].cartId == id) {
                    carIdList.removeAt(i)
                    break
                }
            }
        } else {
            var iterator = marketList.iterator()
            while (iterator.hasNext()) {
                if (iterator.next().isSelect) {
                    iterator.remove()
                }
            }
            iterator = clothesList.iterator()
            while (iterator.hasNext()) {
                if (iterator.next().isSelect) {
                    iterator.remove()
                }
            }

            iterator = fruitsList.iterator()
            while (iterator.hasNext()) {
                if (iterator.next().isSelect) {
                    iterator.remove()
                }
            }
        }
        marketAdapter!!.notifyDataSetChanged()
        clothesAdapter!!.notifyDataSetChanged()
        fruitsAdapter!!.notifyDataSetChanged()

        if (marketList.isEmpty()) {
            rl_market.visibility = View.GONE
            cl_market.visibility = View.GONE
        }
        if (clothesList.isEmpty()) {
            rl_clothes.visibility = View.GONE
            cl_clothes.visibility = View.GONE
        }
        if (fruitsList.isEmpty()) {
            rl_fruits.visibility = View.GONE
            cl_fruits.visibility = View.GONE
        }
        if (marketList.isEmpty() && clothesList.isEmpty() && fruitsList.isEmpty()) {
            rl_clear.visibility = View.VISIBLE
        }

        if (flag == -1) {
            Calculation(0, null)
            Calculation(1, null)
            Calculation(2, null)
        } else {
            Calculation(flag, null)
        }
    }


    //转换集合数据类型
    private fun ShiftingClause(type: String, list: ArrayList<ShopCarModel.carModel>) {
        val goodList = ArrayList<ShopGoodsModel.dataModel>()
        for (i in 0 until list.size) {
            if(list[i].isSelect){
                val model = ShopGoodsModel.dataModel()
                model.goodsNum = list[i].count.toInt()
                model.goodsId = list[i].goodsId

                model.goodsCuprice = list[i].goodsPrice
                model.goodsImg = list[i].goodsImage
                model.goodsName = list[i].goodsTitle
                model.goodsPrice = list[i].goodsPrice
                model.money = DoubleCalculationUtil.mul(list[i].goodsPrice.toDouble(), list[i].count.toDouble())
                goodList.add(model)
            }
        }
        val bundle = Bundle()
        bundle.putString("type", type)
        bundle.putSerializable("list", goodList)
        MyApplication.openActivity(this, SubmissionOrderActivity::class.java, bundle)
    }


    @Subscribe
    fun onEvent(model: ShopCarModel) {
        var linearLayoutManager1 = LinearLayoutManager(this)

        marketList = model.marketList
        if (marketList.isEmpty()) {
            rl_market.visibility = View.GONE
            cl_market.visibility = View.GONE
        }
        linearLayoutManager1.orientation = LinearLayoutManager.VERTICAL
        rv_market.layoutManager = linearLayoutManager1
        marketAdapter = ShopCarDetailsAdapter(this, 0, marketList, this, this, this)
        rv_market.adapter = marketAdapter

        clothesList = model.clothesList
        if (clothesList.isEmpty()) {
            rl_clothes.visibility = View.GONE
            cl_clothes.visibility = View.GONE
        }
        linearLayoutManager1 = LinearLayoutManager(this)
        linearLayoutManager1.orientation = LinearLayoutManager.VERTICAL
        rv_clothes.layoutManager = linearLayoutManager1
        clothesAdapter = ShopCarDetailsAdapter(this, 1, clothesList, this, this, this)
        rv_clothes.adapter = clothesAdapter


        fruitsList = model.fruitsList
        if (fruitsList.isEmpty()) {
            rl_fruits.visibility = View.GONE
            cl_fruits.visibility = View.GONE
        }
        linearLayoutManager1 = LinearLayoutManager(this)
        linearLayoutManager1.orientation = LinearLayoutManager.VERTICAL
        rv_fruits.layoutManager = linearLayoutManager1
        fruitsAdapter = ShopCarDetailsAdapter(this, 2, fruitsList, this, this, this)
        rv_fruits.adapter = fruitsAdapter

        cb_market.isChecked = true
        Calculation(0, true)
        cb_clothes.isChecked = true
        Calculation(1, true)
        cb_fruits.isChecked = true
        Calculation(2, true)

        if (marketList.isEmpty() && clothesList.isEmpty() && fruitsList.isEmpty()) {
            rl_clear.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}