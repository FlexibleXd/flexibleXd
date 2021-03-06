package com.xd.flexible.network;

import android.graphics.Bitmap;

import com.yolanda.nohttp.BitmapBinary;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

/**
 * Created by flexibleXd on 2016/12/22.
 */

public class NoHttpUtils {
    /**
     * stringRequest
     *
     * @param url    api地址
     * @param method 请求方式
     */
    public static Request<String> stringRequest(String url, RequestMethod method) {
        Request<String> request = NoHttp.createStringRequest(url, method);
        return request;
    }

    public static Request<String> stringRequest(String url) {
        return stringRequest(url, RequestMethod.GET);
    }

    /**
     * jsonObeject
     */

    public static Request<JSONObject> jsonObjectRequest(String url) {
        return jsonObjectRequest(url, RequestMethod.GET);
    }

    public static Request<JSONObject> jsonObjectRequest(String url, RequestMethod method) {
        return jsonObjectRequest(url, method, null);
    }


    public static Request<JSONObject> jsonObjectRequest(String url, RequestMethod method, Map<String, String> param) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, method);
        if (param != null) {
            request.add(param);
        }
        return request;
    }


    /**
     * FastJson
     */

    public static FastJsonRequest fastJsonObjectRequest(String url) {
        return fastJsonObjectRequest(url, RequestMethod.GET);
    }

    public static FastJsonRequest fastJsonObjectRequest(String url, RequestMethod method) {
        return fastJsonObjectRequest(url, method, null);
    }

    public static FastJsonRequest fastJsonObjectRequest(String url, Map<String, String> param) {
        return fastJsonObjectRequest(url, RequestMethod.GET, param);
    }

    public static FastJsonRequest fastJsonObjectRequest(String url, RequestMethod method, Map<String, String> param) {
        FastJsonRequest request = new FastJsonRequest(url, method);
        if (param != null) {
            request.add(param);
        }
        return request;
    }


    /**
     * 同步
     *
     * @param url
     * @param param
     * @return
     */
    public static Response<com.alibaba.fastjson.JSONObject> fastJosnAsyn(String url, Map<String, String> param) {
        // 创建请求。
        FastJsonRequest request = new FastJsonRequest(url, RequestMethod.GET);
        if (param != null) {
            request.add(param);
        }
        // 调用同步请求，直接拿到请求结果。
        Response<com.alibaba.fastjson.JSONObject> response = NoHttp.startRequestSync(request);
        return response;
    }


    /**
     * javaBeanRequsst
     *
     * @param url    api地址
     * @param method 请求方式
     * @param clazz  解析成的Bean
     * @param <T>
     */
    public static <T> JavaBeanRequest<T> beanRequest(String url, Class<T> clazz, RequestMethod method) {
        return beanRequest(url, clazz, method, null);
    }

    public static <T> JavaBeanRequest<T> beanRequest(String url, Class<T> clazz, RequestMethod method, Map<String, String> param) {
        JavaBeanRequest<T> request = new JavaBeanRequest(url, method, clazz);
        if (param != null) {
            request.add(param);
        }
        return request;
    }

    public static <T> JavaBeanRequest<T> beanRequest(String url, Class<T> clazz) {
        return beanRequest(url, clazz, RequestMethod.GET);
    }

    /**
     * 上传
     *
     * @param url
     * @param method
     * @return
     */

    public static Request<com.alibaba.fastjson.JSONObject> fileRequset(String url, RequestMethod method, Map<String, File> param) {
        FastJsonRequest request = new FastJsonRequest(url, method);
        for (Map.Entry<String, File> entry : param.entrySet()) {
            request.add(entry.getKey(), new FileBinary(entry.getValue()));
        }
        return request;
    }

    /**
     * 上传
     *
     * @param url
     * @param method
     * @return
     */

    public static Request<com.alibaba.fastjson.JSONObject> bitmapRequset(String url, RequestMethod method, Map<String, Bitmap> param) {
        FastJsonRequest request = new FastJsonRequest(url, method);
        for (Map.Entry<String, Bitmap> entry : param.entrySet()) {
            request.add(entry.getKey(), new BitmapBinary(entry.getValue(), "img.jpg"));
        }
        return request;
    }


}
