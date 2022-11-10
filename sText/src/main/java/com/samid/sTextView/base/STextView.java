package com.samid.sTextView.base;

import android.content.Context;
import android.util.AttributeSet;

public abstract class STextView extends androidx.appcompat.widget.AppCompatTextView {
    public STextView(Context context) {
        this(context, null);
    }

    public STextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public STextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void setAnimationListener(AnimationListener listener);

    public abstract void setProgress(float progress);

    public abstract void animateText(CharSequence text);
}
