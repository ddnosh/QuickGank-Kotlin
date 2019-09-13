package com.androidwind.gank.kotlin.gankabout

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import com.androidwind.androidquick.util.AppUtil
import com.androidwind.gank.kotlin.R
import com.androidwind.gank.kotlin.base.BaseActivity
import com.androidwind.gank.kotlin.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_about.*

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankAboutActivity : BaseActivity() {
    override fun initViewsAndEvents(savedInstanceState: Bundle?) {
//        tv_version_value.text = AppUtil.getVersionName(this)
        tv_version_value.apply {
            text = AppUtil.getVersionName(this@GankAboutActivity)
        }
        tv_desc_value.apply {
            text = getString()
            movementMethod = LinkMovementMethod.getInstance()
        }
        tv_github_value.apply {
            setOnClickListener {
                val bundle = Bundle()
                bundle.putString("URL", tv_github_value.text.toString())
                readyGo(WebViewActivity::class.java, bundle)
            }
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_about
    }

    private fun getString(): SpannableString {
        val spannableString = SpannableString(
            "一个基于AndroidQuick快速开发库的Gank.io客户端"
        )
        spannableString.setSpan(
            ForegroundColorSpan(Color.RED),
            4, 16,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            UnderlineSpan(),
            4, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                val bundle = Bundle()
                bundle.putString("URL", "https://github.com/ddnosh/AndroidQuick")
                readyGo(WebViewActivity::class.java, bundle)
            }
        }, 4, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }
}