package com.gopay.dependencies

import com.gopay.domain.repositories.HomeRepository
import com.gopay.domain.repositories.HomeRepositoryImpl
import org.koin.dsl.module

/**
 * Created by damai007 on 04/December/2023
 */
val repositoryModule = module {
    single<HomeRepository> {
        HomeRepositoryImpl(
            homeService = get(),
            dispatcherProvider = get()
        )
    }
}