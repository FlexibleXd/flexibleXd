package com.xd.flexible.application;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.xd.flexible.R;
import com.xd.flexible.network.CallServer;
import com.xd.flexible.network.NoHttpListener;
import com.xd.flexible.network.NoHttpManager;
import com.yolanda.nohttp.rest.Request;

import java.io.Serializable;


/**
 * Created by flexible on 2016/12/12.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private View loadProgress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
    }


    public void isLoad(boolean isLoad) {
        if (loadProgress == null) {
            loadProgress = LayoutInflater.from(this).inflate(R.layout.view_load_progress, null);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            addContentView(loadProgress, params);
        }
        if (isLoad) {
            loadProgress.setVisibility(View.VISIBLE);
        } else {
            loadProgress.setVisibility(View.GONE);
        }
    }







    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }

    public void startActivityForResult(Class<? extends Activity> clazz, int code) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivityForResult(intent, code);
    }


    public void startActivityForResult(Class<? extends Activity> clazz, int code, String... data) {
        if (data.length % 2 == 1) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        for (int i = 0; i < data.length / 2; i++) {
            intent.putExtra(data[i * 2], data[i * 2 + 1]);
        }
        startActivityForResult(intent, code);
    }

    public void startActivity(Class<? extends Activity> clazz, String... data) {
        if (data.length % 2 == 1) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        for (int i = 0; i < data.length / 2; i++) {
            intent.putExtra(data[i * 2], data[i * 2 + 1]);
        }
        startActivity(intent);
    }

    public void startActivity(Class<? extends Activity> clazz, String data, Serializable obj) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra(data, obj);
        startActivity(intent);
    }

    private Object cancelObject = new Object();


    public <T> void request(int what, Request<T> request, NoHttpListener<T> httpListener) {
        // 这里设置一个sign给这个请求。
        request.setCancelSign(cancelObject);

        CallServer.getInstance().add(what, request, new NoHttpManager<T>(request,
                httpListener));
    }

    @Override
    public void onClick(View v) {

    }
}