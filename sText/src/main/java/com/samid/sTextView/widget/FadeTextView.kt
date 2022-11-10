package com.samid.stextview.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.samid.stextview.base.AnimationListener
import com.samid.stextview.base.STextView

class FadeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : STextView(context, attrs, defStyleAttr) {
    private var fadeText: FadeText? = null

    init {
        init(attrs, defStyleAttr)
    }

    override fun setAnimationListener(listener: AnimationListener) {
        fadeText?.setAnimationListener(listener)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        fadeText = FadeText()
        fadeText?.init(this, attrs, defStyleAttr)
    }

    override fun setProgress(progress: Float) {
        fadeText?.setProgress(progress)
    }

    override fun animateText(text: CharSequence) {
        fadeText?.animateText(text)
    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas);
        fadeText?.onDraw(canvas)
    }
}