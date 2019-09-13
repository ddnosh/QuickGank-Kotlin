package com.androidwind.gank.kotlin.bean.module

import java.io.Serializable

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
open class BaseGank : Serializable {
    var error: Boolean = false

    override fun toString(): String {
        return "Base{" +
                "error=" + error +
                '}'.toString()
    }
}