package com.lixin.amuseadjacent.app.ui.dialog

import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import com.lixin.amuseadjacent.R
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.widget.EditText
import android.widget.TextView
import com.lixin.amuseadjacent.app.MyApplication
import com.lixin.amuseadjacent.app.ui.mine.activity.EffectCommunityActivity
import com.lixin.amuseadjacent.app.util.AbStrUtil


/**
 * 最定义标签
 * Created by Slingge on 2018/8/15
 */
object TalentTagsDialog {

    var builder: AlertDialog? = null
    var view: View? = null

    interface CustomTagsCallBack {
        fun tag(tag: String)
    }


    fun communityDialog(context: Activity, customViewCallback: CustomTagsCallBack) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_tags, null)
        }
        if (builder == null) {
            builder = AlertDialog.Builder(context, R.style.Dialog).create() // 先得到构造器
        }
        builder!!.show()
        builder!!.window.setContentView(view)

        val tv_no = view!!.findViewById<TextView>(R.id.tv_no)
        tv_no.setOnClickListener { v ->
            builder!!.dismiss()
        }

        val tv_tag = view!!.findViewById<EditText>(R.id.tv_tag)

        val tv_yes = view!!.findViewById<TextView>(R.id.tv_yes)
        tv_yes.setOnClickListener { v ->
            val tag = AbStrUtil.etTostr(tv_tag)
            if (!TextUtils.isEmpty(tag)) {
                customViewCallback.tag(tag)
                builder!!.dismiss()
            }
        }

        val dialogWindow = builder!!.window
        dialogWindow.setGravity(Gravity.CENTER)//显示在底部
        val m = context.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用
        val p = dialogWindow.attributes // 获取对话框当前的参数值
//        p.height = (d.height * 0.5).toInt() // 高度设置为屏幕的0.5
        p.width = (d.width * 0.85).toInt() // 宽度设置为屏幕宽
        dialogWindow.attributes = p

        builder!!.window.clearFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }


    fun dismiss() {
        if (builder != null) {
            builder!!.dismiss()
//            builder = null

        }

    }

}