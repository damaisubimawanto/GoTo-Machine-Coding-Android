package com.gopay.base.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.gopay.base.customviews.FullScreenViewType.LoadingView
import com.gopay.base.customviews.FullScreenViewType.ErrorView
import com.gopay.databinding.FullScreenViewBinding
import com.gopay.base.extensions.gone
import com.gopay.base.extensions.isVisible

class FullScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: FullScreenViewBinding

    init {
        binding = FullScreenViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    fun showOrHide(
        type: FullScreenViewType,
        isShowing: Boolean
    ) {
        with(binding) {
            when(type) {
                LoadingView -> {
                    loader.isVisible = isShowing
                }
                ErrorView -> {
                    if (loader.isVisible()) {
                        loader.gone()
                    }
                    errorView.isVisible = isShowing
                    errorText.isVisible = isShowing
                }
            }
        }
    }
}