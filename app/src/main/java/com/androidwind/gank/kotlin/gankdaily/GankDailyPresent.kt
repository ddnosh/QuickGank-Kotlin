package com.androidwind.gank.kotlin.gankdaily

import com.androidwind.gank.kotlin.api.GankApis
import com.androidwind.androidquick.ui.mvp.BasePresenter
import com.androidwind.androidquick.util.LogUtil
import com.androidwind.androidquick.util.RxUtil
import com.androidwind.gank.kotlin.MyApplication
import com.androidwind.gank.kotlin.bean.entity.GankBean
import com.androidwind.gank.kotlin.bean.module.FullGank
import com.androidwind.gank.kotlin.constant.Constants
import java.util.ArrayList

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankDailyPresent : BasePresenter<GankDailyContract.View>(), GankDailyContract.Presenter {

    private val TAG = "GankDailyPresenter";

    override fun getDaily(year: Int, month: Int, day: Int) {
        val gankDaysApis = MyApplication.getRetrofit().createApi(GankApis::class.java!!)
        addSubscribe(
            gankDaysApis.getGankDaily(year, month, day)
                .compose(RxUtil.applySchedulers())
                .compose(view?.bindToLife())
                .subscribe({ gank ->
                    LogUtil.i(TAG, gank.toString())
                    view?.showDailyList(composeGankList(gank.results))
                }, { t ->
                    LogUtil.e(TAG, "error:" + t.message)
                })
        )
    }

    private fun composeGankList(results: FullGank.Result?): List<GankBean> {
        val mGankList = ArrayList<GankBean>()
        results?.androidList?.let {
            mGankList.add(insertGankBean("Android"))
            mGankList.addAll(it);
        }
        results?.iOSList?.let {
            mGankList.add(insertGankBean("iOS"))
            mGankList.addAll(it)
        }
        results?.frontList?.let {
            mGankList.add(insertGankBean("前端"))
            mGankList.addAll(it)
        }
        results?.appList?.let {
            mGankList.add(insertGankBean("App"))
            mGankList.addAll(it)
        }
        results?.outerList?.let {
            mGankList.add(insertGankBean("拓展资源"))
            mGankList.addAll(it)
        }
        results?.recommendList?.let {
            mGankList.add(insertGankBean("瞎推荐"))
            mGankList.addAll(it)
        }
        results?.restList?.let {
            mGankList.add(insertGankBean("休息视频"))
            mGankList.addAll(it)
        }
        return mGankList
    }

    private fun insertGankBean(title: String): GankBean {
        val gankBean = GankBean()
        gankBean.source = Constants.GANK_TYPE
        gankBean.type = title
        return gankBean
    }
}