package com.dog.samurai.toshokan.viewModel

import android.arch.lifecycle.ViewModel
import com.dog.samurai.toshokan.RepositoryProvider
import com.dog.samurai.toshokan.model.FlickrData
import com.dog.samurai.toshokan.repository.FlickrRepository
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

