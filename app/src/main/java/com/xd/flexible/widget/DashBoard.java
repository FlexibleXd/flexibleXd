package com.xd.flexible.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.xd.flexible.utils.ScreenUtils;

/**
 * Created by Flexible on 2017/6/1 0001.
 */

public class DashBoard extends View {

    private Paint paintCircle;
    private Paint paintDegree;
    private int mWidth;
    private int mHeight;
    private Paint paintHour;
    private Paint paintMinute;

    public DashBoard(Context context) {
        super(context);
        init();
    }


    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(5);
        paintDegree = new Paint();
        paintHour = new Paint();
        paintHour.setStrokeWidth(15);
        paintMinute = new Paint();
        paintMinute.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(ScreenUtils.getScreenWidth(getContext()), ScreenUtils.getScreenWidth(getContext()));
        mWidth = ScreenUtils.getScreenWidth(getContext());
        mHeight = ScreenUtils.getScreenWidth(getContext());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, paintCircle);
        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                paintDegree.setStrokeWidth(5);
                paintDegree.setTextSize(20);
                canvas.drawLine(mWidth / 2, 0, mWidth / 2, 30, paintDegree);
                canvas.drawText(i + "", mWidth / 2 - paintDegree.measureText(i + "") / 2, 60, paintDegree);
            } else {
                paintDegree.setStrokeWidth(3);
                paintDegree.setTextSize(10);
                canvas.drawLine(mWidth / 2, 0, mWidth / 2, 20, paintDegree);
                canvas.drawText(i + "", mWidth / 2 - paintDegree.measureText(i + "") / 2, 40, paintDegree);
            }
            canvas.rotate(15, mWidth / 2, mHeight / 2);
        }
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawLine(0, 0, 100, 200, paintHour);
        canvas.drawLine(0, 0, 0, 300, paintMinute);
        canvas.restore();
    }
}
