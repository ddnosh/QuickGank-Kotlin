package com.androidwind.gank.kotlin.ui

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.androidwind.androidquick.util.LogUtil
import java.util.*

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class AQActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    val TAG = "AQActivityLifecycleCallbacks"

    var visibleActivity: Activity? = null

    var store: Stack<Activity> = Stack()

    override fun onActivityPaused(activity: Activity?) {
        LogUtil.i(
            TAG, activity?.getLocalClassName() + " was Pauseed, " + "activity==null  "
                    + (activity == null) + ", activity.isFinishing()    " + activity?.isFinishing() + ", activity.isDestroyed()    " + activity?.isDestroyed()
        )
        visibleActivity = null
    }

    override fun onActivityResumed(activity: Activity?) {
        LogUtil.i(
            TAG, activity?.getLocalClassName() + " was oResumed, " + "activity==null   "
                    + (activity == null) + ", activity.isFinishing()    " + activity?.isFinishing() + ", activity.isDestroyed()    " + activity?.isDestroyed()
        )
        visibleActivity = activity
    }

    override fun onActivityStarted(activity: Activity?) {
        LogUtil.i(
            TAG, activity?.getLocalClassName() + " was Started, " + "activity==null    "
                    + (activity == null) + ", activity.isFinishing()    " + activity?.isFinishing() + ", activity.isDestroyed()    " + activity?.isDestroyed()
        )
        store.add(activity)
    }

    override fun onActivityDestroyed(activity: Activity?) {
        LogUtil.i(
            TAG, activity?.getLocalClassName() + " was Destroyed, " + "activity==null  "
                    + (activity == null) + ", activity.isFinishing()    " + activity?.isFinishing() + ", activity.isDestroyed()    " + activity?.isDestroyed()
        )
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        LogUtil.i(
            TAG, activity?.getLocalClassName() + " was SaveInstanceState, " + "activity==null    "
                    + (activity == null) + ", activity.isFinishing()    " + activity?.isFinishing() + ", activity.isDestroyed()    " + activity?.isDestroyed()
        )
    }

    override fun onActivityStopped(activity: Activity?) {
        LogUtil.i(
            TAG, activity?.getLocalClassName() + " was Stoped, " + "activity==null   "
                    + (activity == null) + ", activity.isFinishing()    " + activity?.isFinishing() + ", activity.isDestroyed()    " + activity?.isDestroyed()
        )
        store.remove(activity)
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        LogUtil.i(
            TAG, activity?.getLocalClassName() + " was Created, " + "activity==null    "
                    + (activity == null) + ", activity.isFinishing()    " + activity?.isFinishing() + ", activity.isDestroyed()    " + activity?.isDestroyed()
        )
    }

    open fun getCurrentVisibleActivity(): Activity? {
        return visibleActivity
    }

    open fun isInForeground(): Boolean {
        return store.size > 0
    }

}