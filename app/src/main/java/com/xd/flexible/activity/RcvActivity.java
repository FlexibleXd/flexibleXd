package com.xd.flexible.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xd.flexible.R;
import com.xd.flexible.adapter.LoadMoreListener;
import com.xd.flexible.adapter.RcvAdapter;
import com.xd.flexible.application.ToolBarActivity;
import com.yolanda.nohttp.PosterHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Flexible on 2017/10/31 0031.
 */

public class RcvActivity extends ToolBarActivity {
    @BindView(R.id.rv_load)
    RecyclerView rvLoad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        ButterKnife.bind(this);
        rvLoad.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("z");
        }
        final RcvAdapter rcvAdapter = new RcvAdapter(data);
        rcvAdapter.setLoadEnable(true);
        rcvAdapter.autoLoadMore(true);
        rcvAdapter.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.add("z");
                        rcvAdapter.setLoading(false);
                        rcvAdapter.notifyData();
                    }
                }, 3000);

            }
        });
        rvLoad.setAdapter(rcvAdapter);
    }
}
