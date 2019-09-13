package com.androidwind.gank.kotlin.ganklist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.androidwind.androidquick.module.glide.GlideManager
import com.androidwind.androidquick.ui.adapter.CommonAdapter
import com.androidwind.androidquick.ui.adapter.CommonViewHolder
import com.androidwind.gank.kotlin.R
import com.androidwind.gank.kotlin.base.BaseFragment
import com.androidwind.gank.kotlin.bean.entity.GankBean
import com.androidwind.gank.kotlin.bean.module.SimpleGank
import com.androidwind.gank.kotlin.constant.Constants
import com.androidwind.gank.kotlin.gankdaily.GankDailyActivity
import com.androidwind.gank.kotlin.gankgirl.GankGirlFragment
import com.androidwind.gank.kotlin.util.DateUtil
import com.androidwind.gank.kotlin.webview.WebViewActivity
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankListFragment : BaseFragment(), GankListContract.View {

    private val TAG = "GankListFragment";
    private val mCatalogueList = ArrayList<GankBean>()
    private var page = 1
    private val mPresenter by lazy { GankListPresenter() }

    private val mAdapter by lazy {
        activity?.let {
            initAdapter()
        }
    }

    companion object {
        fun newInstance(): GankListFragment {
            return GankListFragment()
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fragment_list
    }

    override fun initViewsAndEvents(savedInstanceState: Bundle?) {
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter;
        }

        smart_refresh_layout.setOnRefreshListener {
            mCatalogueList.clear()
            page = 1
            mPresenter.initData(page)
            smart_refresh_layout.finishRefresh()
        }
        smart_refresh_layout.setOnLoadMoreListener {
            mPresenter.initData(++page)
            smart_refresh_layout.finishLoadMore()
        }

        mPresenter.attachView(this)
        mPresenter.initData(page)

        showLoadingDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    private fun initAdapter(): CommonAdapter<GankBean> {
        return object : CommonAdapter<GankBean>(context!!, R.layout.item_ganklist, mCatalogueList) {
            override fun convert(holder: CommonViewHolder, bean: GankBean) {
                GlideManager.loadNet(bean.url, holder.getView(R.id.iv_girl))
                holder.setText(R.id.tv_date, DateUtil.toDateString2(bean.publishedAt))
                holder.setOnClickListener(R.id.cl_item) { v ->
                    val calendar = Calendar.getInstance()
                    calendar.time = bean.publishedAt
                    val bundle = Bundle()
                    bundle.putSerializable(Constants.GANK_DATE, calendar)
                    readyGo(GankDailyActivity::class.java, bundle)
                }
                holder.setOnClickListener(R.id.iv_girl) { v ->
                    val bundle = Bundle()
                    bundle.putString(Constants.GANK_URL, bean.url)
                    readyGo(GankGirlFragment::class.java, bundle)
                }
                holder.setText(R.id.tv_rest, bean.desc)
                holder.setOnClickListener(R.id.tv_rest) { v ->
                    val bundle = Bundle()
                    bundle.putString(Constants.GANK_URL, bean.playUrl)
                    readyGo(WebViewActivity::class.java, bundle)
                }
            }
        }
    }

    override fun showGirlList(girl: SimpleGank) {
        mAdapter?.run {
            dismissLoadingDialog()
            girl.results?.let { mCatalogueList.addAll(it) }
            update(mCatalogueList)
        }
    }
}