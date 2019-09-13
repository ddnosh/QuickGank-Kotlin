package com.androidwind.gank.kotlin.ganksearch

import com.androidwind.androidquick.module.retrofit.exeception.ApiException
import com.androidwind.androidquick.module.rxjava.BaseObserver
import com.androidwind.androidquick.ui.mvp.BasePresenter
import com.androidwind.androidquick.util.LogUtil
import com.androidwind.androidquick.util.RxUtil
import com.androidwind.gank.kotlin.MyApplication
import com.androidwind.gank.kotlin.api.GankApis
import com.androidwind.gank.kotlin.bean.module.SearchGank

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankSearchPresenter : BasePresenter<GankSearchContract.View>(), GankSearchContract.Presenter {

    private val TAG = "GankSearchPresenter"

    override fun initData(search: String, page: Int) {
        val gankDaysApis = MyApplication.getRetrofit().createApi(GankApis::class.java!!)
        addSubscribe(
            gankDaysApis.getGankSearch(search, page)
                .compose(RxUtil.applySchedulers())
                .compose(view.bindToLife())
                .subscribe({ searchGank ->
                    LogUtil.i(TAG, searchGank.toString())
                    view.showSearchList(searchGank);
                }, { t ->
                    LogUtil.e(TAG, "error:" + t.message)
                })
        )
    }

}