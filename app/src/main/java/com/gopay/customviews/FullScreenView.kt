package com.gopay.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.gopay.extensions.isGone
import com.gopay.extensions.visible
import com.gopay.customviews.FullScreenViewType.LoadingView
import com.gopay.customviews.FullScreenViewType.ErrorView
import com.gopay.databinding.FullScreenViewBinding
import com.gopay.extensions.gone
import com.gopay.extensions.isVisible

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

    fun show(type: FullScreenViewType) {
        with(binding) {
            when(type) {
                LoadingView -> {
                    if(loader.isGone()){
                        loader.visible()
                    }
                }
                ErrorView -> {
                    if(loader.isVisible()){
                        loader.gone()
                    }
                    if(errorView.isGone()){
                        errorView.visible()
                    }
                    if(errorText.isGone()){
                        errorText.visible()
                    }
                }
            }
        }
    }

    fun hide(type: FullScreenViewType) {
        with(binding) {
            when(type) {
                LoadingView -> {
                    if(loader.isVisible()){
                        loader.gone()
                    }
                }
                ErrorView -> {
                    if(errorView.isVisible()){
                        errorView.gone()
                    }
                    if(errorText.isVisible()){
                        errorText.gone()
                    }
                }
            }
        }
    }

}