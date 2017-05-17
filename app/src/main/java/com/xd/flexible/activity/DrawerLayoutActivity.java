package com.xd.flexible.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.xd.flexible.R;
import com.xd.flexible.application.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerLayoutActivity extends BaseActivity {

//    @BindView(R.id.tl_main)
//    TabLayout tlMain;
    @BindView(R.id.tb_main)
    Toolbar tbMain;
//    @BindView(R.id.vp_main)
//    ViewPager vpMain;
    @BindView(R.id.dl_main)
    DrawerLayout dlMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //ToolBar DrawerLayout
        tbMain.setTitle("");
        setSupportActionBar(tbMain);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, dlMain, tbMain, 0, 0);
        drawerToggle.syncState();
        dlMain.setDrawerListener(drawerToggle);
    }
}
