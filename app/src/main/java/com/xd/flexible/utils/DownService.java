package com.xd.flexible.utils;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;


import com.xd.flexible.R;

import java.io.File;


/**
 * Created by flexible on 2016/8/24
 */
public class DownService extends Service {
    private DownloadManager downManager;
    private String build;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            String v = intent.getStringExtra("version");
            build = intent.getStringExtra("build");
            handleVersion(v);
        } catch (Exception e) {
        }
        broadcaster();
        return super.onStartCommand(intent, flags, startId);
    }

    private void broadcaster() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(new DownLoadCompleteReceiver(), filter);
    }

    private void handleVersion(String v) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(v));
        //设置在什么网络情况下进行下载
//            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(getResources().getString(R.string.app_name));
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "xx"+build+".apk");
        downManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downManager.enqueue(request);

    }

    class DownLoadCompleteReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/xx"+build+".apk")),
                        "application/vnd.android.package-archive");
                startActivity(intent);
                stopSelf();
            }
        }
    }
}



