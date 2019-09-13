package com.androidwind.gank.kotlin.ganklist

import com.androidwind.androidquick.ui.mvp.BasePresenter
import com.androidwind.androidquick.util.LogUtil
import com.androidwind.androidquick.util.RxUtil
import com.androidwind.gank.kotlin.MyApplication
import com.androidwind.gank.kotlin.api.GankApis
import com.androidwind.gank.kotlin.bean.module.SimpleGank
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankListPresenter : BasePresenter<GankListContract.View>(), GankListContract.Presenter {

    private val TAG = "GankListPresenter"

    override fun initData(page: Int) {
        var gankDaysApis = MyApplication.getRetrofit().createApi(GankApis::class.java)

        addSubscribe(
            Observable.zip(gankDaysApis.getGankList("福利", page),
                gankDaysApis.getGankList("休息视频", page),
                BiFunction<SimpleGank, SimpleGank, SimpleGank> { girl, rest -> composeWithGirlAndRest(girl, rest) })
                .compose(RxUtil.applySchedulers())
                .compose(view?.bindToLife())
                .subscribe({ girl ->
                    LogUtil.i(TAG, girl.toString())
                    view?.apply {
                        showGirlList(girl)
                    }
                }, { t ->
                    LogUtil.e(TAG, "error:" + t.message)
                })
        )
    }

    private fun composeWithGirlAndRest(girl: SimpleGank, rest: SimpleGank): SimpleGank {
        val size = Math.min(girl.results!!.size, rest.results!!.size)
        for (i in 0 until size) {
            girl.results!!.get(i).desc = rest.results!!.get(i).desc
            girl.results!!.get(i).who = rest.results!!.get(i).who
            girl.results!!.get(i).playUrl = rest.results!!.get(i).url
        }
        return girl
    }
}