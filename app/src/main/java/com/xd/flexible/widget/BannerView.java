package com.xd.flexible.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Flexible on 2017/9/4 0004.
 */

public class BannerView extends ViewGroup {

    private int childCount;
    private int childeHeight;
    private int childWidth = getScreenWidth();
    private int startX;
    private int index;
    private Scroller scroller;
    private Timer timer;
    private TimerTask task;
    private boolean isAuto = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 0:
                    if (++index >= childCount)
                        index = 0;
                    scrollTo(childWidth * index, 0);
                    break;
            }
        }
    };
    private boolean tempBanner;

    private void startAuto() {
        isAuto = true;
    }

    private void stopAuto() {
        isAuto = false;
    }

    public BannerView(Context context) {
        super(context);
        init();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext());
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (isAuto)
                    handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(task, 100, 3000);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int left = 0;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                child.layout(left, 0, left + childWidth, childeHeight);
                left += childWidth;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            childeHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(getChildAt(0).getMeasuredWidth() * childCount, childeHeight);
        }
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isAuto)
                    tempBanner = true;
                stopAuto();
                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                scrollBy(-(moveX - startX), 0);
                startX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                if (tempBanner)
                    startAuto();
                int scrollX = getScrollX();
                index = (scrollX + childWidth / 2) / childWidth;
                if (index < 0) {
                    index = 0;
                } else if (index > childCount - 1) {
                    index = childCount - 1;
                }
//                scrollTo(index * getScreenWidth(), 0);

                scroller.startScroll(scrollX, 0, index * childWidth - scrollX, 0);
                postInvalidate();
                break;
        }
        return true;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.widthPixels;
    }
}
