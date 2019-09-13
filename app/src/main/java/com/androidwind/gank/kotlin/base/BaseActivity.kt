package com.androidwind.gank.kotlin.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.androidwind.androidquick.eventbus.EventCenter
import com.androidwind.androidquick.ui.base.QuickActivity
import com.androidwind.androidquick.util.LogUtil
import com.androidwind.gank.kotlin.ui.FrameActivity

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
abstract class BaseActivity : QuickActivity() {

    private val TAG = "BaseActivity"
    override fun getBundleExtras(extras: Bundle) {}

    override fun setDefaultVaryViewRoot(): View? {
        return null
    }

    override fun onEventComing(eventCenter: EventCenter<*>) {
        LogUtil.i(TAG, eventCenter.eventCode.toString() + "")
    }

    override fun toggleOverridePendingTransition(): Boolean {
        return true
    }

    override fun getOverridePendingTransitionMode(): TransitionMode {
        return TransitionMode.LEFT
    }

    override fun isLoadDefaultTitleBar(): Boolean {
        return false
    }

    override fun getGoIntent(clazz: Class<*>): Intent {
        if (BaseFragment::class.java.isAssignableFrom(clazz)) {
            val intent = Intent(this, FrameActivity::class.java)
            intent.putExtra("fragmentName", clazz.name)
            return intent
        } else {
            return super.getGoIntent(clazz)
        }
    }
}