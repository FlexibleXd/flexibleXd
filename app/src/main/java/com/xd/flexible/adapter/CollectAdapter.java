package com.xd.flexible.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.xd.flexible.R;
import com.xd.flexible.config.Api;
import com.xd.flexible.model.CollectBean;
import com.xd.flexible.model.ProductBean;
import com.xd.flexible.network.CallServer;
import com.xd.flexible.network.NoHttpListener;
import com.xd.flexible.network.NoHttpManager;
import com.xd.flexible.network.NoHttpUtils;
import com.xd.flexible.utils.GlideUtils;
import com.xd.flexible.utils.ToastUtil;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Flexible on 2017/1/21 0021.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public CollectAdapter(List<CollectBean.CollectionsBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    private List<CollectBean.CollectionsBean> data;
    private Context context;

    @Override
    public CollectVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect, parent, false);
        return new CollectVH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CollectVH collectHolder = (CollectVH) holder;
        CollectBean.CollectionsBean bean = data.get(position);
        CollectVH mHolder = (CollectVH) holder;
        ProductBean pro = bean.pro;
        if (pro.pro_img != null)
            GlideUtils.pro2Iv(context, Api.DOMAIN_IMG + pro.pro_img, mHolder.ivImg);
        collectHolder.tvName.setText(pro.pro_name);
        collectHolder.tvPrice.setText(TextUtils.concat("¥", pro.price + ""));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class CollectVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_cancle)
        TextView tvCancle;


        public CollectVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvCancle.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cancelCollect(data.get(getPosition()).pro_id, getPosition());
        }

    }

    private void cancelCollect(int id, final int pos) {
        Map<String, String> param = new HashMap<>();
        param.put("pro_id", id + "");
        request(1, NoHttpUtils.fastJsonObjectRequest("", RequestMethod.GET, param), new NoHttpListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject json = response.get();
                int code = json.getInteger("code");
                if (code != 1) {
                    String msg = json.getString("msg");
                    ToastUtil.showToast(context, msg);
                    return;
                }
                ToastUtil.showToast(context, "取消成功");
                data.remove(pos);
                notifyDataSetChanged();
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
            }

            @Override
            public void onFinish(int what) {

            }
        });


    }


    private Object cancelObject = new Object();

    public <T> void request(int what, Request<T> request, NoHttpListener<T> httpListener) {
        // 这里设置一个sign给这个请求。
        request.setCancelSign(cancelObject);

        CallServer.getInstance().add(what, request, new NoHttpManager<T>(request,
                httpListener));
    }
}
