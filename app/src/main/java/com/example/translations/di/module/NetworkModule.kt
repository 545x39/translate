package com.example.translations.di.module

import com.example.translations.BuildConfig
import com.example.translations.framework.datasource.remote.Api
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val API_URL = "https://translate.api.cloud.yandex.net/translate/v2/"
private const val CONNECT_TIMEOUT = 5L
private const val READ_TIMEOUT = 20L

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        fun okHttp(): OkHttpClient = (OkHttpClient.Builder()).apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(HttpLoggingInterceptor()
                .apply { level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE }
            )
        }.build()

        return Retrofit.Builder().apply {
            baseUrl(API_URL)
            client(okHttp())
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
        }.build()
    }

    @Singleton
    @Provides
    fun provideBaseApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}