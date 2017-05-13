package com.xd.flexible.network;

import com.xd.flexible.R;
import com.xd.flexible.application.XdApp;
import com.xd.flexible.model.event.LoginEvent;
import com.xd.flexible.utils.LogUtils;
import com.xd.flexible.utils.ToastUtil;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Flexible on 2017/2/6 0006.
 */

public class NoHttpManager<T> implements OnResponseListener<T> {


    private NoHttpListener<T> mListener;
    private Request<T> mRequest;

    public NoHttpManager(Request<T> request, NoHttpListener<T>
            httpListener) {
        mListener = httpListener;
        this.mRequest = request;
    }

    @Override
    public void onStart(int what) {
        if (mListener != null) {
            mListener.onStart(what);
        }
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        if (response.responseCode() == 401) {
            EventBus.getDefault().post(new LoginEvent());
            return;
        }

        if (mListener != null) {
            mListener.onSucceed(what, response);
        }
    }


    @Override
    public void onFailed(int what, Response<T> response) {
        LogUtils.LOGE("Exception", response.getException().toString());
        ToastUtil.showToastBottom(XdApp.getAppContext(), XdApp.getAppContext().getString(R.string.request_faile));
        if (mListener != null) {
            mListener.onFailed(what, response);
        }
    }

    @Override
    public void onFinish(int what) {
        if (mListener != null) {
            mListener.onFinish(what);
        }
    }
}
