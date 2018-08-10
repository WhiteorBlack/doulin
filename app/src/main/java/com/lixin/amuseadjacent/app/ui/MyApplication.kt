package com.lixin.amuseadjacent.app.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.multidex.MultiDexApplication

import com.lixin.amuseadjacent.app.util.SharedPreferencesUtil
import com.lixin.amuseadjacent.app.util.ToastUtil


/**
 * Created by Slingge on 2017/1/24 0024.
 */

class MyApplication : MultiDexApplication() {

    var lat: Double? = -1.0
    var lon: Double? = -1.0

    //String json = "{\"cmd\":\"getMsg\"" + "}";
    // String json = "{\"cmd\":\"upPrize\",\"prizeId\":\"" + prizeId  + "\",\"userNme\":\"" + MyApplication.getUserName() + "\"}";


    var CameraPath = Environment.getExternalStorageDirectory().path + "/com.lxkj.loanthrough/"

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        myApplication = this
    }

    companion object {
        private var uId = ""
        private var context: Context? = null

        private var myApplication: MyApplication? = null
        // if语句下是不会走的，Application本身已单例
        val instance: MyApplication
            get() {
                if (myApplication == null) {
                    synchronized(MyApplication::class.java) {
                        if (myApplication == null) {
                            myApplication = MyApplication()
                        }
                    }
                }
                return myApplication!!
            }


        /**
         * 检查是否已经登录 true 已登录
         *
         * @return
         */
        fun isLogined(): Boolean{
            return  uId != "" && SharedPreferencesUtil.getSharePreBoolean(context, "isLogin")
        }

        /**
         * 是否登陆提示
         */
        val isLoginToa: Boolean
            get() {
                val b = SharedPreferencesUtil.getSharePreStr(context, "uId") != "" && SharedPreferencesUtil.getSharePreBoolean(context, "isLogin")
                if (b) {
                    return true
                } else {
                    ToastUtil.showToast("请登录")
                    return false
                }
            }

        /**
         * 通过类名启动Activity，并且含有Bundle数据
         *
         * @param targetClass
         * @param extras
         */
        @JvmOverloads
        fun openActivity(context: Context, targetClass: Class<*>,
                         extras: Bundle? = null) {
            val intent = Intent(context, targetClass)
            if (extras != null) {
                intent.putExtras(extras)
            }
            context.startActivity(intent)
        }

        fun openActivityForResult(activity: Activity,
                                  targetClass: Class<*>, extras: Bundle?, requestCode: Int) {
            val intent = Intent(activity, targetClass)
            if (extras != null) {
                intent.putExtras(extras)
            }
            activity.startActivityForResult(intent, requestCode)
        }


        /**
         * Fragment中无效
         */
        fun openActivityForResult(activity: Activity,
                                  targetClass: Class<*>, requestCode: Int) {
            openActivityForResult(activity, targetClass, null, requestCode)
        }
    }


}
/**
 * 通过类名启动Activity
 *
 * @param targetClass
 */
