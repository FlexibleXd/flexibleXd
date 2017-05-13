package com.xd.flexible.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xd.flexible.R;


/**
 * Created by flexibleXd on 2016/12/22.
 * <p>
 * desc  : glide封装
 */

public class GlideUtils {
//    public static void glide2Iv(Context ctx, String imageUrl, ImageView imageView) {
//        Glide.with(ctx).load(imageUrl).centerCrop().into(imageView);
//    }


    /**
     * 产品图片 有默认图片
     *
     * @param ctx
     * @param imageUrl
     * @param imageView
     */
    public static void pro2Iv(Context ctx, String imageUrl, ImageView imageView) {
        Glide.with(ctx).load(imageUrl).centerCrop().placeholder(R.mipmap.default_pro).into(imageView);
    }

    /**
     * 普通加载 无默认 int
     *
     * @param ctx
     * @param resouceId
     * @param imageView
     */
    public static void resouce2Iv(Context ctx, int resouceId, ImageView imageView) {
        Glide.with(ctx).load(resouceId).centerCrop().into(imageView);
    }

    /**
     * 头像 int
     *
     * @param ctx
     * @param imageUrl
     * @param imageView
     */
    public static void head2Iv(Context ctx, int imageUrl, ImageView imageView) {
        Glide.with(ctx).load(imageUrl).centerCrop().placeholder(R.mipmap.defatult_head).into(imageView);
    }

    /**
     * 头像 string
     *
     * @param ctx
     * @param imageUrl
     * @param imageView
     */
    public static void head2Iv(Context ctx, String imageUrl, ImageView imageView) {
        Glide.with(ctx).load(imageUrl).centerCrop().placeholder(R.mipmap.defatult_head).into(imageView);
    }

    /**
     * string
     *
     * @param ctx
     * @param imageUrl
     * @param imageView
     */
    public static void default2Iv(Context ctx, String imageUrl, int defaultImg, ImageView imageView) {
        Glide.with(ctx).load(imageUrl).centerCrop().placeholder(defaultImg).into(imageView);
    }

    //    public static void glide2Iv(Context ctx, String imageUrl, ImageView imageView, int defaultIv) {
//        Glide.with(ctx).load(imageUrl).centerCrop().placeholder(defaultIv).into(imageView);
//    }
//
//    public static void glide2Iv(Context ctx, String imageUrl, ImageView imageView, int defaultIv, int errIv) {
//        Glide.with(ctx).load(imageUrl).centerCrop().placeholder(defaultIv).error(errIv).into(imageView);
//    }
//
//
    public static void uri2Iv(Context ctx, Uri imageUri, ImageView imageView) {
        Glide.with(ctx).load(imageUri).centerCrop().into(imageView);
    }
//
//    public static void glide2IvBitmap(Context ctx, Bitmap imageUri, ImageView imageView) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        imageUri.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] bytes = baos.toByteArray();
//        Glide.with(ctx).load(bytes).centerCrop().into(imageView);
//    }
}
