package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.util.Log
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.ui.mine.adapter.RuleAdapter
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.util.abLog
import com.lxkj.linxintechnologylibrary.app.util.ToastUtil
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import com.umeng.socialize.utils.ShareBoardlistener
import kotlinx.android.synthetic.main.activity_nviting_friends.*

/**
 * 邀请好友
 * Created by Slingge on 2018/9/4
 */
class InvitingFriendsAcivity : BaseActivity() {

    private var ruleAdapter: RuleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nviting_friends)
        init()
    }


    private fun init() {
        inittitle("邀请好友")
        StatusBarWhiteColor()

        tv_id.text = intent.getStringExtra("id")


        val webView = webview.webView
        webView.loadUrl(StaticUtil.WebViewUrl + "id=4")

        tv_down.setOnClickListener { v ->
            val displaylist = arrayOf(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
            ShareAction(this).setDisplayList(*displaylist)
                    .setShareboardclickCallback(shareBoardlistener).open()
        }
    }


    //分享面板的点击事件
    private val shareBoardlistener = ShareBoardlistener { snsPlatform, share_media ->
        if (share_media == null) {

        } else {
            val image = UMImage(this, R.mipmap.ic_launcher)//网络图片
            val web = UMWeb(intent.getStringExtra("url"))
            web.description = "我的邀请码" + intent.getStringExtra("id")
            web.setThumb(image)
            web.title = "逗邻"
            ShareAction(this).setPlatform(share_media).setCallback(umShareListener)
                    .withMedia(web)
                    .share()
        }

    }
    //分享的回调
    private val umShareListener = object : UMShareListener {
        override fun onStart(platform: SHARE_MEDIA) {
            //分享开始的回调
        }

        override fun onResult(platform: SHARE_MEDIA) {
            Log.d("plat", "platform$platform")
            ToastUtil.showToast("分享成功")
        }

        override fun onError(platform: SHARE_MEDIA, t: Throwable) {
            abLog.e("分享失败", t.toString())
            ToastUtil.showToast("分享失败")
        }

        override fun onCancel(platform: SHARE_MEDIA) {
            ToastUtil.showToast("取消分享")
        }
    }
}