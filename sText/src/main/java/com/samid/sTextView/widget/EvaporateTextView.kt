package com.samid.stextview.widget

import android.content.Context
import android.graphics.Canvas
import android.text.TextUtils
import android.util.AttributeSet
import com.samid.stextview.base.AnimationListener
import com.samid.stextview.base.STextView

class EvaporateTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : STextView(context, attrs, defStyleAttr) {

    private var evaporateText: EvaporateText? = null

    init {
        init(attrs, defStyleAttr)
    }

    override fun setAnimationListener(listener: AnimationListener) {
        evaporateText?.setAnimationListener(listener)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        evaporateText = EvaporateText()
        evaporateText?.init(this, attrs, defStyleAttr)

        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END
    }

    override fun setProgress(progress: Float) {
        evaporateText?.setProgress(progress)
    }

    override fun animateText(text: CharSequence) {
        evaporateText?.animateText(text)
    }

    override fun onDraw(canvas: Canvas) {
        // super.onDraw(canvas);
        evaporateText?.onDraw(canvas)
    }
}