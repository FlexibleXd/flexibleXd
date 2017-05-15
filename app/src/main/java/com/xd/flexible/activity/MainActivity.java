package com.xd.flexible.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.xd.flexible.R;
import com.xd.flexible.application.BaseActivity;
import com.xd.flexible.network.NoHttpListener;
import com.xd.flexible.network.NoHttpUtils;
import com.xd.flexible.widget.LoadingDialog;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_base)
    RecyclerView rvBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        LoadingDialog loadingDialog = new LoadingDialog(this, -1, -1);
//        loadingDialog.show();
//        loadingDialog.dismiss();
        request(1, NoHttpUtils.stringRequest("http://www.baidu.com"), new NoHttpListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onFinish(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true);
    }
}
