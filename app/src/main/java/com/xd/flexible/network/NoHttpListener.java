package com.xd.flexible.network;

import com.yolanda.nohttp.rest.Response;

/**
 * Created by Flexible on 2017/2/7 0007.
 */

public interface NoHttpListener<T> {
    void onStart(int what);

    void onFinish(int what);


    void onSucceed(int what, Response<T> response);

    void onFailed(int what, Response<T> response);

}
