package com.dog.samurai.toshokan

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import javax.inject.Inject

class PrefViewModel : ViewModel() {

    private lateinit var prefRepository: PrefRepository

    @Inject
    fun init(repositoryProvider: RepositoryProvider) {
        prefRepository = repositoryProvider.prefRepository()
    }

    fun getData(): Observable<Result> {
        return prefRepository.getData().doOnNext {

        }
    }
}

