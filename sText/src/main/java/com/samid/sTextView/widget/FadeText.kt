package com.samid.stextview.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import com.samid.stextview.base.DefaultAnimatorListener
import com.samid.stextview.base.SText
import com.samid.stextview.base.STextView
import com.samid.stextview.R
import java.util.*

class FadeText : SText() {
    private var random: Random? = null
    var animationDuration = 0
    private var alphaList: MutableList<Int>? = null
    private val DEFAULT_DURATION = 2000

    override fun init(sTextView: STextView, attrs: AttributeSet?, defStyle: Int) {
        super.init(sTextView, attrs, defStyle)

        val typedArray = sTextView.context.obtainStyledAttributes(attrs, R.styleable.FadeTextView)

        animationDuration =
            typedArray.getInt(R.styleable.FadeTextView_animationDuration, DEFAULT_DURATION)

        typedArray.recycle()
    }

    override fun initVariables() {
        random = Random()
        if (alphaList == null) {
            alphaList = ArrayList()
        }
        // generate random alpha
        alphaList!!.clear()
        for (i in 0 until mSTextView.text.length) {
            val randomNumber = random!!.nextInt(2) // 0 or 1
            if ((i + 1) % (randomNumber + 2) == 0) { // 2 or 3
                if ((i + 1) % (randomNumber + 4) == 0) { // 4 or 5
                    alphaList!!.add(55)
                } else {
                    alphaList!!.add(255)
                }
            } else {
                if ((i + 1) % (randomNumber + 4) == 0) { // 4 or 5
                    alphaList!!.add(55)
                } else {
                    alphaList!!.add(0)
                }
            }
        }
    }

    override fun animateStart(text: CharSequence?) {
        initVariables()

        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
            .setDuration(animationDuration.toLong())

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addListener(object : DefaultAnimatorListener() {
            override fun onAnimationEnd(animation: Animator) {
                animationListener?.onAnimationEnd(mSTextView)
            }
        })
        valueAnimator.addUpdateListener { animation ->
            setProgress(animation.animatedValue as Float)
            mSTextView.invalidate()
        }
        valueAnimator.start()
    }

    override fun animatePrepare(text: CharSequence?) {}

    override fun drawFrame(canvas: Canvas?) {
        val layout = mSTextView.layout
        var gapIndex = 0
        for (i in 0 until layout.lineCount) {
            val lineStart = layout.getLineStart(i)
            val lineEnd = layout.getLineEnd(i)
            var lineLeft = layout.getLineLeft(i)
            val lineBaseline = layout.getLineBaseline(i).toFloat()
            val lineText = mText.subSequence(lineStart, lineEnd).toString()

            for (element in lineText) {
                val alpha = alphaList!![gapIndex]

                mPaint?.apply {
                    this.alpha = ((255 - alpha) * progress + alpha).toInt()
                    canvas?.drawText(element.toString(), lineLeft, lineBaseline, this)
                }

                lineLeft += gapList[gapIndex++]
            }
        }
    }
}