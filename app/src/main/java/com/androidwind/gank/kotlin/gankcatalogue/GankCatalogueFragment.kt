package com.androidwind.gank.kotlin.gankcatalogue

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.androidwind.gank.kotlin.MyApplication
import com.androidwind.gank.kotlin.R
import com.androidwind.gank.kotlin.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_catalogue.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankCatalogueFragment : BaseFragment() {

    private val tabs by lazy {
        MyApplication.getInstance().resources
            .getStringArray(R.array.subtabs)
    }

    companion object {
        fun newInstance(): GankCatalogueFragment {
            return GankCatalogueFragment()
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fragment_catalogue
    }

    override fun initViewsAndEvents(savedInstanceState: Bundle?) {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(GankItemFragment.newInstance("Android"))
        fragmentList.add(GankItemFragment.newInstance("iOS"))
        fragmentList.add(GankItemFragment.newInstance("前端"))
        fragmentList.add(GankItemFragment.newInstance("瞎推荐"))
        fragmentList.add(GankItemFragment.newInstance("拓展资源"))
        fragmentList.add(GankItemFragment.newInstance("App"))
        fragmentList.add(GankItemFragment.newInstance("休息视频"))

        val pagerAdapter = MyFragmentPagerAdapter(supportFragmentManager, fragmentList)

        view_pager.apply {
            offscreenPageLimit = tabs.size
            adapter = pagerAdapter
        }

        smart_tab_layout.apply {
            setViewPager(view_pager)
        }
    }

    inner class MyFragmentPagerAdapter(
        private val fragmentmanager: FragmentManager,
        private val fragmentList: List<Fragment>
    ) :
        FragmentPagerAdapter(fragmentmanager) {

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabs[position].toString()
        }
    }

}