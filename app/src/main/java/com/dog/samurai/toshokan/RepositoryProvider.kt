package com.dog.samurai.toshokan

import android.content.Context
import com.dog.samurai.toshokan.repository.*
import javax.inject.Inject


class RepositoryProvider @Inject constructor(val context: Context) {
    private var resasRepository: ResasRepository? = null
    private var flickrRepository: FlickrRepository? = null

    fun resasRepository(): ResasRepository {
        if (resasRepository == null) {
            val service = ApiFactory.createRetrofit<ResasApiService>("https://opendata.resas-portal.go.jp/api/v1/", "")
            resasRepository = ResasRepository(service)
        }
        return resasRepository as ResasRepository
    }

    fun flickrRepository(): FlickrRepository {
        if (flickrRepository == null) {
            val service = ApiFactory.createRetrofit<FlickrApiService>( "https://api.flickr.com/services/", null)
            flickrRepository = FlickrRepository(service)
        }
        return flickrRepository as FlickrRepository
    }

}