package com.gopay.utils

import androidx.annotation.RestrictTo
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.runner.Description

/**
 * Created by damai007 on 04/December/2023
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RestrictTo(value = [RestrictTo.Scope.TESTS])
class CoroutineTestRule : InstantTaskExecutorRule() {

    val dispatcher = TestCoroutineDispatcher()

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}