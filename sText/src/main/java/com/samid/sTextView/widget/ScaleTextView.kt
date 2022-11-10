package com.samid.sTextView.widget

import android.content.Context
import android.graphics.Canvas
import android.text.TextUtils
import android.util.AttributeSet
import com.samid.sTextView.base.AnimationListener
import kotlin.jvm.JvmOverloads
import com.samid.sTextView.base.STextView
import com.samid.sTextView.widget.ScaleText
import com.samid.story.R

class ScaleTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : STextView(context, attrs, defStyleAttr) {

    private val scaleText: ScaleText = ScaleText()

    init {
        scaleText.init(this, attrs, defStyleAttr)
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END

        context?.obtainStyledAttributes(attrs, R.styleable.ScaleTextView)?.apply {
            scaleText.charTime =
                getInteger(R.styleable.ScaleTextView_animationDuration, 300).toFloat()

            recycle()
        }
    }

    override fun setAnimationListener(listener: AnimationListener) {
        scaleText.setAnimationListener(listener)
    }

    override fun onDraw(canvas: Canvas) {
        // super.onDraw(canvas);
        scaleText.onDraw(canvas)
    }

    override fun setProgress(progress: Float) {
        scaleText.setProgress(progress)
    }

    override fun animateText(text: CharSequence) {
        if(this.text.isEmpty()){
            post {
                scaleText.animateText(text)
            }
        }else {
            scaleText.animateText(text)
        }
    }
}