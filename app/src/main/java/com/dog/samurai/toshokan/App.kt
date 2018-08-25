package com.dog.samurai.toshokan

import android.app.Application

class App : Application() {

    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = AppComponent.create(this)
    }
}