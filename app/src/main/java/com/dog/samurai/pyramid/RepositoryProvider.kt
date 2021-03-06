package com.dog.samurai.pyramid

import com.dog.samurai.pyramid.repository.FlickrApiService
import com.dog.samurai.pyramid.repository.FlickrRepository
import com.dog.samurai.pyramid.repository.ResasApiService
import com.dog.samurai.pyramid.repository.ResasRepository
import javax.inject.Inject


class RepositoryProvider @Inject constructor() {
    private var resasRepository: ResasRepository? = null
    private var flickrRepository: FlickrRepository? = null

    fun resasRepository(): ResasRepository {
        if (resasRepository == null) {
            val service = ApiFactory.createRetrofit<ResasApiService>(RESAS_BASE_URL, RESAS_API_KEY)
            resasRepository = ResasRepository(service)
        }
        return resasRepository as ResasRepository
    }

    fun flickrRepository(): FlickrRepository {
        if (flickrRepository == null) {
            val service = ApiFactory.createRetrofit<FlickrApiService>(FLICKR_BASE_URL, null)
            flickrRepository = FlickrRepository(service)
        }
        return flickrRepository as FlickrRepository
    }

}