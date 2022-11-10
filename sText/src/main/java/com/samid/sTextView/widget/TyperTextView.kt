package com.samid.stextview.widget

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import com.samid.stextview.base.AnimationListener
import com.samid.stextview.base.STextView
import com.samid.story.R
import java.util.*

class TyperTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : STextView(context, attrs, defStyleAttr) {
    private val random: Random
    private var mText: CharSequence
    private lateinit var handlers: Handler

    var charIncrease: Int
    var typerSpeed: Int
    private var animationListener: AnimationListener? = null

    init {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TyperTextView)
        typerSpeed = typedArray.getInt(R.styleable.TyperTextView_typerSpeed, 100)
        charIncrease = typedArray.getInt(R.styleable.TyperTextView_charIncrease, 2)
        typedArray.recycle()
        random = Random()
        mText = text

        handlers = Handler(Handler.Callback {
            val currentLength = text.length
            if (currentLength < mText.length) {
                if (currentLength + charIncrease > mText.length) {
                    charIncrease = mText.length - currentLength
                }
                append(mText.subSequence(currentLength, currentLength + charIncrease))
                val randomTime = (typerSpeed + random.nextInt(typerSpeed)).toLong()
                val message = Message.obtain()
                message.what = INVALIDATE
                handlers.sendMessageDelayed(message, randomTime)
                return@Callback false
            } else {
                if (animationListener != null) {
                    animationListener!!.onAnimationEnd(this@TyperTextView)
                }
            }
            false
        })
    }

    override fun setAnimationListener(listener: AnimationListener) {
        animationListener = listener
    }

    override fun setProgress(progress: Float) {
        text = mText.subSequence(0, (mText.length * progress).toInt())
    }

    override fun animateText(text: CharSequence) {
        mText = text

        setText("")

        val message = Message.obtain()
        message.what = INVALIDATE
        handlers.sendMessage(message)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handlers.removeMessages(INVALIDATE)
    }

    companion object {
        const val INVALIDATE = 0x767
    }
}