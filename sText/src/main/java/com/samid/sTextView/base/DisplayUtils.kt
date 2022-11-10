package com.samid.stextview.base

import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.roundToInt

object DisplayUtils {
    private val displayMetrics: DisplayMetrics
        get() = Resources.getSystem().displayMetrics

    fun dp2px(dp: Float): Int {
        return (dp * displayMetrics.density).roundToInt()
    }
}