package com.androidwind.gank.kotlin.bean.entity

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankBean : BaseGankBean() {

    var playUrl: String? = null
    var images: List<String>? = null

    override fun toString(): String {
        return "GankBean{" +
                "playUrl='" + playUrl + '\''.toString() +
                ", images=" + images +
                ", _id='" + _id + '\''.toString() +
                ", used=" + used +
                ", type='" + type + '\''.toString() +
                ", url='" + url + '\''.toString() +
                ", who='" + who + '\''.toString() +
                ", desc='" + desc + '\''.toString() +
                ", createdAt=" + createdAt +
                ", publishedAt=" + publishedAt +
                ", source='" + source + '\''.toString() +
                '}'.toString()
    }
}