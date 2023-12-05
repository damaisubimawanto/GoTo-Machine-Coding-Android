package com.gopay.domain.models

/**
 * Created by damai007 on 05/December/2023
 */
sealed class SortMenuType {

    data object SortByWatcher : SortMenuType()

    data object SortByFork : SortMenuType()

    data object ClearSort : SortMenuType()
}