package com.androidwind.gank.kotlin.gankcatalogue

import com.androidwind.androidquick.ui.mvp.BaseContract
import com.androidwind.gank.kotlin.bean.module.SimpleGank

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
interface GankItemContract {
    interface View : BaseContract.BaseView {
        fun showItemData(simpleGank: SimpleGank)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun initData(catalogue: String, page: Int)
    }
}