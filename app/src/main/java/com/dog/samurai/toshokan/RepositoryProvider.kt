package com.dog.samurai.toshokan

import android.content.Context
import javax.inject.Inject


class RepositoryProvider @Inject constructor(val context: Context) {
    private var toshoRepository: ToshoRepository? = null
    private var prefRepository: PrefRepository? = null

    fun toshoRepository(): ToshoRepository {
        if (toshoRepository == null) {
            val service = ApiFactory.createRetrofit<ToshoApiService>(context, "http://api.calil.jp/", null)
            toshoRepository = ToshoRepository(service)
        }
        return toshoRepository as ToshoRepository
    }

    fun prefRepository(): PrefRepository {
        if (prefRepository == null) {
            val service = ApiFactory.createRetrofit<PrefApiService>(context, "https://opendata.resas-portal.go.jp/api/v1/", "")
            prefRepository = PrefRepository(service)
        }
        return prefRepository as PrefRepository
    }
}