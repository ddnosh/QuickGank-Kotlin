package com.androidwind.gank.kotlin.bean.module

import com.androidwind.gank.kotlin.bean.entity.GankBean

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class SimpleGank : BaseGank() {
    var results: List<GankBean>? = null

    override fun toString(): String {
        return "SimpleGank{" +
                "results=" + results +
                ", error=" + error +
                '}'.toString()
    }
}