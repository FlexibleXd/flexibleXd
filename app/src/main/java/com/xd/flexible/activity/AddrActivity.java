package com.xd.flexible.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xd.flexible.R;
import com.xd.flexible.adapter.AddrAdapter;
import com.xd.flexible.application.ToolBarActivity;
import com.xd.flexible.application.UserKeeper;
import com.xd.flexible.config.Api;
import com.xd.flexible.model.AddrBean;
import com.xd.flexible.model.event.DeleterAddrEvent;
import com.xd.flexible.model.event.RefreshAddrEvent;
import com.xd.flexible.model.listener.NoDataOnClickListener;
import com.xd.flexible.network.NoHttpListener;
import com.xd.flexible.network.NoHttpUtils;
import com.xd.flexible.utils.LogUtils;
import com.xd.flexible.utils.SizeUtils;
import com.xd.flexible.utils.ToastUtil;
import com.xd.flexible.widget.DividerLine;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Flexible on 2017/4/5 0005.
 */

public class AddrActivity extends ToolBarActivity {
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;
    @BindView(R.id.rv_addr)
    RecyclerView rvAddr;
    private List<AddrBean.AddrlistBean> addrList;
    private AddrAdapter addrAdapter;
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        if (!TextUtils.isEmpty(type)) {
            addrAdapter.setOnClickListener(new AddrAdapter.OnItemClickListener() {
                @Override
                public void onClick(View v, int position) {
                    Intent intent = new Intent();
                    intent.putExtra("addr", addrList.get(position));
                    setResult(1, intent);
                    finish();
                }
            });
        }
    }

    private void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            type = intent.getStringExtra("type");
        }


        addrRequest();
    }

    @OnClick(R.id.tv_add_address)
    public void onClick() {
        startActivity(AddressAddActivity.class);
    }

    /**
     * 获取地址列表
     */
    private void addrRequest() {
        request(1, NoHttpUtils.beanRequest("", AddrBean.class), new NoHttpListener<AddrBean>() {
            @Override
            public void onStart(int what) {
                isLoad(true);
            }

            @Override
            public void onSucceed(int what, Response<AddrBean> response) {
                AddrBean addrBean = response.get();
                LogUtils.LOGE("addr", JSON.toJSONString(addrBean, true));
                if (addrBean.code != 1) {
                    ToastUtil.showToast(AddrActivity.this, addrBean.msg);
                    return;
                }
                List<AddrBean.AddrlistBean> bean = addrBean.addrlist;
                addrList.clear();
                for (AddrBean.AddrlistBean addr : bean) {
                    if (addr.is_default == 1) {
                        UserKeeper.getInstance().keepAddr(JSON.toJSONString(addr));
                    }
                }
                if (bean == null || bean.size() == 0) {
//                    noData(true, true, R.mipmap.no_addr, "您还没有添加收货地址", "添加地址");
                    UserKeeper.getInstance().keepAddr("");
                    addrAdapter.notifyDataSetChanged();
                    ToastUtil.showToastBottom(AddrActivity.this, "当前用户没有地址");
                    return;
                }
                noData(false);
                addrList.addAll(bean);
                addrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int what, Response<AddrBean> response) {
            }

            @Override
            public void onFinish(int what) {
                isLoad(false);
            }
        });
    }

    private void initView() {
        rvAddr.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        addrList = new ArrayList<>();
        addrAdapter = new AddrAdapter(addrList, this);
        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setColor(getResources().getColor(R.color.flexible_background));
        dividerLine.setSize(SizeUtils.px2dp(this, 20));

        rvAddr.addItemDecoration(dividerLine);
        rvAddr.setAdapter(addrAdapter);
        setNodataOnClickListener(new NoDataOnClickListener() {
            @Override
            public void onClick() {
                startActivity(AddressAddActivity.class);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshAddr(RefreshAddrEvent event) {
        addrRequest();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(DeleterAddrEvent event) {
        deleterAddress(event.a + "");
    }


    private void deleterAddress(String id) {
        Map<String, String> param = new HashMap<>();
        param.put("addr_id", id);
        request(1, NoHttpUtils.fastJsonObjectRequest("", param), new NoHttpListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject json = response.get();
                LogUtils.LOGE("addr", JSON.toJSONString(json, true));
                int code = json.getInteger("code");
                if (code != 1) {
                    String msg = json.getString("msg");
                    ToastUtil.showToast(AddrActivity.this, msg);
                } else {
                    ToastUtil.showToast(AddrActivity.this, "删除成功");
                    addrRequest();
                }

            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
