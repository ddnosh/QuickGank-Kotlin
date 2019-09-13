package com.androidwind.gank.kotlin.gankcatalogue

import com.androidwind.androidquick.ui.mvp.BasePresenter
import com.androidwind.androidquick.util.LogUtil
import com.androidwind.androidquick.util.RxUtil
import com.androidwind.gank.kotlin.MyApplication
import com.androidwind.gank.kotlin.api.GankApis

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankItemPresenter : BasePresenter<GankItemContract.View>(), GankItemContract.Presenter {

    private val TAG = "GankItemPresenter"

    override fun initData(catalogue: String, page: Int) {
        val gankDaysApis = MyApplication.getRetrofit().createApi(GankApis::class.java)
        addSubscribe(
            gankDaysApis.getGankList(catalogue, page)
                .compose(RxUtil.applySchedulers())
                .compose(view.bindToLife())
                .subscribe({ simpleGank ->
                    LogUtil.i(TAG, simpleGank.toString())
                    view.showItemData(simpleGank);
                }, { t ->
                    LogUtil.e(TAG, "error:" + t.message)
                })
        )
    }
}