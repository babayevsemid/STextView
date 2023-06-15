package com.samid.stextview.base

import android.graphics.Canvas
import android.util.AttributeSet

interface ISText {
    fun init(sTextView: STextView, attrs: AttributeSet?, defStyle: Int)
    fun animateText(text: CharSequence)
    fun onDraw(canvas: Canvas)
    fun setAnimationListener(listener: AnimationListener)
}