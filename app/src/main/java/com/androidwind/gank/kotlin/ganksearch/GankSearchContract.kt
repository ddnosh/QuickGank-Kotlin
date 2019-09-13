package com.androidwind.gank.kotlin.ganksearch

import com.androidwind.androidquick.ui.mvp.BaseContract
import com.androidwind.gank.kotlin.bean.module.SearchGank

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
interface GankSearchContract {
    interface View : BaseContract.BaseView {
        fun showSearchList(searchGank: SearchGank)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun initData(search: String, page: Int)
    }
}