package com.gopay.dependencies

import com.gopay.dispatcher.CoroutineDispatcherProvider
import com.gopay.dispatcher.RealCoroutineDispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DispatcherModule {
    @Provides
    @Singleton
    fun providesCoroutineDispatcher(): CoroutineDispatcherProvider {
        return RealCoroutineDispatcherProvider()
    }
}
