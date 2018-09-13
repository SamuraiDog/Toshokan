package com.dog.samurai.toshokan.repository

import com.dog.samurai.toshokan.model.FlickrData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {
    @GET("rest/")
    fun getData(
            @Query("api_key") apiKey: String = "TEST",
            @Query("method") method: String = "flickr.photos.search",
            @Query("format") format: String = "json",
            @Query("text") text: String,
            @Query("extras") extras: String = "url_h",
            @Query("media") media: String = "photos",
            @Query("nojsoncallback") noJason: Int = 1,
            @Query("content_type") contentType: Int = 1
    ): Observable<FlickrData>
}

class FlickrRepository(private val apiService: FlickrApiService) {
    fun getData(searchWord: String): Observable<FlickrData> {
        return apiService.getData(text = searchWord)
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}
