package com.gopay.base.customviews

sealed class FullScreenViewType {
    data object LoadingView: FullScreenViewType()
    data object ErrorView: FullScreenViewType()
}