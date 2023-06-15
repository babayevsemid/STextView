package com.samid.stextview.base

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.view.ViewCompat

abstract class SText : ISText {
    protected var mHeight = 0
    protected var mWidth = 0
    protected var mText: CharSequence = ""
    protected var mOldText: CharSequence = ""
    protected var mPaint: TextPaint? = null
    protected var mOldPaint: TextPaint? = null
    protected lateinit var mSTextView: STextView
    protected var gapList: MutableList<Float> = ArrayList()
    protected var oldGapList: MutableList<Float> = ArrayList()
    protected var progress = 0f
        private set
    protected var mTextSize = 0f
    protected var oldStartX = 0f
    protected var animationListener: AnimationListener? = null
        private set

    override fun init(sTextView: STextView, attrs: AttributeSet?, defStyle: Int) {
        mSTextView = sTextView
        mOldText = ""
        mText = sTextView.text
        progress = 1f
        mPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mOldPaint = TextPaint(mPaint)

        mSTextView.viewTreeObserver?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mSTextView.viewTreeObserver?.removeOnGlobalLayoutListener(this)

                mTextSize = mSTextView.textSize
                mWidth = mSTextView.width
                mHeight = mSTextView.height
                oldStartX = 0f

                try {
                    val layoutDirection = ViewCompat.getLayoutDirection(mSTextView)
                    oldStartX =
                        if (layoutDirection == View.LAYOUT_DIRECTION_LTR)
                            mSTextView.layout.getLineLeft(0) else mSTextView.layout.getLineRight(
                            0
                        )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                initVariables()
            }
        })
        prepareAnimate()
    }

    fun setProgress(progress: Float) {
        this.progress = progress

        mSTextView.invalidate()
    }

    private fun prepareAnimate() {
        mTextSize = mSTextView.textSize ?: 0f
        mPaint!!.textSize = mTextSize
        mPaint!!.color = mSTextView.currentTextColor ?: Color.BLUE
        mPaint!!.typeface = mSTextView.typeface
        gapList.clear()
        for (i in mText.indices) {
            gapList.add(mPaint!!.measureText(mText[i].toString()))
        }
        mOldPaint!!.textSize = mTextSize
        mOldPaint!!.color = mSTextView.currentTextColor ?: Color.BLUE
        mOldPaint!!.typeface = mSTextView.typeface
        oldGapList.clear()

        for (i in mOldText.indices) {
            oldGapList.add(mOldPaint!!.measureText(mOldText[i].toString()))
        }
    }

    override fun animateText(text: CharSequence) {
        mSTextView.text = text
        mOldText = mText
        mText = text

        prepareAnimate()
        animatePrepare(text)
        animateStart(text)
    }

    override fun setAnimationListener(listener: AnimationListener) {
        animationListener = listener
    }

    override fun onDraw(canvas: Canvas) {
        drawFrame(canvas)
    }

    protected abstract fun initVariables()
    protected abstract fun animateStart(text: CharSequence?)
    protected abstract fun animatePrepare(text: CharSequence?)
    protected abstract fun drawFrame(canvas: Canvas?)
}