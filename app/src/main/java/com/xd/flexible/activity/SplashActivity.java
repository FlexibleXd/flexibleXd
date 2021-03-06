package com.xd.flexible.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xd.flexible.R;
import com.xd.flexible.application.BaseActivity;
import com.xd.flexible.utils.AppUtil;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xd.flexible.config.Config.SPLASH_TIME;


/**
 * Created by flexible on 2016/4/29 0029.
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.tv_skip)
    TextView tvSkip;
    private Handler mDelayHandler = new Handler();
    private boolean mIsEnableBack = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
//        try {
//            Object service = getSystemService("statusbar");
//            Class<?> statusBarManager = Class.forName("android.app.StatusBarManage");
//            Method disable = statusBarManager.getMethod("disable", int.class);
//            disable.invoke(service,View.STATUS_BAR_HIDDEN ); // 为View.STATUS_BAR_DISABLE_HOME 的值/
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    private void initView() {
        mDelayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toNext();

            }
        }, SPLASH_TIME);
        AppUtil.timerSeckill(tvSkip, SPLASH_TIME, 1000, 1);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toNext();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mIsEnableBack) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler.removeCallbacksAndMessages(null);
            mDelayHandler = null;
        }
        super.onDestroy();
    }

    protected void toNext() {
//        startActivity(MainActivity.class);
//        finish();
    }
}
