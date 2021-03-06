package com.dog.samurai.pyramid

import android.app.Application
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {

    @Provides
    fun provideApp(): Application = app

    @Provides
    @Singleton
    fun provideRepositoryProvider(): RepositoryProvider = RepositoryProvider()
}


@GlideModule
class MyAppGlideModule : AppGlideModule()
