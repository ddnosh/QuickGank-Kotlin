package com.androidwind.gank.kotlin.bean.entity

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class TabEntity constructor(var title: String, var selectedIcon: Int, var unSelectedIcon: Int) : CustomTabEntity {

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }
}