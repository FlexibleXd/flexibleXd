package com.xd.flexible.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.xd.flexible.model.city.China;
import com.xd.flexible.model.city.Province;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Flexible on 2017/1/17 0017.
 */

public class PickUtils {
    public static ArrayList<Province> options1Items = new ArrayList<>();
    public static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    public static ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

    /**
     * 初始化pickerView
     */
    public static OptionsPickerView intiPickerView(Context ctx, final TextView tvAddrProvince, final TextView tvAddrCity, final TextView tvAddrArea) {
        initData(ctx);
        //选项选择器
//        OptionsPickerView pvCity = new OptionsPickerView(ctx);
//        //三级联动效果
//        pvCity.setPicker(options1Items, options2Items, options3Items, true);
//        //设置选择的三级单位
//        pvCity.setTitle("选择城市");
//        pvCity.setCancelable(true);
//        pvCity.setCyclic(false, false, false);
//        //设置默认选中的三级项目
//        //监听确定选择按钮
//        pvCity.setSelectOptions(14, 6, 0);
//        pvCity.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3) {
//                //返回的分别是三个级别的选中位置
//                tvAddrProvince.setText(options1Items.get(options1).getName());
//                tvAddrCity.setText(options2Items.get(options1).get(option2));
//                tvAddrArea.setText(options3Items.get(options1).get(option2).get(options3));
//            }
//        });
//条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(ctx, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(option2)
//                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                tvAddrProvince.setText(options1Items.get(options1).getName());
                tvAddrCity.setText(options2Items.get(options1).get(option2));
                tvAddrArea.setText(options3Items.get(options1).get(option2).get(options3));
//                tvOptions.setText(tx);
            }
        }).setTitleText("选择城市").setCyclic(false,false,false).build();
        pvOptions.setSelectOptions(14, 6, 0);
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
        return pvOptions;
    }


    public static OptionsPickerView intiPickerView(Context ctx) {
        initData(ctx);
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(ctx, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
            }
        }).setTitleText("选择城市").setCyclic(false,false,false).build();
        pvOptions.setSelectOptions(14, 6, 0);
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
        return pvOptions;
    }


    public static TimePickerView initTimeView(final Context ctx, final TextView tvTime) {
//        //选项选择器
//        TimePickerView pvTime = new TimePickerView(ctx, TimePickerView.Type.YEAR_MONTH_DAY);
//        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1);//要在setTime 之前才有效果哦
//        pvTime.setTime(new Date());
//        pvTime.setCyclic(false);
//        pvTime.setCancelable(true);
//        //时间选择后回调
//        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
//
//            @Override
//            public void onTimeSelect(Date date) {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                tvTime.setText(format.format(date));
//            }
//        });


        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(ctx, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                tvTime.setText(date.toString());
            }
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();


        return pvTime;
    }

//    public static TimePickerView initTimeView(final Context ctx, final TextView tvTime, int Time) {
////        //选项选择器
////        TimePickerView pvTime = new TimePickerView(ctx, TimePickerView.Type.YEAR_MONTH_DAY);
////        //控制时间范围
////        Calendar calendar = Calendar.getInstance();
////        pvTime.setRange(calendar.get(Calendar.YEAR) - Time, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
////        pvTime.setTime(new Date());
////        pvTime.setCyclic(false);
////        pvTime.setCancelable(true);
////        //时间选择后回调
////        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
////
////            @Override
////            public void onTimeSelect(Date date) {
////                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
////                tvTime.setText(format.format(date));
////            }
////        });
//
////时间选择器
//        TimePickerView pvTime = new TimePickerView.Builder(ctx, new TimePickerView.OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date,View v) {//选中事件回调
//            }
//        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
//                .build();
//        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
//        pvTime.show();
//        return pvTime;
//    }

    /**
     * 初始化json数据
     */
    private static void initData(Context ctx) {
        try {
            //解析json数据
            InputStream is = ctx.getResources().getAssets().open("city.json");
            int available = is.available();
            byte[] b = new byte[available];
            int read = is.read(b);
            //注意格式，utf-8 或者gbk  否则解析出来可能会出现乱码
            String json = new String(b, "gbk");
            China china = JSON.parseObject(json, China.class);
            ArrayList<China.Province> citylist = china.citylist;
            //======省级
            for (China.Province province : citylist) {
                String provinceName = province.p;
                ArrayList<China.Province.Area> c = province.c;
                //选项1
                options1Items.add(new Province(0, provinceName, "", ""));
                ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
                //区级
                //选项2
                ArrayList<String> options2Items_01 = new ArrayList<String>();
                if (c != null) {
                    for (China.Province.Area area : c) {
                        options2Items_01.add(area.n);
                        ArrayList<China.Province.Area.Street> a = area.a;
                        ArrayList<String> options3Items_01_01 = new ArrayList<String>();
                        //县级
                        if (a != null) {
                            for (China.Province.Area.Street street : a) {
                                options3Items_01_01.add(street.s);
                            }
                            options3Items_01.add(options3Items_01_01);
                        } else {
                            //县级为空的时候
                            options3Items_01_01.add("");
                            options3Items_01.add(options3Items_01_01);
                        }
                    }
                    options2Items.add(options2Items_01);
                } else {
                    //区级为空的时候  国外
                    options2Items_01.add("");
                    options2Items.add(options2Items_01);
                }
                options3Items.add(options3Items_01);
                ArrayList<String> options3Items_01_01 = new ArrayList<String>();
                options3Items_01_01.add("");
                options3Items_01.add(options3Items_01_01);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
