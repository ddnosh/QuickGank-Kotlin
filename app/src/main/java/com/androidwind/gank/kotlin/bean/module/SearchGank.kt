package com.androidwind.gank.kotlin.bean.module

import com.androidwind.gank.kotlin.bean.entity.SearchBean

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class SearchGank : BaseGank() {
    var results: List<SearchBean>? = null

    override fun toString(): String {
        return "SearchGank{" +
                "results=" + results +
                ", error=" + error +
                '}'.toString()
    }
}