package com.samid.stextview.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Shader
import android.util.AttributeSet
import com.samid.stextview.base.AnimationListener
import com.samid.stextview.base.DisplayUtils
import com.samid.stextview.base.STextView
import com.samid.stextview.R

class RainbowTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : STextView(context, attrs, defStyleAttr) {

    private var mMatrix: Matrix? = null
    private var mTranslate = 0f
    var colorSpeed = 0f
    var colorSpace = 0f
    private var colors =
        intArrayOf(-0xd4de, -0x80de, -0x1200de, -0xdd00de, -0xdd0b01, -0xddc601, -0xabff09)
    private var mLinearGradient: LinearGradient? = null

    init {
        init(attrs, defStyleAttr)
    }

    override fun setAnimationListener(listener: AnimationListener) {
        throw UnsupportedOperationException("Invalid operation for rainbow")
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RainbowTextView)
        colorSpace = typedArray.getDimension(
            R.styleable.RainbowTextView_colorSpace,
            DisplayUtils.dp2px(150f).toFloat()
        )
        colorSpeed = typedArray.getDimension(
            R.styleable.RainbowTextView_colorSpeed,
            DisplayUtils.dp2px(5f).toFloat()
        )

        typedArray.recycle()

        mMatrix = Matrix()

        initPaint()
    }

    fun setColors(vararg colors: Int) {
        this.colors = colors
        initPaint()
    }

    private fun initPaint() {
        mLinearGradient = LinearGradient(0f, 0f, colorSpace, 0f, colors, null, Shader.TileMode.MIRROR)
        paint.shader = mLinearGradient
    }

    override fun setProgress(progress: Float) {}
    override fun animateText(text: CharSequence) {
        setText(text)
    }

    override fun onDraw(canvas: Canvas) {
        if (mMatrix == null) {
            mMatrix = Matrix()
        }
        mTranslate += colorSpeed
        mMatrix!!.setTranslate(mTranslate, 0f)
        mLinearGradient!!.setLocalMatrix(mMatrix)
        super.onDraw(canvas)
        postInvalidateDelayed(100)
    }
}