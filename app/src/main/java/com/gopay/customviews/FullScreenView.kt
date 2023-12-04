package com.gopay.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.gopay.R
import com.gopay.extensions.isGone
import com.gopay.extensions.visible
import com.gopay.customviews.FullScreenViewType.LoadingView
import com.gopay.customviews.FullScreenViewType.ErrorView
import com.gopay.extensions.gone
import com.gopay.extensions.isVisible
import kotlinx.android.synthetic.main.full_screen_view.view.*

class FullScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(
            context,
            R.layout.full_screen_view,
            this
        )
    }

    fun show(type: FullScreenViewType) {
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
                if(error_view.isGone()){
                    error_view.visible()
                }
                if(error_text.isGone()){
                    error_text.visible()
                }
            }
        }
    }

    fun hide(type: FullScreenViewType) {
        when(type) {
            LoadingView -> {
                if(loader.isVisible()){
                    loader.gone()
                }
            }
            ErrorView -> {
                if(error_view.isVisible()){
                    error_view.gone()
                }
                if(error_text.isVisible()){
                    error_text.gone()
                }
            }
        }
    }

}