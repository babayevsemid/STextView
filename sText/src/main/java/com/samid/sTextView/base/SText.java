package com.samid.sTextView.base;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;

public abstract class SText implements ISText {

    protected int mHeight, mWidth;
    protected CharSequence mText, mOldText;
    protected TextPaint mPaint, mOldPaint;
    protected STextView mSTextView;
    protected List<Float> gapList = new ArrayList<>();
    protected List<Float> oldGapList = new ArrayList<>();
    protected float progress; // 0 ~ 1
    protected float mTextSize;
    protected float oldStartX = 0;
    protected AnimationListener animationListener;

    public void setProgress(float progress) {
        this.progress = progress;
        mSTextView.invalidate();
    }

    @Override
    public void init(STextView hTextView, AttributeSet attrs, int defStyle) {
        mSTextView = hTextView;
        mOldText = "";
        mText = hTextView.getText();
        progress = 1;

        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mOldPaint = new TextPaint(mPaint);

        mSTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mSTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mSTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                mTextSize = mSTextView.getTextSize();
                mWidth = mSTextView.getWidth();
                mHeight = mSTextView.getHeight();
                oldStartX = 0;

                try {
                    int layoutDirection = ViewCompat.getLayoutDirection(mSTextView);

                    oldStartX = layoutDirection == View.LAYOUT_DIRECTION_LTR
                            ? mSTextView.getLayout().getLineLeft(0)
                            : mSTextView.getLayout().getLineRight(0);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                initVariables();
            }
        });
        prepareAnimate();
    }

    private void prepareAnimate() {
        mTextSize = mSTextView.getTextSize();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mSTextView.getCurrentTextColor());
        mPaint.setTypeface(mSTextView.getTypeface());
        gapList.clear();
        for (int i = 0; i < mText.length(); i++) {
            gapList.add(mPaint.measureText(String.valueOf(mText.charAt(i))));
        }
        mOldPaint.setTextSize(mTextSize);
        mOldPaint.setColor(mSTextView.getCurrentTextColor());
        mOldPaint.setTypeface(mSTextView.getTypeface());
        oldGapList.clear();
        for (int i = 0; i < mOldText.length(); i++) {
            oldGapList.add(mOldPaint.measureText(String.valueOf(mOldText.charAt(i))));
        }
    }

    @Override
    public void animateText(CharSequence text) {
        mSTextView.setText(text);
        mOldText = mText;
        mText = text;
        prepareAnimate();
        animatePrepare(text);
        animateStart(text);
    }

    @Override
    public void setAnimationListener(AnimationListener listener) {
        animationListener = listener;
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawFrame(canvas);
    }

    protected abstract void initVariables();

    protected abstract void animateStart(CharSequence text);

    protected abstract void animatePrepare(CharSequence text);

    protected abstract void drawFrame(Canvas canvas);
}
