package com.dog.samurai.toshokan

import android.app.Application
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(app: App)

    /**
     * ViewModelsにRepository-providerをinjectする
     */
    fun inject(toshoViewModel: ToshoViewModel)
    fun inject(prefViewModel: PrefViewModel)

    companion object Factory {
        fun create(app: Application): AppComponent {
            return DaggerAppComponent.builder().appModule(AppModule(app))
                    .build()
        }
    }
}

