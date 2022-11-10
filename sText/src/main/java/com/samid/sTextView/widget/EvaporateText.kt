package com.samid.stextview.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import com.samid.stextview.base.*

class EvaporateText : SText() {
    var charTime = 300f
    var mostCount = 20

    private var mTextHeight = 0
    private val differentList: MutableList<CharacterDiffResult> = ArrayList()
    private var duration: Long = 0
    private var animator: ValueAnimator? = null

    override fun init(hTextView: STextView, attrs: AttributeSet?, defStyle: Int) {
        super.init(hTextView, attrs, defStyle)

        animator = ValueAnimator()
        animator?.apply {
            interpolator = AccelerateDecelerateInterpolator()

            addListener(object : DefaultAnimatorListener() {
                override fun onAnimationEnd(animation: Animator) {
                    animationListener?.onAnimationEnd(mSTextView)
                }
            })

            addUpdateListener { animation ->
                setProgress(animation.animatedValue as Float)
                mSTextView.invalidate()
            }

            var n = mText.length
            n = if (n <= 0) 1 else n
            duration = (charTime + charTime / mostCount * (n - 1)).toLong()
        }
    }

    override fun animateText(text: CharSequence) {
        mSTextView.post(Runnable {
            if (mSTextView.layout == null) {
                return@Runnable
            }
            oldStartX = mSTextView.layout.getLineLeft(0)
            super@EvaporateText.animateText(text)
        })
    }

    override fun initVariables() {}

    override fun animateStart(text: CharSequence?) {
        var n = mText.length
        n = if (n <= 0) 1 else n
        duration = (charTime + charTime / mostCount * (n - 1)).toLong()
        animator?.cancel()
        animator?.setFloatValues(0f, 1f)
        animator?.duration = duration
        animator?.start()
    }

    override fun animatePrepare(text: CharSequence?) {
        differentList.clear()
        differentList.addAll(CharacterUtils.diff(mOldText, mText))

        val bounds = Rect()

        mPaint?.getTextBounds(mText.toString(), 0, mText.length, bounds)

        mTextHeight = bounds.height()
    }

    override fun drawFrame(canvas: Canvas?) {
        val startX = mSTextView.layout.getLineLeft(0)
        val startY = mSTextView.baseline.toFloat()
        var offset = startX
        var oldOffset = oldStartX
        val maxLength = mText.length.coerceAtLeast(mOldText.length)

        for (i in 0 until maxLength) {

            // draw old text
            if (i < mOldText.length) {
                //
                val pp =
                    progress * duration / (charTime + charTime / mostCount * (mText.length - 1))
                mOldPaint?.textSize = mTextSize

                val move = CharacterUtils.needMove(i, differentList)
                if (move != -1) {
                    mOldPaint?.alpha = 255

                    var p = pp * 2f
                    p = if (p > 1) 1f else p
                    val distX =
                        CharacterUtils.getOffset(i, move, p, startX, oldStartX, gapList, oldGapList)

                    mOldPaint?.let {
                        canvas?.drawText(
                            mOldText[i].toString() + "", 0, 1, distX, startY,
                            it
                        )
                    }
                } else {
                    mOldPaint?.alpha = ((1 - pp) * 255).toInt()

                    val y = startY - pp * mTextHeight

                    mOldPaint?.let {
                        val width = it.measureText(mOldText[i].toString() + "")

                        canvas?.drawText(
                            mOldText[i].toString() + "",
                            0,
                            1,
                            oldOffset + (oldGapList[i] - width) / 2,
                            y,
                            it
                        )
                    }
                }
                oldOffset += oldGapList[i]
            }

            // draw new text
            if (i < mText.length) {
                if (!CharacterUtils.stayHere(i, differentList)) {
                    var alpha =
                        (255f / charTime * (progress * duration - charTime * i / mostCount)).toInt()
                    alpha = if (alpha > 255) 255 else alpha
                    alpha = if (alpha < 0) 0 else alpha
                    mPaint?.alpha = alpha
                    mPaint?.textSize = mTextSize

                    val pp =
                        progress * duration / (charTime + charTime / mostCount * (mText.length - 1))

                    val y = mTextHeight + startY - pp * mTextHeight

                    mPaint?.let {
                        val width = it.measureText(mText[i].toString() + "")

                        canvas?.drawText(
                            mText[i].toString() + "",
                            0,
                            1,
                            offset + (gapList[i] - width) / 2,
                            y,
                            it
                        )
                    }
                }
                offset += gapList[i]
            }
        }
    }
}