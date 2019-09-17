package com.androidwind.gank.kotlin.util

import android.app.Activity
import android.content.Context
import com.androidwind.androidquick.util.ToastUtil

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
fun Context.toast(toastContent: String) {
    ToastUtil.showToast(toastContent)
}