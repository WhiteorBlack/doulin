package com.lixin.amuseadjacent.app.ui.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.util.AbStrUtil
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * Created by Slingge on 2018/8/16
 */
object ScreenFriendsPop {

    var flag = 0//0全部，1男，2女
    private var pop: PopupWindow? = null

    interface ScreenCallBack {
        fun screen(flag: Int)
    }

    fun screenDialog(context: Context, rlTopBar: View,screenCallBack: ScreenCallBack ) {

        val view = LayoutInflater.from(context).inflate(R.layout.pop_screen_friends, null)

        if (pop == null) {
            pop = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        val tv_all = view.findViewById<TextView>(R.id.tv_all)
        val tv_boy = view.findViewById<TextView>(R.id.tv_boy)
        val tv_girle = view.findViewById<TextView>(R.id.tv_girle)
        val tv_cancel = view.findViewById<TextView>(R.id.tv_cancel)
        val tv_enter = view.findViewById<TextView>(R.id.tv_enter)

        tv_all.setOnClickListener { v ->
            flag = 0

            tv_all.setTextColor(context.resources.getColor(R.color.white))
            tv_all.setBackgroundResource(R.drawable.bg_them8)

            tv_boy.setTextColor(context.resources.getColor(R.color.black))
            tv_boy.setBackgroundResource(R.drawable.bg_white8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy_b, tv_boy)

            tv_girle.setTextColor(context.resources.getColor(R.color.black))
            tv_girle.setBackgroundResource(R.drawable.bg_white8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl_b, tv_girle)
        }

        tv_boy.setOnClickListener { v ->
            flag = 1

            tv_all.setTextColor(context.resources.getColor(R.color.black))
            tv_all.setBackgroundResource(R.drawable.bg_white8)

            tv_boy.setTextColor(context.resources.getColor(R.color.white))
            tv_boy.setBackgroundResource(R.drawable.bg_boy8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy, tv_boy)

            tv_girle.setTextColor(context.resources.getColor(R.color.black))
            tv_girle.setBackgroundResource(R.drawable.bg_white8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl_b, tv_girle)
        }

        tv_girle.setOnClickListener { v ->
            flag = 2

            tv_all.setTextColor(context.resources.getColor(R.color.black))
            tv_all.setBackgroundResource(R.drawable.bg_white8)

            tv_boy.setTextColor(context.resources.getColor(R.color.black))
            tv_boy.setBackgroundResource(R.drawable.bg_white8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_boy_b, tv_boy)

            tv_girle.setTextColor(context.resources.getColor(R.color.white))
            tv_girle.setBackgroundResource(R.drawable.bg_girl8)
            AbStrUtil.setDrawableLeft(context, R.drawable.ic_girl, tv_girle)
        }

        tv_cancel.setOnClickListener { v ->
            pop!!.dismiss()
        }

        tv_enter.setOnClickListener { v ->
            pop!!.dismiss()
            screenCallBack.screen(flag)
        }


        val cd = ColorDrawable(0)
//        pop!!.setBackgroundDrawable(cd)
        pop!!.animationStyle = R.style.PopupAnimation
        pop!!.update()
        pop!!.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
        pop!!.isTouchable = true // 设置popupwindow可点击
        pop!!.isOutsideTouchable = true // 设置popupwindow外部可点击
        pop!!.isFocusable = true // 获取焦点

        // 设置popupwindow的位置（相对tvLeft的位置）
        pop!!.showAsDropDown(rlTopBar, 0, 0)

        pop!!.setTouchInterceptor { v, event ->
            // 如果点击了popupwindow的外部，popupwindow也会消失
            if (event.action == MotionEvent.ACTION_OUTSIDE) {
                pop!!.dismiss()
                return@setTouchInterceptor true
            }
            false
        }
    }


}