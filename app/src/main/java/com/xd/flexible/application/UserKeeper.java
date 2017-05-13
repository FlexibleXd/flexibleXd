package com.xd.flexible.application;


import com.xd.flexible.utils.SPUtils;

/**
 * Created by Flexible on 2017/1/9 0009.
 */

public class UserKeeper {
    private volatile static UserKeeper keeper;
    private SPUtils sp = new SPUtils(XdApp.getAppContext(), "UserKeeper");
    private final String ACCOUNT = "_ACCOUNT";
    private final String IS_FRIST = "_IS_FRIST";
    private final String IS_LOGIN = "_IS_LOGIN";
    private final String DEFAULT_ADDR = "_DEAULT_ADDR";

    private UserKeeper() {
    }

    public static UserKeeper getInstance() {
        if (keeper == null) {
            synchronized (UserKeeper.class) {
                if (keeper == null) {
                    keeper = new UserKeeper();
                }
            }
        }
        return keeper;
    }

    public void keepAccount(String data) {
        sp.putString(ACCOUNT, data);
    }

    public String getAccount() {
        return sp.getString(ACCOUNT, "");
    }

    public void keepIsFrist(String data) {
        sp.putString(IS_FRIST, data);
    }

    public String getIsFrist() {
        return sp.getString(IS_FRIST, "");
    }

    public void keepIsLogin(Boolean data) {
        sp.putBoolean(IS_LOGIN, data);
    }

    public Boolean getIsLogin() {
        return sp.getBoolean(IS_LOGIN, false);
    }

    public void keepAddr(String data) {
        sp.putString(DEFAULT_ADDR, data);
    }

    public String getAddr() {
        return sp.getString(DEFAULT_ADDR, "");
    }
}
