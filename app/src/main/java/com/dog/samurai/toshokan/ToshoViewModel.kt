package com.dog.samurai.toshokan

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import javax.inject.Inject

class ToshoViewModel : ViewModel() {

    private lateinit var toshoRepository: ToshoRepository

    @Inject
    fun init(repositoryProvider: RepositoryProvider) {
        toshoRepository = repositoryProvider.toshoRepository()
    }

    fun getData(id: String, pref:String?) : Observable<List<ToshoModel>>{
        return toshoRepository.getData(id, pref).doOnNext {

        }
    }
}

