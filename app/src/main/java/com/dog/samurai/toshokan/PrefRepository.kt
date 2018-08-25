package com.dog.samurai.toshokan

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.http.Query

interface PrefApiService {
    @GET("prefectures")
    fun getData(): Observable<Result>
}

class PrefRepository(private val apiService: PrefApiService) {
    fun getData(): Observable<Result> {
        return apiService.getData()
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}

//BqcZxTcIqZGrC0zDBHJQfyImYWkS8Hcj6psU3J5y