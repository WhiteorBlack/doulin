package com.lixin.amuseadjacent.app.ui.dialog

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.service.adapter.CouponAdapter
import com.lixin.amuseadjacent.app.ui.service.model.CouponModel
import com.lixin.amuseadjacent.app.ui.service.model.ReceiveCouponModel
import com.lixin.amuseadjacent.app.ui.service.request.Coupon_3132
import com.lixin.amuseadjacent.app.util.RecyclerItemTouchListener
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil

/**
 * Created by Slingge on 2018/8/30
 */
object CouponDialog {


    private var builder: AlertDialog? = null

    fun communityDialog(context: Activity, couponList: ArrayList<CouponModel.couponModel>) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_coupon, null)
        if (builder == null) {
            builder = AlertDialog.Builder(context, R.style.Dialog).create() // 先得到构造器
        }
        builder!!.show()
        builder!!.window.setContentView(view)

        val couponAdapter = CouponAdapter(context, couponList)

        val rv_coupon = view.findViewById<RecyclerView>(R.id.rv_coupon)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_coupon.layoutManager = linearLayoutManager
        rv_coupon.adapter = couponAdapter

        rv_coupon.addOnItemTouchListener(object : RecyclerItemTouchListener(rv_coupon) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                if (i < 0) {
                    return
                }
                couponList[i].isSelect = !couponList[i].isSelect
                couponAdapter.notifyDataSetChanged()
            }
        })

        val tv_receive = view.findViewById<TextView>(R.id.tv_receive)
        tv_receive.setOnClickListener { v ->
            ProgressDialog.showDialog(context)
            val model = ReceiveCouponModel()
            for (i in 0 until couponList.size) {
                if (couponList[i].isSelect) {
                    model.securitiesid.add(couponList[i].securitiesid)
                }
            }
            Coupon_3132.Receive(model)
        }

        val dialogWindow = builder!!.window
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
//        p.height = (d.height * 0.75).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.9).toInt() // 宽度设置为屏幕宽
        dialogWindow.attributes = p

    }


    fun dissmis() {
        if (builder != null) {
            builder!!.dismiss()
            builder = null
        }
    }

}