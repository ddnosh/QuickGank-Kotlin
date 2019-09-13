package com.androidwind.gank.kotlin.gankcatalogue

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING
import android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING
import android.view.View
import com.androidwind.androidquick.ui.adapter.CommonAdapter
import com.androidwind.androidquick.ui.adapter.CommonViewHolder
import com.androidwind.gank.kotlin.R
import com.androidwind.gank.kotlin.base.BaseFragment
import com.androidwind.gank.kotlin.bean.entity.GankBean
import com.androidwind.gank.kotlin.bean.module.SimpleGank
import com.androidwind.gank.kotlin.constant.Constants
import com.androidwind.gank.kotlin.util.DateUtil
import com.androidwind.gank.kotlin.webview.WebViewActivity
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_item.*

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankItemFragment : BaseFragment(), GankItemContract.View {

    private var page = 1
    private val mCatalogueList = ArrayList<GankBean>()
    private val mPresenter by lazy {
        GankItemPresenter()
    }
    private val mAdapter by lazy {
        activity?.let {
            initAdapter()
        }
    }

    private fun initAdapter(): CommonAdapter<GankBean> {
        return object : CommonAdapter<GankBean>(activity, R.layout.item_gankdaily_content, mCatalogueList) {
            override fun convert(holder: CommonViewHolder, bean: GankBean) {
                holder.setText(R.id.tv_content, bean.desc)
                holder.setOnClickListener(R.id.cl_daily) {
                    val bundle = Bundle()
                    bundle.putString(Constants.GANK_URL, bean.url)
                    readyGo(WebViewActivity::class.java, bundle)
                }
                holder.setText(R.id.tv_author, bean.who)
                holder.setText(R.id.tv_time, DateUtil.toDateString2(bean.publishedAt))
            }
        };
    }

    companion object {
        fun newInstance(catalogue: String): GankItemFragment {
            val fragment = GankItemFragment()
            return fragment.apply {
                val args = Bundle()
                args.putString(Constants.GANK_TYPE, catalogue)
                fragment.arguments = args
            }
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fragment_item
    }

    override fun initViewsAndEvents(savedInstanceState: Bundle?) {
        val catalogue = arguments?.get(Constants.GANK_TYPE).toString()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = mAdapter
            addOnScrollListener(
                object : OnLoadMoreListener() {
                    override fun onLoading(countItem: Int, lastItem: Int) {
                        showLoadingDialog()
                        mPresenter.initData(catalogue, ++page)
                    }
                }
            )
        }

        swipe_refresh_layout.apply {
            setProgressBackgroundColorSchemeResource(android.R.color.white)
            setOnRefreshListener {
                mCatalogueList.clear();
                page = 1;
                mPresenter.initData(catalogue, page);
            };
        }

        mPresenter.attachView(this)
        mPresenter.initData(catalogue, page)

        showLoadingDialog()
    }

    override fun showItemData(item: SimpleGank) {
        swipe_refresh_layout.apply {
            isRefreshing = false
        }
        dismissLoadingDialog()
        item.results?.let {
            mCatalogueList.addAll(it)
            mAdapter?.update(mCatalogueList)
        }
    }

    abstract inner class OnLoadMoreListener : RecyclerView.OnScrollListener() {
        private var countItem: Int = 0
        private var lastItem: Int = 0
        private var isScolled = false
        private var layoutManager: RecyclerView.LayoutManager? = null

        protected abstract fun onLoading(countItem: Int, lastItem: Int)

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            isScolled = newState == SCROLL_STATE_DRAGGING || newState == SCROLL_STATE_SETTLING
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (recyclerView.layoutManager is LinearLayoutManager) {
                layoutManager = recyclerView.layoutManager
                countItem = layoutManager!!.itemCount
                lastItem = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            }
            if (isScolled && countItem != lastItem && lastItem == countItem - 1) {
                onLoading(countItem, lastItem)
            }
        }
    }
}