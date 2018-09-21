package com.lixin.amuseadjacent.app.ui.mine.activity.order

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.dialog.ProgressDialog
import com.lixin.amuseadjacent.app.ui.mine.adapter.ImageAdapter
import com.lixin.amuseadjacent.app.ui.mine.adapter.OrderCommodityAdapter
import com.lixin.amuseadjacent.app.ui.mine.model.MyOrderModel
import com.lixin.amuseadjacent.app.ui.mine.model.OrderDetailsModel
import com.lixin.amuseadjacent.app.ui.mine.request.MyOrder_144155
import com.lixin.amuseadjacent.app.ui.service.activity.PaymentActivity
import com.lixin.amuseadjacent.app.util.DoubleCalculationUtil
import com.lixin.amuseadjacent.app.util.StaticUtil
import kotlinx.android.synthetic.main.activity_order_details.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 订单详情
 * 默认为待付款
 * Created by Slingge on 2018/9/3
 */
class OrderDetailsActivity : BaseActivity(), View.OnClickListener {

    private var orderNum = ""
    private var orderAdapter: OrderCommodityAdapter? = null
    private var orderList = ArrayList<MyOrderModel.orderModel>()

    private var orderState = ""
    private var position = -1
    private var totalPay = ""//实付支付金额

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        EventBus.getDefault().register(this)
        init()
    }


    private fun init() {
        inittitle("订单")
        StatusBarWhiteColor()

        orderNum = intent.getStringExtra("num")
        position = intent.getIntExtra("position", -1)

        tv_pay.setOnClickListener(this)
        tv_refund.setOnClickListener(this)
        tv_again.setOnClickListener(this)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_comment.layoutManager = linearLayoutManager

        orderAdapter = OrderCommodityAdapter(this, position, null, orderList)
        rv_comment.adapter = orderAdapter

        ProgressDialog.showDialog(this)
        MyOrder_144155.orderDetails(orderNum)
    }


    @Subscribe
    fun onEvent(model: OrderDetailsModel) {
        tv_name.text = "收货人：" + model.username
        tv_phone.text = model.userPhone
        tv_address.text = "地址：" + model.city + model.address
        tv_total.text = model.oderAllPrice
        tv_coupon.text = model.securitiesPrice
        tv_actualPay.text = "实付：￥" + model.oderPayPrice

        totalPay = model.oderPayPrice

        var num = 0//集合商品数量
        for (i in 0 until model.orderCommodity.size) {
            num = DoubleCalculationUtil.add(model.orderCommodity[i].commodityBuyNum.toDouble(), num.toDouble()).toInt()
        }
        tv_num.text = "共" + num + "件商品"
        tv_note.text = model.message
        tv_orderNum.text = model.orderNum

        orderList.addAll(model.orderCommodity)
        orderAdapter!!.notifyDataSetChanged()

        if (model.orderState != "1") {
            placeTime.visibility = View.VISIBLE//下单时间
            tv_placeTime.visibility = View.VISIBLE
            payType.visibility = View.VISIBLE//支付方式
            tv_payType.visibility = View.VISIBLE


            tv_placeTime.text = model.adtime
            if (model.payType == "0") {//支付方式,0零钱,1支付宝,2微信 3银行卡
                tv_payType.text = "零钱支付"
            } else if (model.payType == "1") {
                tv_payType.text = "支付宝支付"
            } else if (model.payType == "2") {
                tv_payType.text = "微信支付"
            } else if (model.payType == "3") {
                tv_payType.text = "银行卡支付"
            }
            tv_placeTime.text = model.adtime

            if (model.orderState == "12") {
                return
            }
            payTime.visibility = View.VISIBLE//支付时间
            tv_payTime.visibility = View.VISIBLE
            tv_payTime.text = model.payTime
        }

        //1待付款,2待送货,3待收货,4待取货,5清洗中,6待归还,7归还中,8退款中,9已退款,10待评价,11已完成 12已取消
        if (model.orderState == "10" || model.orderState == "11") {
            collectTime.visibility = View.VISIBLE//收货时间
            tv_collectTime.visibility = View.VISIBLE
            tv_collectTime.text = model.endTime
        }

        if (model.orderState == "8" || model.orderState == "9") {
            takeTime.visibility = View.VISIBLE//退款审核时间
            takeTime.text = "退款审核时间"
            tv_takeTime.visibility = View.VISIBLE
            tv_takeTime.text = model.refundShenTime

            tv_refundInfo.visibility = View.VISIBLE
            rv_refund.visibility = View.VISIBLE
            tv_refundInfo.text = model.reason
            if (model.refundPics.isNotEmpty()) {
                val linearLayoutManager = GridLayoutManager(this, 4)
                rv_refund.layoutManager = linearLayoutManager
                val adapter = ImageAdapter(this, model.refundPics)
                rv_refund.adapter = adapter
            }

            if (model.orderState == "9") {
                cleaningEnd.visibility = View.VISIBLE// 退款时间
                tv_cleaningEnd.visibility = View.VISIBLE//// 退款时间
                cleaningEnd.text = "退款时间"
                tv_cleaningEnd.text = model.refundTime
            }
        }

        if (model.orderState == "6" || model.orderState == "7") {
            takeTime.visibility = View.VISIBLE//取件时间
            tv_takeTime.visibility = View.VISIBLE
            cleaningEnd.visibility = View.VISIBLE//清洗结束时间
            tv_cleaningEnd.visibility = View.VISIBLE

            tv_takeTime.text = model.getTime
            tv_cleaningEnd.text = model.cleanTime
            if (model.orderState == "7") {//
                returnTime.visibility = View.VISIBLE//归还时间
                tv_returnTime.visibility = View.VISIBLE
                tv_returnTime.text = model.returnTime
            }
        }
        orderState = model.orderState
        if (orderState == "1") {//1待付款
            tv_pay.text = "立即付款"
            tv_again.text = "取消"

            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.VISIBLE
            tv_refund.visibility = View.GONE
        } else if (orderState == "2") {//2待送货
            tv_pay.text = "确认送达"

            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.VISIBLE
            tv_refund.visibility = View.GONE
        } else if (orderState == "3") {//3待收货
            tv_pay.text = "确认收货"
            tv_refund.text = "    退款    "

            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.VISIBLE
            tv_refund.visibility = View.VISIBLE
        } else if (orderState == "4") {//4待取货
            tv_pay.text = "确认取件"
            tv_refund.text = "    退款    "

            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.VISIBLE
            tv_refund.visibility = View.VISIBLE
        } else if (orderState == "5") {//5清洗中

            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.GONE
            tv_refund.visibility = View.GONE
        } else if (orderState == "6") {//6带归还

            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.GONE
            tv_refund.visibility = View.GONE
        } else if (orderState == "7") {//7归还中

            tv_pay.text = "确认收货"
            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.VISIBLE
            tv_refund.visibility = View.GONE
        } else if (orderState == "8") {//8退款中

            tv_pay.text = "确认收货"
            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.VISIBLE
            tv_refund.visibility = View.GONE

        } else if (orderState == "9") {//9已退款

            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.GONE
            tv_refund.visibility = View.GONE

        } else if (orderState == "10") {//10待评价
            tv_pay.text = "去评价"

            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.VISIBLE
            tv_refund.visibility = View.GONE

        } else if (orderState == "11") {//11已完成

            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.GONE
            tv_refund.visibility = View.GONE
        } else if (orderState == "12") {//12已取消

            tv_again.visibility = View.VISIBLE
            tv_pay.visibility = View.GONE
            tv_refund.visibility = View.GONE
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_pay -> {
                if (orderState == "10") {//评价
                    val bundle = Bundle()
                    bundle.putInt("position", position)
                    bundle.putString("num", orderNum)
                    MyApplication.openActivityForResult(this, EvaluateActivity::class.java, bundle, StaticUtil.EvaluateResult)
                } else if (orderState == "1") {//付款
                    val bundle = Bundle()
                    bundle.putString("oderNum", orderNum)
                    bundle.putString("balance", StaticUtil.balance)
                    bundle.putString("payMoney", totalPay)
                    MyApplication.openActivity(this, PaymentActivity::class.java, bundle)
                } else if (orderState == "3" || orderState == "7" || orderState == "8") {//确认收货
                    ProgressDialog.showDialog(this)
                    MyOrder_144155.ConfirmOrder(this, orderNum, position, null)
                } else if (orderState == "4") {//归还，洗衣洗鞋，确认已取件
                    ProgressDialog.showDialog(this)
                    MyOrder_144155.TakepartLaundry(this, orderNum, position, null)
                } else if (orderState == "6") {//归还，洗衣洗鞋，洗完之后，点击开始送还
                    ProgressDialog.showDialog(this)
                    MyOrder_144155.ReturnLaundry(this, orderNum, position, null)
                }
            }
            R.id.tv_refund -> {
                //退款
                if (orderState == "2" || orderState == "3" || orderState == "4") {
                    val bundle = Bundle()
                    bundle.putString("num", orderNum)
                    bundle.putInt("position", position)
                    MyApplication.openActivityForResult(this, RefundActivity::class.java, bundle, StaticUtil.RefundResult)
                }
            }
            R.id.tv_again -> {
                if (orderState == "1") {//取消订单
                    ProgressDialog.showDialog(this)
                    MyOrder_144155.CancelOrder(null, orderNum, position, null)
                } else {//再来一单
                    ProgressDialog.showDialog(this)
                    MyOrder_144155.againOrder(this, orderNum)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}