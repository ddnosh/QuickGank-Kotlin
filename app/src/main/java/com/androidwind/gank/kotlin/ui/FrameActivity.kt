package com.androidwind.gank.kotlin.ui

import android.os.Bundle
import com.androidwind.androidquick.ui.base.QuickFragment
import com.androidwind.androidquick.util.LogUtil
import com.androidwind.androidquick.util.ReflectUtil
import com.androidwind.gank.kotlin.R
import com.androidwind.gank.kotlin.base.BaseActivity

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class FrameActivity : BaseActivity() {

    private var TAG = "FrameActivity"
    private var bundle: Bundle? = null

    override fun initViewsAndEvents(savedInstanceState: Bundle?) {
        val className = bundle?.getString("fragmentName")
        LogUtil.i(TAG, "the fragment class name is->" + className!!)
        val `object` = ReflectUtil.getObject(className)
        if (`object` is QuickFragment) {
            supportFragmentManager.beginTransaction().replace(R.id.content_frame, `object`)
                .commitAllowingStateLoss()
        } else {
            LogUtil.e(TAG, " the fragment class is not exist!!!")
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_frame
    }

    override fun getBundleExtras(extras: Bundle) {
        bundle = extras
    }
}