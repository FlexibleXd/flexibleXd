package com.xd.flexible.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by flexibleXd on 2016/12/26.
 */

public class IsScrollViewPage extends ViewPager {

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }

    private boolean isScroll = true;

    public IsScrollViewPage(Context context) {
        super(context);
    }

    public IsScrollViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isScroll && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isScroll && super.onInterceptTouchEvent(event);
    }
}
