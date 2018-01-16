package com.xd.flexible.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xd.flexible.R;
import com.xd.flexible.adapter.refensh.LoadMoreListener;
import com.xd.flexible.adapter.RcvAdapter;
import com.xd.flexible.application.ToolBarActivity;
import com.xd.flexible.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Flexible on 2017/10/31 0031.
 */

public class RcvActivity extends ToolBarActivity {
    @BindView(R.id.rv_load)
    RecyclerView rvLoad;
    @BindView(R.id.ptr_frame)
    PtrFrameLayout ptrFrame;

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

        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(this);
        ptrFrame.setHeaderView(defaultHeader);
        ptrFrame.addPtrUIHandler(defaultHeader);
        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                LogUtils.LOGE("z","zzzz");
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrame.refreshComplete();
                    }
                }, 1800);
            }

        });
        rvLoad.setAdapter(rcvAdapter);
    }
}
