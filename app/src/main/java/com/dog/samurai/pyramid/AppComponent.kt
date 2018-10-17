package com.dog.samurai.pyramid

import android.app.Application
import com.dog.samurai.pyramid.viewModel.FlickrViewModel
import com.dog.samurai.pyramid.viewModel.ResasViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(app: App)

    fun inject(resasViewModel: ResasViewModel)

    fun inject(flickrViewModel: FlickrViewModel)

    companion object Factory {
        fun create(app: Application): AppComponent {
            return DaggerAppComponent.builder().appModule(AppModule(app))
                    .build()
        }
    }
}

