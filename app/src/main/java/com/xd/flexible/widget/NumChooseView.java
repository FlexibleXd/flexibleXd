package com.xd.flexible.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xd.flexible.R;


/**
 * Created by flexible on 2016/7/7 0007.
 */
public class NumChooseView extends LinearLayout implements View.OnClickListener {
    private Context cxt;
    private TextView add, txt, del;
    private int max = Integer.MAX_VALUE;
    private AddOnclickListener addOnclickListener;
    private MinusOnclickListener minusOnclickListener;


    private Boolean isLoad = false;

    public NumChooseView(Context cxt) {
        super(cxt);
        this.cxt = cxt;
        init();
    }

    public NumChooseView(Context cxt, AttributeSet attrs) {
        super(cxt, attrs);
        this.cxt = cxt;
        init();
    }

    public NumChooseView(Context cxt, AttributeSet attrs, int def) {
        super(cxt, attrs, def);
        this.cxt = cxt;
        init();
    }

    private void init() {
        View v = LayoutInflater.from(cxt).inflate(R.layout.view_num_choose, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(v, params);
        add = (TextView) v.findViewById(R.id.tv_add);
        txt = (TextView) v.findViewById(R.id.tv_num);
        del = (TextView) v.findViewById(R.id.tv_reduce);
        add.setOnClickListener(this);
        del.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                if (isLoad) {
                    return;
                }
                String cur = txt.getText().toString();
                int c = Integer.parseInt(cur);
                if (c >= max) {
                    Toast.makeText(cxt, "不能继续增加", Toast.LENGTH_SHORT).show();
                    c = max;
                } else {
                    c++;
                    if (addOnclickListener != null)
                        addOnclickListener.onClick();
                }
                txt.setText(c + "");
                break;
            case R.id.tv_reduce:
                if (isLoad) {
                    return;
                }
                String cur1 = txt.getText().toString();
                int c1 = Integer.parseInt(cur1);
                if (c1 <= 1) {
                    Toast.makeText(cxt, "不能继续减少", Toast.LENGTH_SHORT).show();
                    c1 = 1;
                } else {
                    c1--;
                    if (minusOnclickListener != null)
                        minusOnclickListener.onClick();
                }
                txt.setText(c1 + "");
                break;
        }
    }

    public void setAddOnclickListener(AddOnclickListener addOnclickListener) {
        this.addOnclickListener = addOnclickListener;
    }

    public void setMinusOnclickListener(MinusOnclickListener minusOnclickListener) {
        this.minusOnclickListener = minusOnclickListener;
    }


    public interface AddOnclickListener {
        void onClick();
    }

    public interface MinusOnclickListener {
        void onClick();
    }

    public Boolean getLoad() {
        return isLoad;
    }

    public void setLoad(Boolean load) {
        isLoad = load;
    }

    public String getNum() {
        return txt.getText().toString();
    }

    public void setNum(int num) {
        txt.setText(num + "");
    }

    public void setMaxNum(int max) {
        this.max = max;
    }
}
