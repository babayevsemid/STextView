package com.samid.stextview.base

import android.animation.Animator

open class DefaultAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator) {
        // no-op
    }

    override fun onAnimationEnd(animation: Animator) {
        // no-op
    }

    override fun onAnimationCancel(animation: Animator) {
        // no-op
    }

    override fun onAnimationRepeat(animation: Animator) {
        // no-op
    }
}