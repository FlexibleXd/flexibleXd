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

import static android.R.attr.data;
import static android.R.attr.isDefault;
import static com.xd.flexible.adapter.AddrAdapter.ADDR;


/**
 * Created by Flexible on 2017/3/29 0029.
 */

public class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> data;
    private Context ctx;
    private OnItemClickListener onItemClickListener;


    public BaseAdapter(List<Object> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new BaseVH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
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

    class BaseVH extends RecyclerView.ViewHolder {


        public BaseVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(v, getPosition());
                    }
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
}

