package com.dog.samurai.pyramid.viewModel

import android.arch.lifecycle.ViewModel
import com.dog.samurai.pyramid.RepositoryProvider
import com.dog.samurai.pyramid.model.Prefectures
import com.dog.samurai.pyramid.model.Pyramid
import com.dog.samurai.pyramid.model.Result
import com.dog.samurai.pyramid.repository.ResasRepository
import io.reactivex.Observable
import javax.inject.Inject

class ResasViewModel : ViewModel() {

    private lateinit var resasRepository: ResasRepository
    var prefectureMap = listOf<Prefectures>()

    @Inject
    fun init(repositoryProvider: RepositoryProvider) {
        resasRepository = repositoryProvider.resasRepository()
    }

    fun getPrefecture(): Observable<Result> {
        return resasRepository.getPrefecture().doOnNext {
            prefectureMap = it.result
        }
    }

    fun getPyramid(year: Int, prefCode: Int): Observable<Pyramid> {
        return resasRepository.getPyramid(prefCode, year)
    }
}

