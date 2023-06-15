package com.samid.stextview.base

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.samid.stextview.base.AnimationListener

abstract class STextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    abstract fun setAnimationListener(listener: AnimationListener)
    abstract fun setProgress(progress: Float)
    abstract fun animateText(text: CharSequence)
}