package com.lixin.amuseadjacent.app.ui.dialog

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
import com.nostra13.universalimageloader.core.ImageLoader


/**
 * Created by Slingge on 2018/9/18
 */
class ShopCartDialog(val plusCallBack: PlusCallBack, val reduceCallBack: ReduceCallBack, val delCallBack: DelCallBack) {

    private var dialog: MyBottomSheetDialog? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var recyclerView: RecyclerView? = null
    private var view: View? = null

    private var iv_down: ImageView? = null


    interface PlusCallBack {
        fun plus(position: Int) {

        }
    }

    interface ReduceCallBack {
        fun reduce(position: Int) {

        }
    }

    interface DelCallBack {
        fun del(position: Int) {

        }
    }


    fun shopCar(context: Activity, rightList: ArrayList<ShopGoodsModel.dataModel>) {

        if (dialog == null) {
            view = LayoutInflater.from(context).inflate(R.layout.bottomsheetdialog_list, null)
            dialog = MyBottomSheetDialog(context)
            dialog!!.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            dialog!!.setCanceledOnTouchOutside(true)
            dialog!!.setContentView(view)
            linearLayoutManager = LinearLayoutManager(context)
            recyclerView = view!!.findViewById(R.id.recyclerView)

            iv_down = view!!.findViewById(R.id.iv_down)

            recyclerView!!.layoutManager = linearLayoutManager
        }
        dialog!!.show()

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

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val model = goodsList[position]
            ImageLoader.getInstance().displayImage(model.goodsImg, holder.image)
            val lp = ConstraintLayout.LayoutParams(holder.image.layoutParams)
            lp.setMargins(12, 0, 0, 0)
            holder.image.layoutParams = lp

            holder.tv_name.text = model.goodsName

            if (TextUtils.isEmpty(model.goodsCuprice)) {
                holder.tv_money.text = " ￥：" + model.goodsPrice
            } else {
                holder.tv_money.text = " ￥：" + model.goodsCuprice
            }

            holder.num.text = model.goodsNum.toString()

            holder.tv_plus.setOnClickListener { v ->
                plusCallBack.plus(position)
                holder.num.text = (model.goodsNum++).toString()
            }
            holder.tv_reduce.setOnClickListener { v ->
                if (goodsList[position].goodsNum == 1) {
                    return@setOnClickListener
                }
                holder.num.text = (model.goodsNum--).toString()
                reduceCallBack.reduce(position)
            }

            holder.tv_plus.setOnClickListener { v ->
                delCallBack.del(position)
                notifyItemRemoved(position)
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

                ic_chack.visibility = View.VISIBLE
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