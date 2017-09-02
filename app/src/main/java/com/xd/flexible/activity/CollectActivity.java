package com.xd.flexible.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.xd.flexible.R;
import com.xd.flexible.adapter.CollectAdapter;
import com.xd.flexible.application.ToolBarActivity;
import com.xd.flexible.config.Api;
import com.xd.flexible.model.CollectBean;
import com.xd.flexible.network.NoHttpListener;
import com.xd.flexible.network.NoHttpUtils;
import com.xd.flexible.utils.ToastUtil;
import com.xd.flexible.widget.DividerLine;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Flexible on 2017/1/11 0011.
 */

public class CollectActivity extends ToolBarActivity {
    @BindView(R.id.ptr_load)
    PtrClassicFrameLayout ptrLoad;
    @BindView(R.id.rv_collect)
    RecyclerView rvCollect;
    private CollectAdapter collectAdapter;
    private List<CollectBean.CollectionsBean> companyList;
    private RecyclerAdapterWithHF mAdapter;
    private int page = 1;
    private boolean hasMore = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcv);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
//        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
//                startActivity(SupplyDetailActivity.class,"data",co);
//            }
//        });
        //加载更多
        ptrLoad.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                if (!hasMore) {
                    ToastUtil.showToast(CollectActivity.this, "没有更多了");
                    ptrLoad.loadMoreComplete(true);
                    ptrLoad.setLoadMoreEnable(false);
                    return;
                }
                LoadRequest();
            }
        });
        //下拉刷新
        ptrLoad.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                companyRequest();
            }
        });
    }

    private void initData() {
        companyRequest();
    }

    private void companyRequest() {
        isLoad(true);
        Map<String, String> param = new HashMap<>();
        param.put("pageNumber", 1 + "");
        request(1, NoHttpUtils.beanRequest("",  CollectBean.class,RequestMethod.GET, param), new NoHttpListener<CollectBean>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<CollectBean> response) {
                hasMore = true;
                ptrLoad.refreshComplete();
                ptrLoad.setLoadMoreEnable(true);
                CollectBean bean = response.get();
                if (bean.code != 1) {
                    ToastUtil.showToast(CollectActivity.this, bean.msg);
                    return;
                }
                int pageSize = bean.pager.pageSize;
                companyList.clear();
                if (bean.collections == null || bean.collections.size() == 0) {
//                    noData(true, false, R.mipmap.no_collect, "还没有任何收藏", "");
                    ToastUtil.showToastBottom(CollectActivity.this, "没有更多了");
                    ptrLoad.setLoadMoreEnable(false);
                    hasMore = false;
                } else {
                    noData(false);
                    companyList.addAll(bean.collections);
                    collectAdapter.notifyDataSetChanged();
                    if (bean.collections.size() < pageSize) {
                        hasMore = false;
                        ptrLoad.setLoadMoreEnable(false);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<CollectBean> response) {
            }

            @Override
            public void onFinish(int what) {
                isLoad(false);
            }
        });
    }

    private void LoadRequest() {
        page++;
        isLoad(true);
        Map<String, String> param = new HashMap<>();
        param.put("pageNumber", page + "");
        request(1, NoHttpUtils.beanRequest("", CollectBean.class, RequestMethod.GET, param), new NoHttpListener<CollectBean>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<CollectBean> response) {
                ptrLoad.loadMoreComplete(true);
                CollectBean bean = response.get();
                if (bean.code != 1) {
                    ToastUtil.showToast(CollectActivity.this, bean.msg);
                    return;
                }
                int pageSize = bean.pager.pageSize;
                if (bean.collections == null || bean.collections.size() == 0) {
                    ToastUtil.showToast(CollectActivity.this, "没有更多了");
                    ptrLoad.setLoadMoreEnable(false);
                    hasMore = false;
                } else {
                    companyList.addAll(bean.collections);
                    collectAdapter.notifyDataSetChanged();
                    if (bean.collections.size() < pageSize) {
                        hasMore = false;
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<CollectBean> response) {
                page--;
            }

            @Override
            public void onFinish(int what) {
                isLoad(false);
            }
        });
    }

    private void initView() {
        rvCollect.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerLine line = new DividerLine(DividerLine.VERTICAL);
        line.setColor(getResources().getColor(R.color.divider));
        line.setSize(1);
        rvCollect.addItemDecoration(line);
        companyList = new ArrayList<>();
        collectAdapter = new CollectAdapter(companyList, this);
        mAdapter = new RecyclerAdapterWithHF(collectAdapter);
        rvCollect.setAdapter(mAdapter);
        ptrLoad.setAutoLoadMoreEnable(true);
    }
}
