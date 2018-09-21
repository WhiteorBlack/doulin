package com.lixin.amuseadjacent.app.ui.service.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.activity.AddressActivity
import com.lixin.amuseadjacent.app.ui.mine.activity.CouponMyActivity
import com.lixin.amuseadjacent.app.ui.mine.model.AddressModel
import com.lixin.amuseadjacent.app.ui.mine.model.CouponMyModel
import com.lixin.amuseadjacent.app.ui.service.adapter.SubmissionOrderAdapter
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.ui.service.model.SubmissionModel
import com.lixin.amuseadjacent.app.ui.service.request.SubmissionOrder_38
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lixin.amuseadjacent.app.util.DoubleCalculationUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_submission_order.*

/**
 * 提交订单
 * Created by Slingge on 2018/9/1
 */
class SubmissionOrderActivity : BaseActivity(), View.OnClickListener {

    private var carList = ArrayList<ShopGoodsModel.dataModel>()
    private var totalMoney = 0.0//总支付金额
    private var actualMoney = 0.0  //减去优惠券实际支付

    private var addressId = ""
    private var couponId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission_order)
        init()
    }


    private fun init() {
        inittitle("提交订单")
        StatusBarWhiteColor()

        carList = intent.getSerializableExtra("list") as ArrayList<ShopGoodsModel.dataModel>
        carNum()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_comment.layoutManager = linearLayoutManager
        val orderAdapter = SubmissionOrderAdapter(this, carList)
        rv_comment.adapter = orderAdapter

        iv_address.setOnClickListener(this)
        tv_submission.setOnClickListener(this)
        tv_coupon.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_submission -> {
                if (TextUtils.isEmpty(addressId)) {
                    ToastUtil.showToast("请选择收货地址")
                    return
                }
                ProgressDialog.showDialog(this)

                val msg = AbStrUtil.etTostr(et_note)

                val mode = SubmissionModel()
                mode.type = intent.getStringExtra("type")//0新鲜果蔬 1洗衣洗鞋 2超市便利
                mode.allprice = totalMoney.toString()
                mode.payprice = actualMoney.toString()
                mode.securitiesid = couponId
                mode.addressId = addressId
                mode.message = msg
                for (i in 0 until carList.size) {
                    val goodMode = SubmissionModel.goodsModel()
                    goodMode.count = carList[i].goodsNum.toString()
                    goodMode.goodsId = carList[i].goodsId
                    mode.goodsList.add(goodMode)
                }
                SubmissionOrder_38.submission(this, mode)
            }
            R.id.tv_coupon -> {
                val bundle = Bundle()
                bundle.putInt("type", 1)
                MyApplication.openActivityForResult(this, CouponMyActivity::class.java, bundle, 0)
            }
            R.id.iv_address -> {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivityForResult(this, AddressActivity::class.java, bundle, 1)
            }
        }
    }


    //购物车数量及价格
    private fun carNum() {
        var num = 0
        totalMoney = 0.0
        for (i in 0 until carList.size) {
            num += carList[i].goodsNum
            totalMoney = DoubleCalculationUtil.add(carList[i].money, totalMoney)
        }
        tv_num.text = num.toString() + "件"

        actualMoney = totalMoney
        tv_totalMoney.text = "￥$totalMoney"
        tv_actualpay.text = "￥$actualMoney"
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 1) {//地址
            val model = data.getSerializableExtra("model") as AddressModel.addModel
            tv_name.text = model.username
            tv_phone.text = model.userPhone
            tv_address.text = model.city + model.address
            addressId = model.addressId
        } else if (requestCode == 0) {//优惠券
            val model = data.getSerializableExtra("model") as CouponMyModel.couponModel
            couponId = model.securitiesid
            actualMoney = DoubleCalculationUtil.sub(totalMoney, model.securitiesPrice.toDouble())
            if (actualMoney < 0) {
                actualMoney = 0.0
            }
            tv_actualpay.text = "￥$actualMoney"
            tv_coupon.text = "￥" + model.securitiesPrice
        }

    }

}
 