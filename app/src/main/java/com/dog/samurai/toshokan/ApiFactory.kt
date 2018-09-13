package com.dog.samurai.toshokan

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ApiFactory {
    companion object {
        inline fun <reified T> createRetrofit(baseUrl: String, header: String?): T {
            val builder = OkHttpClient().newBuilder()
            builder.connectTimeout(30, TimeUnit.SECONDS)
            builder.readTimeout(30, TimeUnit.SECONDS)
            builder.writeTimeout(60, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
                builder.addInterceptor(interceptor)
            }

            builder.addInterceptor { chain ->
                val request = if (header.isNullOrEmpty()) {
                    chain.request().newBuilder()
                            .header("User-Agent", "android")
                            .build()
                } else {
                    chain.request().newBuilder()
                            .header("User-Agent", "android")
                            .header("X-API-KEY", header!!)
                            .build()
                }
                chain.proceed(request)
            }

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                    .baseUrl(baseUrl)
                    .client(builder.build())
                    .build()

            return retrofit.create(T::class.java)

        }
    }
}