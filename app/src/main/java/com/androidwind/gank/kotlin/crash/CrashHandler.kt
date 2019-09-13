package com.androidwind.gank.kotlin.crash

import android.content.Context
import com.androidwind.gank.kotlin.constant.Constants

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
object CrashHandler : Thread.UncaughtExceptionHandler {
    var mContext: Context? = null;
    var mDefaultHandler: Thread.UncaughtExceptionHandler? = null;

    override fun uncaughtException(t: Thread, e: Throwable) {
        if (mDefaultHandler != null) {
            if (Constants.EXCEPTION_MVP_VIEW_NOT_ATTACHED.equals(e.message)) {
                return
            }
            mDefaultHandler!!.uncaughtException(t, e)
        }
    }

    fun init(context: Context) {
        mContext = context.applicationContext
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }
}