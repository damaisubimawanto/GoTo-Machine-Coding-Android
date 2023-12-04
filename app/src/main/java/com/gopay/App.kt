package com.gopay

import android.app.Application
import com.gopay.dependencies.AppModule
import com.gopay.dependencies.CoreComponent
import com.gopay.dependencies.DaggerCoreComponent

class App : Application() {

    val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}