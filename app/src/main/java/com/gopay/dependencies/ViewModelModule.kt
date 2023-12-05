package com.gopay.dependencies

import com.gopay.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by damai007 on 04/December/2023
 */
val viewModelModule = module {
    viewModel {
        MainViewModel(
            dispatcher = get(),
            getRepositoryListUseCase = get()
        )
    }
}