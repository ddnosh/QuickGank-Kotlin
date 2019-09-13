package com.androidwind.gank.kotlin.ui


import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import com.androidwind.androidquick.ui.view.CommonToolBar
import com.androidwind.androidquick.util.ToastUtil
import com.androidwind.gank.kotlin.R
import com.androidwind.gank.kotlin.base.BaseActivity
import com.androidwind.gank.kotlin.bean.entity.TabEntity
import com.androidwind.gank.kotlin.gankabout.GankAboutActivity
import com.androidwind.gank.kotlin.gankcatalogue.GankCatalogueFragment
import com.androidwind.gank.kotlin.ganklist.GankListFragment
import com.androidwind.gank.kotlin.ganksearch.GankSearchFragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class MainActivity : BaseActivity() {
    private var TAG = "MainActivity"
    private var DOUBLE_CLICK_TIME = 0L
    private val mTitles = arrayOf("日报", "目录", "搜索")
    private val mSelectedIcon =
        intArrayOf(R.mipmap.ic_daily_selected, R.mipmap.ic_catalogue_selected, R.mipmap.ic_search_selected)
    private val mNormalIcon =
        intArrayOf(R.mipmap.ic_daily_normal, R.mipmap.ic_catalogue_normal, R.mipmap.ic_search_normal)
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private val rxPermissions by lazy { RxPermissions(this) }
    private val mFragments = ArrayList<Fragment>();

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_MENU) {
            return true
        } else if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - DOUBLE_CLICK_TIME > 2000) {
                ToastUtil.showToast("再按一次退出")
                DOUBLE_CLICK_TIME = currentTime
            } else {
                System.gc()
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun initViewsAndEvents(savedInstanceState: Bundle?) {
        permissionsCheck()
        //title bar
        common_tool_bar.setOnTopBarClickListener(
            object : CommonToolBar.OnToolBarClickListener {
                override fun onLeftClick() {

                }

                override fun onRightClick() {
                    readyGo(GankAboutActivity::class.java)
                }
            }
        )
        //bottom bar
        for (i in 0 until mTitles.size) {
            mTabEntities.add(
                TabEntity(
                    mTitles[i],
                    mSelectedIcon[i],
                    mNormalIcon[i]
                )
            )
        }
        mFragments.add(GankListFragment.newInstance())
        mFragments.add(GankCatalogueFragment.newInstance())
        mFragments.add(GankSearchFragment.newInstance())
        bottom_bar.setTabData(mTabEntities, this, R.id.container, mFragments)
    }

    @SuppressLint("CheckResult")
    private fun permissionsCheck() {
        rxPermissions
            .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe { granted ->
                if (granted!!) { // Always true pre-M

                } else {

                }
            }
    }
}
