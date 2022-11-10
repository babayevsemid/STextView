package com.samid.stextview.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import com.samid.stextview.base.*

class ScaleText : SText() {
    var mostCount = 20f
    var charTime = 300f

    private val differentList: MutableList<CharacterDiffResult> = ArrayList()
    private var duration: Long = 0
    private var animator: ValueAnimator? = null

    override fun init(sTextView: STextView, attrs: AttributeSet?, defStyle: Int) {
        super.init(sTextView, attrs, defStyle)

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
        if (mSTextView.layout == null) return

        mSTextView.post(Runnable {
            if (mSTextView.layout == null) {
                return@Runnable
            }
            oldStartX = mSTextView.layout.getLineLeft(0)
            super@ScaleText.animateText(text)
        })
    }

    override fun initVariables() {}

    override fun animatePrepare(text: CharSequence?) {
        differentList.clear()
        differentList.addAll(CharacterUtils.diff(mOldText, mText))
    }

    override fun animateStart(text: CharSequence?) {
        var n = mText.length
        n = if (n <= 0) 1 else n

        duration = (charTime + charTime / mostCount * (n - 1)).toLong()
        animator?.cancel()
        animator?.setFloatValues(0f, 1f)
        animator?.duration = duration
        animator?.start()
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
                val move = CharacterUtils.needMove(i, differentList)
                if (move != -1) {
                    mOldPaint?.apply {
                        textSize = mTextSize
                        alpha = 255

                        var p = progress * 2f
                        p = if (p > 1) 1f else p

                        val distX =
                            CharacterUtils.getOffset(
                                i,
                                move,
                                p,
                                startX,
                                oldStartX,
                                gapList,
                                oldGapList
                            )

                        canvas?.drawText(mOldText[i].toString() + "", 0, 1, distX, startY, this)
                    }
                } else {
                    mOldPaint?.apply {
                        this.alpha = ((1 - progress) * 255).toInt()
                        this.textSize = mTextSize * (1 - progress)

                        val width = this.measureText(mOldText[i].toString() + "")

                        canvas?.drawText(
                            mOldText[i].toString() + "",
                            0,
                            1,
                            oldOffset + (oldGapList[i] - width) / 2,
                            startY,
                            this
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
                    if (alpha > 255) alpha = 255
                    if (alpha < 0) alpha = 0
                    var size =
                        mTextSize * 1f / charTime * (progress * duration - charTime * i / mostCount)
                    if (size > mTextSize) size = mTextSize
                    if (size < 0) size = 0f
                    mPaint?.apply {
                        this.alpha = alpha
                        this.textSize = size

                        val width = this.measureText(mText[i].toString() + "")

                        canvas?.drawText(
                            mText[i].toString() + "",
                            0,
                            1,
                            offset + (gapList[i] - width) / 2,
                            startY,
                            this
                        )
                    }
                }
                offset += gapList[i]
            }
        }
    }
}