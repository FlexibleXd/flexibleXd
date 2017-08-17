package com.xd.flexible.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.xd.flexible.R;
import com.xd.flexible.application.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Flexible on 2017/7/25 0025.
 */

public class LoadActivity extends BaseActivity {
    @BindView(R.id.rv_load)
    RecyclerView rvLoad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        ButterKnife.bind(this);
    }
}
