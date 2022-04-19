package com.example.translations.di.module

import com.example.translations.BuildConfig
import com.example.translations.framework.datasource.implementation.remote.API_URL
import com.example.translations.framework.datasource.implementation.remote.Api
import com.example.translations.framework.datasource.implementation.remote.CONNECT_TIMEOUT
import com.example.translations.framework.datasource.implementation.remote.READ_TIMEOUT
import com.example.translations.util.ifTrue
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.schedulers.Schedulers.io
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        //<editor-fold defaultstate="collapsed" desc="INNER FUNCTIONS">
        fun OkHttpClient.Builder.addLoggingInterceptor() {
            (BuildConfig.DEBUG).ifTrue {
                with(HttpLoggingInterceptor()) {
                    level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(this)
                }
            }
        }

        fun okHttp(): OkHttpClient {
            with(OkHttpClient.Builder()) {
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                retryOnConnectionFailure(true)
                addLoggingInterceptor()
                return build()
            }
        }

        fun gsonConverter() = GsonConverterFactory.create(GsonBuilder().setLenient().create())

        // </editor-fold>

        return Retrofit.Builder().apply {
            baseUrl(API_URL)
            client(okHttp())
            addConverterFactory(gsonConverter())
            addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(io()))
        }.build()
    }

    @Singleton
    @Provides
    fun provideBaseApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}