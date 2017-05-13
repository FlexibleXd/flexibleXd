package com.xd.flexible.utils;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

/**
 * Created by Flexible on 2017/1/13 0013.
 */

public class DialogUtils {
    public static MaterialDialog.Builder ListDialog(Context context, String title, String... item) {
        MaterialDialog.Builder md = new MaterialDialog.Builder(context);
        return md.title(title).items(item);

    }

    public static MaterialDialog.Builder ListDialog(Context context, String title, List<String> item) {
        MaterialDialog.Builder md = new MaterialDialog.Builder(context);
        return md.title(title).items(item);

    }

    public static MaterialDialog.Builder ViewDialog(Context context, int id) {
        boolean wrapInScrollView = true;
        return new MaterialDialog.Builder(context)

                .customView(id, wrapInScrollView);
    }

    public static MaterialDialog.Builder ViewDialog(Context context, View id) {
        boolean wrapInScrollView = true;
        return new MaterialDialog.Builder(context)

                .customView(id, wrapInScrollView);
    }

    /**
     *
     * @param context
     * @param title
     * @param content
     * @param nagative 取消
     * @param positive 确定
     * @return
     */
    public static MaterialDialog.Builder SimpleDialog(Context context, String title, String content, String nagative, String positive) {
        return new MaterialDialog.Builder(context)
                .title(title).content(content).negativeText(nagative).positiveText(positive);
    }

    public static MaterialDialog.Builder NotCancleDialog(Context context, String title, String content) {
        return new MaterialDialog.Builder(context).progress(true, 0).content(content);
    }
}
