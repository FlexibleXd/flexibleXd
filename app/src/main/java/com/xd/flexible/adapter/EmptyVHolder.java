package com.xd.flexible.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xd.flexible.R;

/**
 * Created by Flexible on 2017/10/30 0030.
 */

public class EmptyVHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public EmptyVHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.tv_empty);
    }

    public void setEmptyText(String text) {
        textView.setText(text);
    }
}
