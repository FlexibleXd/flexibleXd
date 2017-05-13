package com.xd.flexible.config;

/**
 * Created by Flexible on 2017/4/6 0006.
 */

public interface Config {
    Boolean DEBUG = true;

    int NET_CONNECTION_TIMEOUT = 10 * 1000;

    int NET_READ_TIMEOUT = 20 * 1000;
    int MAIN_TURNING = 4 * 1000;
    int ACTIVITY_TURNING = 6 * 1000;
    int SPLASH_TIME = 5 * 1000;
    String WX_APPID = "";

}
