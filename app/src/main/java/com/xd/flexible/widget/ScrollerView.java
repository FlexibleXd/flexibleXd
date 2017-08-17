package com.xd.flexible.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.xd.flexible.utils.LogUtils;

import static android.R.attr.x;

/**
 * Created by Flexible on 2017/5/26 0026.
 */

public class ScrollerView extends ViewGroup {

    private Scroller mScroller;
    private int lastX;
    private int lastY;

    public ScrollerView(Context context) {
        super(context);
        init();
    }


    public ScrollerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.LOGE("zzz", "event.getAction(" + event.getAction());
        int x = (int)event. getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX =x-lastX;
                int offsetY =y-lastY;
                ((View)getParent()).scrollBy(-offsetX,-offsetY);
                break;

            case MotionEvent.ACTION_UP:
                View parent = (View) getParent();
                LogUtils.LOGE("zzz", "(parent.getScrollX()" + parent.getScrollX());
                mScroller.startScroll(parent.getScrollX(), parent.getScrollY(), -parent.getScrollX(), -parent.getScrollY());
                invalidate();
                break;

        }
        return true;

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
