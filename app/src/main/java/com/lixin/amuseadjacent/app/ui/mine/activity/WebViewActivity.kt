package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.webkit.WebView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.util.StaticUtil
import com.lixin.amuseadjacent.app.view.MyWebView

/**
 * Created by Slingge on 2018/9/3
 */
class WebViewActivity : BaseActivity() {

    /**
     * http://39.107.106.122/wisdom/api/about/displayContent?id=1
     * 用户协议 1
     * 隐私条款 2
     * 提现规则 3
     * 邀请规则 4
     * 优惠券说明 5
     * 影响力说明 6
     * 关于逗邻 7
     * id=8  交易说明
     * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        init()
    }


    private fun init() {
        StatusBarWhiteColor()
        val myWebView = findViewById<MyWebView>(R.id.webview)
        val webView = myWebView.webView
        val flag = intent.getIntExtra("flag", -1)
        if (flag == 0) {//优惠券说名
            inittitle("优惠券说明")
            webView!!.loadUrl(StaticUtil.WebViewUrl+"id=5")
        } else if (flag == 1) {//常见问题
            inittitle("常见问题")
//            webView!!.loadUrl(URL+"id=5")
        } else if (flag == 2) {//关于逗邻
            inittitle("关于逗邻")
            webView!!.loadUrl(StaticUtil.WebViewUrl+"id=7")
        } else if (flag == 3) {//用户协议
            inittitle("用户协议")
            webView!!.loadUrl(StaticUtil.WebViewUrl+"id=1")
        } else if (flag == 4) {//隐私协议
            inittitle("隐私协议")
            webView!!.loadUrl(StaticUtil.WebViewUrl+"id=2")
        } else if (flag ==5) {// 交易说明
            inittitle("交易说明")
            webView!!.loadUrl(StaticUtil.WebViewUrl+"id=8")
        } else if (flag ==6) {// 影响力规则
            inittitle("影响力规则")
            webView!!.loadUrl(StaticUtil.WebViewUrl+"id=6")
        } else {
            inittitle(intent.getStringExtra("title"))
            webView!!.loadUrl(intent.getStringExtra("url"))
        }


    }

}