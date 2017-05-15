package com.xd.flexible.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.xd.flexible.R;
import com.xd.flexible.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_base)
    RecyclerView rvBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LoadingDialog loadingDialog = new LoadingDialog(this, -1, -1);
        loadingDialog.show();
        loadingDialog.dismiss();
    }
}
