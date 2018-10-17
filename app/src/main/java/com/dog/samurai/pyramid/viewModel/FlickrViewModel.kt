package com.dog.samurai.pyramid.viewModel

import android.arch.lifecycle.ViewModel
import com.dog.samurai.pyramid.RepositoryProvider
import com.dog.samurai.pyramid.model.FlickrData
import com.dog.samurai.pyramid.repository.FlickrRepository
import io.reactivex.Observable
import javax.inject.Inject

class FlickrViewModel : ViewModel() {

    private lateinit var flickrRepository: FlickrRepository

    @Inject
    fun init(repositoryProvider: RepositoryProvider) {
        flickrRepository = repositoryProvider.flickrRepository()
    }

    fun getData(searchWord: String): Observable<FlickrData> {
        return flickrRepository.getData(searchWord)
    }
}

