package com.dog.samurai.toshokan.repository

import com.dog.samurai.toshokan.model.Result
import com.dog.samurai.toshokan.model.VisitorResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.http.Query

interface ResasApiService {
    @GET("prefectures")
    fun getPrefecture(): Observable<Result>

    @GET("tourism/foreigners/forFrom")
    fun getFromData(
            @Query("year") year: Int,
            @Query("prefCode") prefCode: Int,
            @Query("purpose") purpose: Int = 1
    ): Observable<VisitorResult>
}

class ResasRepository(private val apiService: ResasApiService) {
    fun getPrefecture(): Observable<Result> {
        return apiService.getPrefecture()
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getFromData(year: Int, prefCode: Int): Observable<VisitorResult> {
        return apiService.getFromData(year, prefCode)
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

    }
}

//BqcZxTcIqZGrC0zDBHJQfyImYWkS8Hcj6psU3J5y