package com.androidwind.gank.kotlin.bean.module

import com.androidwind.gank.kotlin.bean.entity.GankBean
import com.google.gson.annotations.SerializedName

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class FullGank : BaseGank() {
    var category: List<String>? = null
    var results: Result? = null

    inner class Result {
        @SerializedName("Android")
        var androidList: List<GankBean>? = null
        @SerializedName("休息视频")
        var restList: List<GankBean>? = null
        @SerializedName("iOS")
        var iOSList: List<GankBean>? = null
        @SerializedName("福利")
        var girlList: List<GankBean>? = null
        @SerializedName("拓展资源")
        var outerList: List<GankBean>? = null
        @SerializedName("瞎推荐")
        var recommendList: List<GankBean>? = null
        @SerializedName("App")
        var appList: List<GankBean>? = null
        @SerializedName("前端")
        var frontList: List<GankBean>? = null
    }

    override fun toString(): String {
        return "FullGank{" +
                "category=" + category +
                ", results=" + results +
                ", error=" + error +
                '}'.toString()
    }
}