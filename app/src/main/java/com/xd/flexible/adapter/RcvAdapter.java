package com.xd.flexible.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xd.flexible.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Flexible on 2017/10/31 0031.
 */

public class RcvAdapter extends BaseAdapter1 {


    public RcvAdapter(List data, Context ctx, AdapterItemListener onItemClickListener) {
        super(data, ctx, onItemClickListener);
    }

    public RcvAdapter(List data, Context ctx) {
        super(data, ctx);
    }

    public RcvAdapter(List data) {
        super(data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateVHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);
        return new Holder(v);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFootVHolder(ViewGroup parent, int type) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flexible__empty_holder, parent, false);

        return new FootVHolder(v);
    }

    @Override
    protected void onBindVHolder(RecyclerView.ViewHolder holder, int position) {
        Holder h = (Holder) holder;
        h.tvTest.setText("--------------" + position);
    }

    @Override
    protected void onBindFootVHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class Holder extends BaseVHolder {
        @BindView(R.id.tv_test)
        TextView tvTest;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
