package com.xd.flexible.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSONObject;
import com.xd.flexible.R;
import com.xd.flexible.activity.AddressEditActivity;
import com.xd.flexible.config.Api;
import com.xd.flexible.model.AddrBean;
import com.xd.flexible.model.event.DeleterAddrEvent;
import com.xd.flexible.network.CallServer;
import com.xd.flexible.network.NoHttpListener;
import com.xd.flexible.network.NoHttpManager;
import com.xd.flexible.network.NoHttpUtils;
import com.xd.flexible.utils.ToastUtil;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Flexible on 2017/3/29 0029.
 */

public class AddrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String ADDR = "_ADDR";

    private List<AddrBean.AddrlistBean> data;
    private Context ctx;
    private OnItemClickListener onItemClickListener;
    public Map<Integer, Boolean> isDefault = new HashMap<>();


    public AddrAdapter(List<AddrBean.AddrlistBean> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }

    private void init() {
        for (int i = 0; i < data.size(); i++) {
            isDefault.put(i, false);

        }
    }

    private void setDefault(int pos) {
        for (Map.Entry<Integer, Boolean> entry : isDefault.entrySet()) {
            if (pos == entry.getKey()) {
                entry.setValue(true);
            } else {
                entry.setValue(false);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddrVH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AddrVH addrHolder = (AddrVH) holder;
        AddrBean.AddrlistBean addrlistBean = data.get(position);
        addrHolder.tvName.setText(addrlistBean.name);
        addrHolder.tvPhone.setText(addrlistBean.phone);
        addrHolder.tvAddr.setText(addrlistBean.addr);
        switch (addrlistBean.is_default) {
            case 0:
                addrHolder.cbDefault.setChecked(false);
                addrHolder.cbDefault.setClickable(true);
                break;
            case 1:
                addrHolder.cbDefault.setChecked(true);
                addrHolder.cbDefault.setClickable(false);
                break;
        }
    }

    /**
     * 点击事件的回调
     */
    public interface OnItemClickListener {
        void onClick(View v, int pos);
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AddrVH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_addr)
        TextView tvAddr;
        @BindView(R.id.cb_default)
        CheckBox cbDefault;
        @BindView(R.id.ll_edit)
        TextView llEdit;
        @BindView(R.id.ll_deleter)
        TextView llDeleter;


        public AddrVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            init();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(v, getPosition());
                    }
                }
            });
        }

        @OnClick({R.id.ll_edit, R.id.ll_deleter, R.id.cb_default})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_edit:
                    Intent intent = new Intent(ctx, AddressEditActivity.class);
                    intent.putExtra(ADDR, data.get(getPosition()));
                    ctx.startActivity(intent);
                    break;
                case R.id.ll_deleter:
                    MaterialDialog.Builder md = new MaterialDialog.Builder(view.getContext());
                    md.content("是否确认删除地址").positiveText("确认").negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            EventBus.getDefault().post(new DeleterAddrEvent(data.get(getPosition()).addr_id));
                        }
                    }).onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).show();
                    break;
                case R.id.cb_default:
                    AddrBean.AddrlistBean addr = data.get(getPosition());
                    for (int i = 0; i < data.size(); i++) {
                        if (getPosition() == i) {
                            data.get(getPosition()).is_default = 1;
                        } else {
                            data.get(i).is_default = 0;
                        }
                    }
                    notifyDataSetChanged();
                    editAddress(addr.addr_id + "");
                    break;

            }
        }
    }


    private Object cancelObject = new Object();

    public <T> void request(int what, Request<T> request, NoHttpListener<T> httpListener) {
        // 这里设置一个sign给这个请求。
        request.setCancelSign(cancelObject);

        CallServer.getInstance().add(what, request, new NoHttpManager<T>(request,
                httpListener));
    }

    /**
     * 修改地址信息
     *
     * @param id
     *
     */
    private void editAddress(String id) {
        Map<String, String> param = new HashMap<>();
        param.put("addr_id", id);
//        param.put("is_default", is_default);
        request(1, NoHttpUtils.fastJsonObjectRequest("", RequestMethod.POST, param), new NoHttpListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject json = response.get();
                int code = json.getInteger("code");
                if (code != 1) {
                    String msg = json.getString("msg");
                    ToastUtil.showToast(ctx, msg);
                } else {
                    ToastUtil.showToast(ctx, "修改成功");
//                    EventBus.getDefault().post(new RefreshAddrEvent());
                    notifyDataSetChanged();
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

