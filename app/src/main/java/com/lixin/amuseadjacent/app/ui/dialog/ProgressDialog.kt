package com.lixin.amuseadjacent.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.ContextMenu
import android.view.Gravity
import android.view.LayoutInflater
import android.view.TextureView
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.MyApplication
import kotlinx.android.synthetic.main.fragment_message.view.*

@SuppressLint("StaticFieldLeak")
/**
 * Created by Slingge on 2018/9/4
 */
object ProgressDialog {

    var builder: AlertDialog? = null

    private var text: TextView? = null

    fun showDialog(context: Activity) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
        if (builder == null) {
            builder = AlertDialog.Builder(context, R.style.ProgressDialog).create() // 先得到构造器
        }

        if (text == null) {
            text = view.findViewById(R.id.text)
        }

        builder!!.show()
        builder!!.window.setContentView(view)
        builder!!.setCanceledOnTouchOutside(false)

        val dialogWindow = builder!!.window
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.8).toInt() // 宽度设置为屏幕宽
        dialogWindow.attributes = p
    }

    fun dissDialog() {
        if (builder != null) {
            builder!!.dismiss()
            builder = null
        }
    }

}