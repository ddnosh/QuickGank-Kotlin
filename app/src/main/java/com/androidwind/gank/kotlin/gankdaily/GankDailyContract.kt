package com.androidwind.gank.kotlin.gankdaily

import com.androidwind.androidquick.ui.mvp.BaseContract
import com.androidwind.gank.kotlin.bean.entity.GankBean

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
interface GankDailyContract {

    interface View : BaseContract.BaseView {
        fun showDailyList(gankBeanList: List<GankBean>?)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun getDaily(year: Int, month: Int, day: Int)
    }
}