package com.gopay

import android.app.Application
import com.gopay.dependencies.dispatcherModule
import com.gopay.dependencies.imageModule
import com.gopay.dependencies.networkModule
import com.gopay.dependencies.repositoryModule
import com.gopay.dependencies.storageModule
import com.gopay.dependencies.useCaseModule
import com.gopay.dependencies.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                dispatcherModule,
                imageModule,
                networkModule,
                storageModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}