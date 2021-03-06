package com.xd.flexible.application;

import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xd.flexible.R;
import com.xd.flexible.model.listener.NoDataOnClickListener;
import com.xd.flexible.model.listener.RefreshOnClickListener;
import com.xd.flexible.utils.GlideUtils;
import com.xd.flexible.utils.NetworkUtils;
import com.xd.flexible.utils.StringUtils;

import butterknife.ButterKnife;


/**
 * Created by flexibleXd on 2016/12/23.
 */

public class ToolBarActivity extends BaseActivity {
    private Toolbar toolbar;
    private ViewGroup container;
    private ViewGroup frame;
    private View noNet;
    private TextView tvRefresh;
    private View noData;
    private TextView tvNoData;
    public static final String _TITLE = "TOOL_TITLE";
    private ImageView ivNoData;
    private TextView tvNoClick;

    public void setCheckNet(Boolean checkNet) {
        isCheckNet = checkNet;
    }

    private Boolean isCheckNet = true;

    @Override
    public void setContentView(int layoutResID) {
        View _iView = getLayoutInflater().inflate(layoutFrameResId(),
                null);
        toolbar = (Toolbar) _iView.findViewById(R.id.flexible__toolbar);
        container = (ViewGroup) _iView.findViewById(R.id.flexible__container);
        frame = (ViewGroup) _iView.findViewById(R.id.flexible__frame);
        initToolbar();
        if (layoutResID > 0) {
            View append = getLayoutInflater().inflate(layoutResID, null);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            getContainer().addView(append, layoutParams);
        }
        noNet();
        super.setContentView(_iView);
    }


    /**
     * 无网络回调
     */

    private RefreshOnClickListener refreshOnClickListener;


    public void setRefreshOnClickListener(RefreshOnClickListener refreshOnClickListener) {
        this.refreshOnClickListener = refreshOnClickListener;
    }

    /**
     * 无数据回调
     */
    private NoDataOnClickListener noDataOnClickListener;


    public void setNodataOnClickListener(NoDataOnClickListener noDataOnClickListener) {
        this.noDataOnClickListener = noDataOnClickListener;
    }


    /**
     * 无数据页面
     *
     * @param isShow    是否显示
     * @param hasClick  是否有按钮点击
     * @param imgId     图片资源id
     * @param text      提示语言
     * @param clickText 按钮文字
     */

    public void noData(boolean isShow, boolean hasClick, int imgId, String text, String clickText) {
        if (noData == null) {
            noData = LayoutInflater.from(this).inflate(R.layout.view_no_data, null);
            tvNoData = ButterKnife.findById(noData, R.id.tv_no_data);
            ivNoData = ButterKnife.findById(noData, R.id.iv_no);
            tvNoClick = ButterKnife.findById(noData, R.id.tv_click);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            getContainer().addView(noData, params);
        }
        GlideUtils.resouce2Iv(XdApp.getAppContext(), imgId, ivNoData);
        tvNoData.setText(text);
        if (hasClick) {
            tvNoClick.setText(clickText);
            tvNoClick.setVisibility(View.VISIBLE);
        } else {
            tvNoClick.setVisibility(View.GONE);
        }

        tvNoClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noDataOnClickListener != null)
                    noDataOnClickListener.onClick();
            }
        });
        if (isShow) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
    }

    /**
     * 无数据页面
     *
     * @param isShow 是否显示
     */
    public void noData(boolean isShow) {
        if (noData != null) {
            if (isShow) {
                noData.setVisibility(View.VISIBLE);
            } else {
                noData.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 无网络
     */
    public void noNet() {
        if (!isCheckNet) {
            return;
        }
        if (noNet == null) {
            noNet = LayoutInflater.from(this).inflate(R.layout.view_no_net, null);
            tvRefresh = ButterKnife.findById(noNet, R.id.tv_refresh);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            getContainer().addView(noNet, params);
        }
        if (NetworkUtils.isConnected(this)) {
            noNet.setVisibility(View.GONE);
            getContainer().removeView(noNet);
        } else {
            noNet.setVisibility(View.VISIBLE);

        }
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (refreshOnClickListener != null)
                    refreshOnClickListener.onClick();
                if (NetworkUtils.isConnected(XdApp.getAppContext())) {
                    noNet.setVisibility(View.GONE);
                    getContainer().removeView(noNet);
                } else {
                    noNet.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    protected int layoutFrameResId() {
        return R.layout.flexible__content;
    }

    public ViewGroup getContainer() {
        return container;
    }

    public ViewGroup getFrame() {
        return frame;
    }

    protected void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String title = getIntent().getStringExtra(_TITLE);
        if (!StringUtils.isEmpty(title)) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
