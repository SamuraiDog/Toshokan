package com.dog.samurai.toshokan

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {

    @Provides
    fun provideApp(): Application = app

    @Provides
    @Singleton
    fun provideRepositoryProvider(): RepositoryProvider = RepositoryProvider(app)
}
