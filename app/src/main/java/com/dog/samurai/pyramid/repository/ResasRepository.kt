package com.dog.samurai.pyramid.repository

import com.dog.samurai.pyramid.model.Pyramid
import com.dog.samurai.pyramid.model.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.http.Query

interface ResasApiService {
    @GET("prefectures")
    fun getPrefecture(): Observable<Result>

    @GET("population/composition/pyramid")
    fun getPyramid(
            @Query("prefCode") prefCode: Int,
            @Query("yearLeft") yearLeft: Int,
            @Query("yearRight") yearRight: Int,
            @Query("cityCode") cityCode: String = "-"
    ): Observable<Pyramid>
}

class ResasRepository(private val apiService: ResasApiService) {
    fun getPrefecture(): Observable<Result> {
        return apiService.getPrefecture()
                .retry(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getPyramid(prefCode: Int, year: Int): Observable<Pyramid> {
        return apiService.getPyramid(prefCode, year, year)
                .retry(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
