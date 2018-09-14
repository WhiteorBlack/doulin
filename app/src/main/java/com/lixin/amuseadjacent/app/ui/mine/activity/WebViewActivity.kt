package com.lixin.amuseadjacent.app.ui.mine.activity

import android.os.Bundle
import android.webkit.WebView
import com.lixin.amuseadjacent.R
import com.lixin.amuseadjacent.app.ui.base.BaseActivity
import com.lixin.amuseadjacent.app.view.MyWebView

/**
 * Created by Slingge on 2018/9/3
 */
class WebViewActivity : BaseActivity() {


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
        } else if (flag == 1) {//常见问题
            inittitle("常见问题")
        } else if (flag == 2) {//关于逗邻
            inittitle("关于逗邻")
        } else if (flag == 3) {//关于逗邻
            inittitle("用户协议")
        } else if (flag == 4) {//关于逗邻
            inittitle("隐私协议")
        } else {
            inittitle(intent.getStringExtra("title"))
            webView!!.loadUrl(intent.getStringExtra("url"))
        }


    }

}