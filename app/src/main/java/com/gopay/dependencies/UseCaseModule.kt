package com.gopay.dependencies

import com.gopay.domain.usecases.GetRepositoryListUseCase
import org.koin.dsl.module

/**
 * Created by damai007 on 04/December/2023
 */
val useCaseModule = module {
    factory {
        GetRepositoryListUseCase(
            homeRepository = get()
        )
    }
}