package com.lixin.amuseadjacent.app.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.view.*
import com.lixin.amuseadjacent.R
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.nostra13.universalimageloader.core.ImageLoader
import com.xiao.nicevideoplayer.NiceUtil
import com.xiao.nicevideoplayer.NiceVideoPlayer
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import com.xiao.nicevideoplayer.TxVideoPlayerController

@SuppressLint("StaticFieldLeak")
/**
 * Created by Slingge on 2018/10/16
 */
object ViodeoPlayerDialog : DialogInterface.OnDismissListener {
    override fun onDismiss(p0: DialogInterface?) {
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
        NiceUtil.showActionBar(context)
        player = null
    }

    private var WindowHeigth = 0
    private var WindowWidth = 0

    private var context:Context?=null

    private var builder: AlertDialog? = null
    @SuppressLint("StaticFieldLeak")
    private var player: NiceVideoPlayer? = null

    fun videoPlayer(context: Activity, urlVideo: String, dynamicImg: String, height: Int, width: Int) {
        this.context=context
        NiceUtil.hideActionBar(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_videoplayer, null)
        if (builder == null) {
            builder = AlertDialog.Builder(context, R.style.Dialog).create() // 先得到构造器
            builder!!.setOnDismissListener(this)

            val manager = context.windowManager
            val outMetrics = DisplayMetrics()
            manager.defaultDisplay.getMetrics(outMetrics)
            WindowWidth = outMetrics.widthPixels
            WindowHeigth = outMetrics.heightPixels
        }
        builder!!.show()
        builder!!.window.setContentView(view)

        player = view.findViewById(R.id.player)

        val linearParams = player!!.layoutParams as ConstraintLayout.LayoutParams //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20

        linearParams.height = WindowHeigth// 控件的宽强制设成30
        linearParams.width = WindowWidth// 控件的宽强制设成30
        player!!.layoutParams = linearParams

        player!!.setPlayerType(NiceVideoPlayer.TYPE_IJK)
        player!!.setUp(urlVideo, null)
        val controller = TxVideoPlayerController(context)
        controller.setTitle("")
        controller.setHideFull()

        ImageLoader.getInstance().displayImage(dynamicImg, controller.imageView())
        player!!.setController(controller)
        player!!.start()
    }

}