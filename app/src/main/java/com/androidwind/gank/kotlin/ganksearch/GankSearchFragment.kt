package com.androidwind.gank.kotlin.ganksearch

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING
import android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.androidwind.androidquick.ui.adapter.CommonAdapter
import com.androidwind.androidquick.ui.adapter.CommonViewHolder
import com.androidwind.gank.kotlin.R
import com.androidwind.gank.kotlin.base.BaseFragment
import com.androidwind.gank.kotlin.bean.entity.SearchBean
import com.androidwind.gank.kotlin.bean.module.SearchGank
import com.androidwind.gank.kotlin.constant.Constants
import com.androidwind.gank.kotlin.webview.WebViewActivity
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankSearchFragment : BaseFragment(), GankSearchContract.View {

    private val TAG = "GankSearchFragment"
    private val mSearchList = ArrayList<SearchBean>()
    private var page = 1
    private val mAdapter by lazy {
        initAdapter()
    }
    private val mPresenter by lazy {
        GankSearchPresenter()
    }

    companion object {
        fun newInstance(): GankSearchFragment {
            return GankSearchFragment()
        }
    }

    private fun initAdapter(): CommonAdapter<SearchBean> {
        return object : CommonAdapter<SearchBean>(activity, R.layout.item_gankdaily_content, mSearchList) {
            override fun convert(holder: CommonViewHolder, bean: SearchBean) {
                holder.setText(R.id.tv_content, bean.desc)
                holder.setOnClickListener(R.id.cl_daily) {
                    val bundle = Bundle()
                    bundle.putString(Constants.GANK_URL, bean.url)
                    readyGo(WebViewActivity::class.java, bundle)
                }
                holder.setText(R.id.tv_author, bean.who)
                holder.setText(R.id.tv_time, bean.publishedAt!!.split("T")[0])
            }
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fragment_search
    }

    override fun initViewsAndEvents(savedInstanceState: Bundle?) {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = mAdapter
            addOnScrollListener(object : GankSearchFragment.OnLoadMoreListener() {
                override fun onLoading(countItem: Int, lastItem: Int) {
                    showLoadingDialog()
                    mPresenter.initData(ev_search.text.toString().trim { it <= ' ' }, ++page)
                }
            })
        }

        ev_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mSearchList.clear()
                    page = 1
                    mPresenter.initData(ev_search.text.toString().trim { it <= ' ' }, page)
                    return true
                }
                return false
            }

        })

        swipe_refresh_layout.apply {
            setProgressBackgroundColorSchemeResource(android.R.color.white)
            setOnRefreshListener {
                mSearchList.clear()
                page = 1
                mPresenter.initData(ev_search.text.toString().trim { it <= ' ' }, page)
            }
        }

        mPresenter.attachView(this)
    }

    override fun showSearchList(searchGank: SearchGank) {
        swipe_refresh_layout.isRefreshing = false
        dismissLoadingDialog()
        searchGank.results?.let {
            mSearchList.addAll(it)
            mAdapter.run {
                update(mSearchList)
            }
        }
    }

    private abstract inner class OnLoadMoreListener : RecyclerView.OnScrollListener() {
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