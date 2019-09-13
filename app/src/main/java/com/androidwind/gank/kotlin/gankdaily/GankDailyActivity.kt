package com.androidwind.gank.kotlin.gankdaily

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.androidwind.androidquick.ui.adapter.CommonViewHolder
import com.androidwind.androidquick.ui.adapter.MultiItemCommonAdapter
import com.androidwind.androidquick.ui.adapter.MultiItemTypeSupport
import com.androidwind.gank.kotlin.R
import com.androidwind.gank.kotlin.base.BaseActivity
import com.androidwind.gank.kotlin.bean.entity.GankBean
import com.androidwind.gank.kotlin.constant.Constants
import com.androidwind.gank.kotlin.util.DateUtil
import com.androidwind.gank.kotlin.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_daily.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankDailyActivity : BaseActivity(), GankDailyContract.View {

    private val mBundle by lazy {
        intent.extras
    }

    private val mDailyList = ArrayList<GankBean>()

    private val mDailyAdapter by lazy {
        initAdapter()
    }

    private val mPresenter by lazy {
        GankDailyPresent()
    }

    private fun initAdapter(): DailyAdapter {
        return DailyAdapter(this, mDailyList);
    }

    override fun initViewsAndEvents(savedInstanceState: Bundle?) {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@GankDailyActivity)
            setHasFixedSize(true)
            adapter = mDailyAdapter
        }

        mPresenter.attachView(this)
        val calendar = mBundle.getSerializable(Constants.GANK_DATE) as Calendar
        mPresenter.getDaily(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        showLoadingDialog()
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_daily
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun showDailyList(gankBeanList: List<GankBean>?) {
        mDailyAdapter.run {
            dismissLoadingDialog()
            gankBeanList?.let { mDailyList.addAll(it) }
            notifyDataSetChanged();
        }
    }

    inner class DailyAdapter(context: Context, datas: List<GankBean>) :
        MultiItemCommonAdapter<GankBean>(context, datas, object : MultiItemTypeSupport<GankBean> {
            override fun getLayoutId(itemType: Int): Int {
                return if (itemType == 1) {
                    R.layout.item_gankdaily_title
                } else {
                    R.layout.item_gankdaily_content
                }
            }

            override fun getItemViewType(position: Int, bean: GankBean): Int {
                return if (bean.source.equals(Constants.GANK_TYPE)) 1 else 2
            }

        }) {

        override fun convert(holder: CommonViewHolder, bean: GankBean) {
            if (holder.itemViewType == 1) {
                holder.setText(R.id.tv_title, bean.type)
            } else {
                holder.setText(R.id.tv_content, bean.desc)
                holder.setOnClickListener(R.id.tv_content) {
                    val bundle = Bundle()
                    bundle.putString(Constants.GANK_URL, bean.url)
                    readyGo(WebViewActivity::class.java, bundle)
                }
                holder.setText(R.id.tv_author, bean.who)
                holder.setText(R.id.tv_time, DateUtil.toDateString2(bean.publishedAt))
            }
        }
    }

}