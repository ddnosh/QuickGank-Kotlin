package com.androidwind.gank.kotlin.bean.entity

import java.io.Serializable
import java.util.*

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
open class BaseGankBean : Serializable {
    var _id: String? = null
    var used: Boolean = false
    var type: String? = null
    var url: String? = null
    var who: String? = null
    var desc: String? = null
    var createdAt: Date? = null
    var publishedAt: Date? = null
    var source: String? = null
}