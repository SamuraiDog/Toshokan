package com.dog.samurai.toshokan

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.http.Query

interface ToshoApiService {
    @GET("library")
    fun getData(@Query("appkey") id: String,
                @Query("pref") pref: String? = null,
                @Query("city") city: String? = null,
                @Query("systemid") systemid: String? = null,
                @Query("geocode") geocode: String? = null,
                @Query("format") format: String? = "json",
                @Query("callback") callback: String = ""
    ): Observable<List<ToshoModel>>
}

class ToshoRepository(private val apiService: ToshoApiService) {
    fun getData(id: String, pref:String?): Observable<List<ToshoModel>> {
        return apiService.getData(id, pref)
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}