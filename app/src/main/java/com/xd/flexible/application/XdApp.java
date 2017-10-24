package com.xd.flexible.application;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.xd.flexible.R;
import com.xd.flexible.config.Config;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.cache.DiskCacheStore;

import static com.xd.flexible.config.Config.NET_CONNECTION_TIMEOUT;
import static com.xd.flexible.config.Config.NET_READ_TIMEOUT;


/**
 * Created by flexibleXd on 2016/12/22.
 */

public class XdApp extends Application {
    private static Context ctx;

    @Override
    public void onCreate() {
        StringBuffer param = new StringBuffer();
        param.append("appid="+getString(R.string.app_id));
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE+"="+SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(XdApp.this, param.toString());

        super.onCreate();
        noHttpConfig();
        ctx = getApplicationContext();
    }


    private void noHttpConfig() {
        NoHttp.initialize(this, new NoHttp.Config().setConnectTimeout(NET_CONNECTION_TIMEOUT).setReadTimeout(NET_READ_TIMEOUT).setCacheStore(new DiskCacheStore(this)));
        Logger.setDebug(Config.DEBUG); // 开启NoHttp调试模式。
        Logger.setTag("flexible"); // 设置NoHttp打印Log的TAG。
    }

    public static Context getAppContext() {
        return ctx;
    }
}
