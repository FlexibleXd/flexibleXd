package com.xd.flexible.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by flexibleXd on 2016/12/24.
 */

public class ImgClickEditText extends EditText {
    private OnDrawableLeftListener mLeftListener;
    private OnDrawableRightListener mRightListener;
    private OnDrawableListener drawableListener;
    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

    @SuppressLint("NewApi")
    public ImgClickEditText(Context context, AttributeSet attrs, int defStyleAttr,
                            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ImgClickEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImgClickEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImgClickEditText(Context context) {
        super(context);
    }

    public void setOnDrawableLeftListener(OnDrawableLeftListener listener) {
        this.mLeftListener = listener;
    }

    public void setOnDrawableRightListener(OnDrawableRightListener listener) {
        this.mRightListener = listener;
    }

    public void setOnDrawableClickListener(OnDrawableListener listener) {
        this.drawableListener = listener;
    }

    public interface OnDrawableListener {
        void onDrawableLeftClick(View view);

        void onDrawableRightClick(View view);
    }

    public interface OnDrawableLeftListener {
        void onDrawableLeftClick(View view);
    }

    public interface OnDrawableRightListener {
        void onDrawableRightClick(View view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mRightListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
                        mRightListener.onDrawableRightClick(this);
                        return true;
                    }
                }
                if (drawableListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
                        drawableListener.onDrawableRightClick(this);
                        return true;
                    }
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {
                        drawableListener.onDrawableLeftClick(this);
                        return true;
                    }
                }
                if (mLeftListener != null) {
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {
                        mLeftListener.onDrawableLeftClick(this);
                        return true;
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }
}
