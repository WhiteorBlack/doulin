package com.lixin.amuseadjacent.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import kotlinx.android.synthetic.main.activity_event_details.*
import java.util.*

/**
 * Created by Slingge on 2018/10/24
 */
object TalentTypeDialog {

    interface CallBack {
        fun callback(remarks: String)
    }

    fun createDialog(context: Activity, callBack: CallBack, remarks: String) {
        val builder = AlertDialog.Builder(context, R.style.Dialog).create()
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_talent, null)
        builder.show()
        builder.window.setContentView(view)
        val et_userName = view.findViewById<EditText>(R.id.tv_tag)
        et_userName.setText(remarks)
        val btn = view.findViewById<TextView>(R.id.tv_yes)
        val cancle=view.findViewById<TextView>(R.id.tv_no)
        cancle.setOnClickListener{v->
            builder.dismiss()
        }
        btn.setOnClickListener { v ->
            val remarks = AbStrUtil.etTostr(et_userName)
            if (TextUtils.isEmpty(remarks)) {
                ToastUtil.showToast( "请输入技能分类")
                return@setOnClickListener
            }
            callBack.callback(remarks)
            builder.dismiss()
        }


        val dialogWindow = builder.window
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
//        p.height = (d.height * 0.75).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.9).toInt() // 宽度设置为屏幕宽
        dialogWindow.attributes = p

        builder.window.clearFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.showSoftInput(et_userName, 0)
            }
        },
                200)

    }


}