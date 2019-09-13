package com.androidwind.gank.kotlin.api

import com.androidwind.gank.kotlin.bean.module.FullGank
import com.androidwind.gank.kotlin.bean.module.SearchGank
import com.androidwind.gank.kotlin.bean.module.SimpleGank
import com.androidwind.gank.kotlin.constant.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
interface GankApis {

    //for gank list
    @GET(value = "data/{catalogue}/" + Constants.CATALOGUE_SIZE + "/{page}")
    fun getGankList(@Path("catalogue") catalogue: String, @Path("page") page: Int): Observable<SimpleGank>

    //for gank daily
    @GET("day/{year}/{month}/{day}")
    fun getGankDaily(@Path("year") year: Int, @Path("month") month: Int, @Path("day") day: Int): Observable<FullGank>

    //for gank search
    @GET("search/query/{search}/category/all/count/" + Constants.CATALOGUE_SIZE + "/page" + "/{page}")
    fun getGankSearch(@Path("search") search: String, @Path("page") page: Int): Observable<SearchGank>

}