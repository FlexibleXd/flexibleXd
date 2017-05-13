package com.xd.flexible.application;


import com.xd.flexible.utils.SPUtils;

/**
 * Created by Flexible on 2017/1/9 0009.
 */

public class DataKeeper {
    private volatile static DataKeeper keeper;
    private SPUtils sp = new SPUtils(XdApp.getAppContext(), "DataKeeper");
    private final String HOT_GOODS = "_HOT_GOODS";
    private final String GOODS = "_GOODS";
    private final String CLASSES = "_CLASSES";

    private DataKeeper() {
    }

    public static DataKeeper getInstance() {
        if (keeper == null) {
            synchronized (DataKeeper.class) {
                if (keeper == null) {
                    keeper = new DataKeeper();
                }
            }
        }
        return keeper;
    }

    public void keepHotGoods(String data) {
        sp.putString(HOT_GOODS, data);
    }

    public String getHotGoods() {
        return sp.getString(HOT_GOODS, "");
    }

    public void keepClasses(String data) {
        sp.putString(CLASSES, data);
    }

    public String getClasses() {
        return sp.getString(CLASSES, "");
    }

//    public void keepGoods(String data) {
//        sp.putString(GOODS, data);
//    }
//
//    public String getGoods() {
//        return sp.getString(GOODS, "");
//    }

}
