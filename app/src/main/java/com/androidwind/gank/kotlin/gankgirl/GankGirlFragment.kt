package com.androidwind.gank.kotlin.gankgirl

import android.graphics.Bitmap
import android.os.Bundle
import com.androidwind.androidquick.module.glide.GlideManager
import com.androidwind.androidquick.module.glide.support.ImageListener
import com.androidwind.gank.kotlin.R
import com.androidwind.gank.kotlin.base.BaseFragment
import com.androidwind.gank.kotlin.constant.Constants
import kotlinx.android.synthetic.main.fragment_girl.*

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class GankGirlFragment : BaseFragment() {

    private val mBundle by lazy {
        activity?.intent?.extras
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fragment_girl
    }

    override fun initViewsAndEvents(savedInstanceState: Bundle?) {
        val url = mBundle?.getString(Constants.GANK_URL)
        GlideManager.getBitmap(activity, url, object : ImageListener<Bitmap> {
            override fun onSuccess(result: Bitmap?) {
                iv_girl.setImageBitmap(result)
            }

            override fun onFail(throwable: Throwable?) {

            }

        })
    }

}