package com.xd.flexible.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.xd.flexible.R;
import com.xd.flexible.application.BaseActivity;
import com.xd.flexible.model.event.LoginEvent;
import com.xd.flexible.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BottomActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, View.OnClickListener {

    @BindView(R.id.bnb_main_bottom_bar)
    BottomNavigationBar bnbMainBottomBar;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_content)
    FrameLayout mainContent;
    private ArrayList<Fragment> fragments;
    private FragmentManager fm;
    private View tbMain;
    private View tbMall;
    private View tbDiscover;
    private View tbMy;
    private ImageView ivCart;
    private EditText etSerach;
    private ImageView mainMsg;
    private ImageView discoveryMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        ButterKnife.bind(this);
        intiView();
        initData();
    }


    private void initData() {
        checkVersion();
    }


    private void intiView() {

        tbMain = LayoutInflater.from(this).inflate(R.layout.toolbar_main, null);
        tbMall = LayoutInflater.from(this).inflate(R.layout.toolbar_main, null);
        tbDiscover = LayoutInflater.from(this).inflate(R.layout.toolbar_main, null);
        tbMy = LayoutInflater.from(this).inflate(R.layout.toolbar_main, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbar.addView(tbMain, lp);
        toolbar.addView(tbMall, lp);
        toolbar.addView(tbDiscover, lp);
        toolbar.addView(tbMy, lp);

        tbMain.setVisibility(View.VISIBLE);
        tbMall.setVisibility(View.GONE);
        tbDiscover.setVisibility(View.GONE);
        tbMy.setVisibility(View.GONE);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        toolbar.setContentInsetsRelative(0, 0);
        bnbMainBottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bnbMainBottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bnbMainBottomBar.setBackgroundColor(getResources().getColor(R.color.white));
//        bnbMainBottomBar
//                .addItem(new BottomNavigationItem(R.mipmap.main_main, "首页").setActiveColorResource(R.color.colorPrimary).setInActiveColor("#ffffff"))
//                .addItem(new BottomNavigationItem(R.mipmap.main_mall, "商城").setActiveColorResource(R.color.colorPrimary).setInActiveColor("#ffffff"))
//                .addItem(new BottomNavigationItem(R.mipmap.main_discover, "发现").setActiveColorResource(R.color.colorPrimary).setInActiveColor("#ffffff"))
//                .addItem(new BottomNavigationItem(R.mipmap.main_my, "我的").setActiveColorResource(R.color.colorPrimary).setInActiveColor("#ffffff"))
//                .initialise();
        fm = getSupportFragmentManager();
        fragments = getFragments();
        setDefaultFragment();
        bnbMainBottomBar.setTabSelectedListener(this);
    }


    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.main_content, fragments.get(0));
        transaction.commit();
    }

    public ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
//        fragments.add(new MainFrgment());
//        fragments.add(new MallFrgment());
//        fragments.add(new DiscoverFrgment());
//        fragments.add(new MyFrgment());
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                tbMain.setVisibility(View.VISIBLE);
                tbMall.setVisibility(View.GONE);
                tbDiscover.setVisibility(View.GONE);
                tbMy.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                break;
            case 1:
                tbMain.setVisibility(View.GONE);
                tbMall.setVisibility(View.VISIBLE);
                tbDiscover.setVisibility(View.GONE);
                tbMy.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                break;
            case 2:
                tbMain.setVisibility(View.GONE);
                tbMall.setVisibility(View.GONE);
                tbDiscover.setVisibility(View.VISIBLE);
                tbMy.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                break;
            case 3:
                tbMain.setVisibility(View.GONE);
                tbMall.setVisibility(View.GONE);
                tbDiscover.setVisibility(View.GONE);
//                tbMy.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.GONE);
                break;
        }


        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fg = fragments.get(position);
        if (fg.isAdded()) {
            transaction.show(fg);
        } else {
            transaction.add(R.id.main_content, fg);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabUnselected(int position) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fg = fragments.get(position);
        transaction.hide(fg);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * 阻止保存fragment
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    long lastTime = 0;

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        if (curTime - lastTime > 2000) {
            lastTime = curTime;
            ToastUtil.showToast(this, "再按一次退出信德");
        } else {
            super.onBackPressed();
        }
    }


//    @Subscribe
//    public void Login(LoginEvent lg) {
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//    }

    private void checkVersion() {
//        request(1, NoHttpUtils.fastJsonObjectRequest(Api.VERSION, RequestMethod.GET), new NoHttpListener<JSONObject>() {
//
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(int what, Response<JSONObject> response) {
//                LogUtils.LOGE("versionRequest", JSON.toJSONString(response.get(), true));
//                JSONObject json = response.get();
//                int code = json.getInteger("code");
//                if (code != 1) {
//                    return;
//                }
//                final JSONObject version = json.getJSONObject("version");
//                if (version != null) {
//                    int build = AppUtil.getAPPVersionCode(MainActivity.this);
//                    int serviceBuild = version.getInteger("build");
//                    if (serviceBuild > build) {
//                        DialogUtils.SimpleDialog(MainActivity.this, "发现新版本，是否需要更新", version.getString("content"), "取消", "确定").onPositive(new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                Intent in = new Intent(MainActivity.this, DownService.class);
//                                in.putExtra("version", Api.DOMAIN_UPLOAD + version.getString("file_url"));
//                                startService(in);
//                                dialog.dismiss();
//                            }
//                        }).onNegative(new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                dialog.dismiss();
//                            }
//                        }).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(int what, Response<JSONObject> response) {
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });
    }

    @Subscribe
    public void Login(LoginEvent lg) {
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
    }

}
