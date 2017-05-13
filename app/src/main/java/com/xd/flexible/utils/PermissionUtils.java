package com.xd.flexible.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.xd.flexible.application.XdApp;


/**
 * Created by Flexible on 2017/2/6 0006.
 */

public class PermissionUtils {
    public static void requestPermission(Activity act, String permission, int code, String msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(act, permission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(act, permission)) {
                    ToastUtil.showToastBottom(XdApp.getAppContext(), msg);
                } else {
                    ActivityCompat.requestPermissions(act, new String[]{permission}, code);
                }
            }
        }
    }
}