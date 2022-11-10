package com.samid.sTextView.base;

import android.graphics.Canvas;
import android.util.AttributeSet;

public interface ISText {
    void init(STextView hTextView, AttributeSet attrs, int defStyle);

    void animateText(CharSequence text);

    void onDraw(Canvas canvas);

    void setAnimationListener(AnimationListener listener);
}
