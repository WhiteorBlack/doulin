package com.lixin.amuseadjacent.app.util

import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.lixin.amuseadjacent.app.ui.MyApplication



/**
 * 单例Toast
 */

object ToastUtil {

    private var mToast: Toast? = null

    fun showToast(text: String) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.instance, text, Toast.LENGTH_SHORT)
        }
        mToast!!.setText(text)
        mToast!!.show()
    }


    fun showLongToast(text: String) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.instance, text, Toast.LENGTH_LONG)
        }
        mToast!!.setText(text)
        mToast!!.show()
    }

    /**
     * @param view 最外层布局
     * LENGTH_INDEFINITE	始终显示，点击Snackbar上的按钮才会消失
    LENGTH_SHORT	显示1500毫秒
    LENGTH_LONG
     *  setActionTextColor(ColorStateList colors)
     * */
    fun showSnackBar(view: View, title: String, onClick: View.OnClickListener) {
        Snackbar.make(view, title, Snackbar.LENGTH_SHORT).setAction("隐藏", onClick
        ).setDuration(Snackbar.LENGTH_SHORT).show()
    }

    fun showSnackBar(view: View, title: String) {
        Snackbar.make(view, title, Snackbar.LENGTH_SHORT).setDuration(Snackbar.LENGTH_SHORT).show()
    }


}
