package com.xd.flexible.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by Flexible on 2017/11/13 0013.
 */

public class TestView extends View {
    public TestView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//买热

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
