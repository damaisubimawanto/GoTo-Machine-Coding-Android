package com.gopay.dependencies

import com.gopay.base.dispatcher.CoroutineDispatcherProvider
import com.gopay.base.dispatcher.RealCoroutineDispatcherProvider
import org.koin.dsl.module

val dispatcherModule = module {
    single<CoroutineDispatcherProvider> {
        RealCoroutineDispatcherProvider()
    }
}
