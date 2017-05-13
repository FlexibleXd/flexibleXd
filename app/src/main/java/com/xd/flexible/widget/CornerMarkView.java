package com.xd.flexible.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xd.flexible.R;


/**
 * Created by Flexible on 2017/3/27 0027.
 */

public class CornerMarkView extends RelativeLayout {
    private TextView tvCount;
    private int msgCount;

    public CornerMarkView(Context context) {
        this(context, null);
    }

    public CornerMarkView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerMarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.view_corner_mark, this, true);
        tvCount = (TextView) rl.findViewById(R.id.num);
        ImageView ivContent = (ImageView) rl.findViewById(R.id.iv);
        TextView tvCount = (TextView) rl.findViewById(R.id.tv);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs,
                    R.styleable.CornerMarkView);
            int img = ta.getResourceId(R.styleable.CornerMarkView_img,
                    R.mipmap.ic_launcher);
            String text = ta.getString(R.styleable.CornerMarkView_text);
            ivContent.setImageResource(img);
            tvCount.setText(text);
        }
    }

    public String getNum() {
        return tvCount.getText().toString();
    }

    public void setNum(int count) {
        msgCount = count;
        if (count == 0) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
            if (count < 100) {
                tvCount.setText(count + "");
            } else {
                tvCount.setText("99+");
            }
        }
        invalidate();
        tvCount.setText(count+"");
    }
}
