package com.xd.flexible.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xd.flexible.R;

/**
 * Created by Flexible on 2017/10/30 0030.
 */

public class FootVHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public FootVHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView1);
    }

    public void setHeadText(String text) {
        if (text == null) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
    }
}
