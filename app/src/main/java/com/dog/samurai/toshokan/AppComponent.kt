package com.dog.samurai.toshokan

import android.app.Application
import com.dog.samurai.toshokan.viewModel.FlickrViewModel
import com.dog.samurai.toshokan.viewModel.ResasViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(app: App)

    /**
     * ViewModelsにRepository-providerをinjectする
     */
    fun inject(resasViewModel: ResasViewModel)

    fun inject(flickrViewModel: FlickrViewModel)

    companion object Factory {
        fun create(app: Application): AppComponent {
            return DaggerAppComponent.builder().appModule(AppModule(app))
                    .build()
        }
    }
}

