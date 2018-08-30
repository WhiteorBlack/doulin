package com.lixin.amuseadjacent.app.ui.dialog

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.service.adapter.CouponAdapter

/**
 * Created by Slingge on 2018/8/30
 */
object CouponDialog {


    private var builder: AlertDialog? = null

    private var couponAdapter: CouponAdapter? = null

    fun communityDialog(context: Activity) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_coupon, null)
        if (builder == null) {
            builder = AlertDialog.Builder(context, R.style.Dialog).create() // 先得到构造器
        }
        builder!!.show()
        builder!!.window.setContentView(view)

        couponAdapter = CouponAdapter(context)
        val rv_coupon = view.findViewById<RecyclerView>(R.id.rv_coupon)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_coupon.layoutManager = linearLayoutManager
        rv_coupon.adapter = couponAdapter

        val tv_receive = view.findViewById<TextView>(R.id.tv_receive)
        tv_receive.setOnClickListener { v -> builder!!.dismiss() }

        val dialogWindow = builder!!.window
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
        p.height = (d.height * 0.75).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.9).toInt() // 宽度设置为屏幕宽
        dialogWindow.attributes = p

    }


}