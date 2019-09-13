package com.androidwind.gank.kotlin.ganklist

import com.androidwind.androidquick.ui.mvp.BaseContract
import com.androidwind.gank.kotlin.bean.module.SimpleGank

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
interface GankListContract {

    interface View : BaseContract.BaseView {
        fun showGirlList(girl: SimpleGank)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun initData(page: Int)
    }
}