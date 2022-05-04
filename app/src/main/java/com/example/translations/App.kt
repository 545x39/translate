package com.example.translations

import android.app.Application
import com.example.translations.di.AppComponent
import com.example.translations.di.DaggerAppComponent
import com.example.translations.di.module.AppModule
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.InputStream

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent
            .factory()
            .create(AppModule(baseContext))
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        "[CLS:%s], [MTD:%s], [LN:%s]",
                        super.createStackElementTag(element),
                        element.methodName,
                        element.lineNumber
                    )
                }
            })
        }
    }
}