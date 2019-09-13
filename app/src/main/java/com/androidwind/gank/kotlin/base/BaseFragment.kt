package com.androidwind.gank.kotlin.base

import android.content.Context
import android.content.Intent
import android.view.View
import com.androidwind.androidquick.eventbus.EventCenter
import com.androidwind.androidquick.ui.base.QuickFragment
import com.androidwind.gank.kotlin.ui.FrameActivity

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
abstract class BaseFragment : QuickFragment() {
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onFirstUserVisible() {

    }

    override fun onUserVisible() {

    }

    override fun onUserInvisible() {

    }

    override fun setDefaultVaryViewRoot(): View? {
        return null
    }

    override fun onEventComing(eventCenter: EventCenter<*>) {

    }

    override fun getGoIntent(clazz: Class<*>): Intent {
        if (BaseFragment::class.java.isAssignableFrom(clazz)) {
            val intent = Intent(activity, FrameActivity::class.java)
            intent.putExtra("fragmentName", clazz.name)
            return intent
        } else {
            return super.getGoIntent(clazz)
        }
    }
}