package com.xd.flexible.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Flexible on 2017/4/6 0006.
 */

public class BaseBean implements Parcelable {
    public int code;
    public String msg;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
    }

    public static final Parcelable.Creator<BaseBean> CREATOR = new Creator<BaseBean>() {
        @Override
        public BaseBean createFromParcel(Parcel source) {
            return new BaseBean(source);
        }

        @Override
        public BaseBean[] newArray(int size) {
            return new BaseBean[size];
        }

    };

    private BaseBean(Parcel in) {
        code = in.readInt();
        msg = in.readString();
    }
}
