package com.gopay.customviews

sealed class FullScreenViewType {
    data object LoadingView: FullScreenViewType()
    data object ErrorView: FullScreenViewType()
}