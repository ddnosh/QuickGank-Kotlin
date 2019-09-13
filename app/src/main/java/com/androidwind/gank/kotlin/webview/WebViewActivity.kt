package com.androidwind.gank.kotlin.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.androidwind.androidquick.util.StringUtil
import com.androidwind.gank.kotlin.R
import com.androidwind.gank.kotlin.base.BaseActivity
import com.androidwind.gank.kotlin.constant.Constants
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class WebViewActivity : BaseActivity() {

    private var URL = "https://github.com/ddnosh"

    private var mBundle: Bundle? = null

    override fun initViewsAndEvents(savedInstanceState: Bundle?) {
        initData()
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_webview
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    fun initData() {
        var url = mBundle?.getString(Constants.GANK_URL)
        if (StringUtil.isEmpty(url)) {
            url = URL
        }

        val webSettings = wvWebView.getSettings()
        webSettings.setJavaScriptEnabled(true)

        wvWebView.requestFocus()

        // 设置setWebChromeClient对象
        wvWebView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                getToolbar().title = title
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                pbWebView.setProgress(newProgress)
            }
        }

        wvWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                wvWebView.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                getToolbar().title = StringUtil.getTrimedString(wvWebView.getUrl())
                pbWebView.setVisibility(View.VISIBLE)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                getToolbar().title = StringUtil.getTrimedString(wvWebView.getTitle())
                pbWebView.setVisibility(View.GONE)
            }
        }

        wvWebView.loadUrl(url)
    }

    override fun getBundleExtras(extras: Bundle) {
        mBundle = extras
    }

    override fun isLoadDefaultTitleBar(): Boolean {
        return true
    }

    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    override fun onBackPressed() {
        if (wvWebView.canGoBack()) {
            wvWebView.goBack()
            return
        }

        super.onBackPressed()
    }

    //类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    override fun onPause() {
        super.onPause()
        wvWebView.onPause()
    }

    override fun onResume() {
        wvWebView.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        if (wvWebView != null) {
            wvWebView.destroy()
        }
        super.onDestroy()
    }
}