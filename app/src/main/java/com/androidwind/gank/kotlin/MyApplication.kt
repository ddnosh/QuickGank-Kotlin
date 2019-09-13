package com.androidwind.gank.kotlin

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.androidwind.androidquick.module.retrofit.RetrofitManager
import com.androidwind.androidquick.util.SpUtil
import com.androidwind.androidquick.util.ToastUtil
import com.androidwind.gank.kotlin.crash.CrashHandler
import com.androidwind.gank.kotlin.ui.AQActivityLifecycleCallbacks
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class MyApplication : MultiDexApplication() {

    lateinit var lifecycleCallback: AQActivityLifecycleCallbacks;

    companion object {

        lateinit var sINSTANCE: MyApplication

        fun getInstance(): MyApplication {
            return sINSTANCE;
        }

        fun getCurrentVisibleActivity(): Activity? {
            return sINSTANCE.lifecycleCallback.getCurrentVisibleActivity()
        }

        fun getRetrofit(): RetrofitManager {
            return RetrofitManager("http://gank.io/api/")
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this)
        sINSTANCE = this
        //lifecyclecallback
        lifecycleCallback = AQActivityLifecycleCallbacks()
        registerActivityLifecycleCallbacks(lifecycleCallback)
        //init ToastUtil
        ToastUtil.register(this)
        //init SpUtil
        SpUtil.init(this)
        //init stetho
        Stetho.initializeWithDefaults(this)
        //init crashhandler
        CrashHandler.init(this)
    }
}