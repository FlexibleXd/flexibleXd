package com.xd.flexible.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;


/**
 * Created by Flexible on 2017/9/2 0002.
 */

public class AvatarMananger {
    private String AVATAR_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "xd";
    public static final int AVATER_PIC = 1001;
    public static final int AVATER_ALBUM = 1002;
    public static final int AVATER_CROPE = 1003;
    private static AvatarMananger instance;
    private static Activity act;
    public static Uri lastUri;

    private AvatarMananger() {
        deleterFile();
        newDirectory();
    }


    private void newDirectory() {
        File file = new File(AVATAR_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static AvatarMananger newInstance(Activity activity) {
        act = activity;
        if (null == instance)
            instance = new AvatarMananger();
        return instance;
    }

    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        act.startActivityForResult(intent, AVATER_ALBUM);
    }

    public void takePic() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        lastUri = Uri.fromFile(new File(AVATAR_PATH + "/" + System.currentTimeMillis() + ".jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, lastUri);
        act.startActivityForResult(intent, AVATER_PIC);
    }


    public void cropePic(Uri uri, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra("return-data", false);
        lastUri = Uri.fromFile(new File(AVATAR_PATH + "/" + System.currentTimeMillis() + ".jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, lastUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        act.startActivityForResult(intent, AVATER_CROPE);
    }


    private void deleterFile() {
        FileUtils.deleteDir(AVATAR_PATH);
    }


    public static Uri getLastUri() {
        return lastUri;
    }
}
