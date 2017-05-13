package com.xd.flexible.model;

/**
 * Created by Flexible on 2017/2/14 0014.
 */

public enum OrderType {

    PAY("未付款"), SEND("待发货"), SURE("待收货"), FINISH("已完成"), CLOSE("已取消");
    private String s;

    public String getType() {
        return s;
    }

    OrderType(String s) {
        this.s = s;

    }
}
