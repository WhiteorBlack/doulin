package com.lixin.amuseadjacent.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.view.MyBottomSheetDialog
import com.lixin.amuseadjacent.app.ui.service.model.ShopGoodsModel
import com.lixin.amuseadjacent.app.util.DoubleCalculationUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader


/**
 * Created by Slingge on 2018/9/18
 */
class ShopCartDialog(val plusCallBack: PlusCallBack, val reduceCallBack: ReduceCallBack,
                     val delCallBack: DelCallBack, val settlementCallBack: SettlementCallBack) {

    private var dialog: MyBottomSheetDialog? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var recyclerView: RecyclerView? = null
    private var view: View? = null

    private var SettlementView: View? = null

    private var iv_down: ImageView? = null


    interface PlusCallBack {
        fun plus(position: Int, num: Int, money: Double) {

        }
    }

    interface ReduceCallBack {
        fun reduce(position: Int, num: Int, money: Double) {

        }
    }

    interface DelCallBack {
        fun del(position: Int) {
        }
    }

    interface SettlementCallBack {
        fun Settlement() {
        }
    }

    fun setGoodList(context: Activity, rightList: ArrayList<ShopGoodsModel.dataModel>){
        val adapter = Adapter(context, rightList)
        recyclerView!!.adapter = adapter
    }

    fun shopCar(context: Activity, rightList: ArrayList<ShopGoodsModel.dataModel>) {

        if (dialog == null) {
            view = LayoutInflater.from(context).inflate(R.layout.bottomsheetdialog_list, null)
            dialog = MyBottomSheetDialog(context, R.style.ProgressDialog)
            dialog!!.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            dialog!!.setCanceledOnTouchOutside(true)
            dialog!!.setContentView(view)
            linearLayoutManager = LinearLayoutManager(context)
            recyclerView = view!!.findViewById(R.id.recyclerView)

            SettlementView = view!!.findViewById(R.id.view)

            iv_down = view!!.findViewById(R.id.iv_down)

            dialog!!.delegate.findViewById<View>(android.support.design.R.id.design_bottom_sheet)!!
                    .setBackgroundColor(context.resources.getColor(R.color.colorTransparent))

            recyclerView!!.layoutManager = linearLayoutManager
        }
        dialog!!.show()

        SettlementView!!.setOnClickListener { v ->
            settlementCallBack.Settlement()
        }

        val adapter = Adapter(context, rightList)
        recyclerView!!.adapter = adapter

        iv_down!!.setOnClickListener { v ->
            dialog!!.dismiss()
        }
    }


    inner class Adapter(val context: Activity, val goodsList: ArrayList<ShopGoodsModel.dataModel>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_shop_car_details, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return goodsList.size
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val model = goodsList[position]
            ImageLoader.getInstance().displayImage(model.goodsImg, holder.image)

            var num = model.goodsNum

            model.UnitPrice = if (TextUtils.isEmpty(model.goodsCuprice)) {
                model.goodsPrice.toDouble()
            } else {
                model.goodsCuprice.toDouble()
            }

            if (model.money == 0.0) {
                model.money = model.UnitPrice
            }

            var money = model.money
            holder.tv_money.text = " ￥：$money"
            holder.tv_name.text = model.goodsName
            holder.num.text = num.toString()

            holder.tv_plus.setOnClickListener { v ->

                if (!TextUtils.isEmpty(model.goodsStock) && num == model.goodsStock.toInt()) {
                    ToastUtil.showToast("库存不足")
                    return@setOnClickListener
                }
                num++
                holder.num.text = num.toString()

                money = DoubleCalculationUtil.mul(num.toDouble(), model.UnitPrice)
                holder.tv_money.text = " ￥：$money"

                plusCallBack.plus(position, num, money)
            }
            holder.tv_reduce.setOnClickListener { v ->
                if (num == 1) {
                    return@setOnClickListener
                }
                num--
                holder.num.text = num.toString()

                money = DoubleCalculationUtil.mul(num.toDouble(), model.UnitPrice)
                holder.tv_money.text = " ￥：$money"

                reduceCallBack.reduce(position, num, money)
            }

            holder.iv_del.setOnClickListener { v ->
                delCallBack.del(position)
            }

        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ic_chack = view.findViewById<ImageView>(R.id.ic_chack)

            val image = view.findViewById<ImageView>(R.id.image)
            val tv_name = view.findViewById<TextView>(R.id.tv_name)
            val tv_money = view.findViewById<TextView>(R.id.tv_money)
            val iv_del = view.findViewById<ImageView>(R.id.iv_del)
            val tv_plus = view.findViewById<TextView>(R.id.tv_plus)
            val num = view.findViewById<TextView>(R.id.num)
            val tv_reduce = view.findViewById<TextView>(R.id.tv_reduce)

            init {
                ic_chack.visibility = View.INVISIBLE
                ic_chack.setPadding(12, 0, 0, 0)
            }
        }
    }


    fun dismiss() {
        dialog!!.dismiss()
    }

    fun destroy() {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
    }


}