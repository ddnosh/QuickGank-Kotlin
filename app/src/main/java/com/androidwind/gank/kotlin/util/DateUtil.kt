package com.androidwind.gank.kotlin.util

import java.util.*

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
object DateUtil {
    fun toDateString1(date: Date): StringBuilder {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return StringBuilder("$year-$month-$day")
    }

    fun toDateString2(date: Date?): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return year.toString() + "年" + month + "月" + day + "日"
    }
}