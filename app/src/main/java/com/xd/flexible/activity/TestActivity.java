package com.xd.flexible.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xd.flexible.R;
import com.xd.flexible.utils.AvatarMananger;
import com.xd.flexible.widget.BannerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yolanda.nohttp.NoHttp.getContext;


/**
 * Created by Flexible on 2017/9/2 0002.
 */

public class TestActivity extends Activity {
    @BindView(R.id.iv_show)
    ImageView ivShow;
    @BindView(R.id.album)
    Button album;
    @BindView(R.id.pic)
    Button pic;
    @BindView(R.id.banner)
    BannerView banner;
    private int[] resouce = new int[]{R.mipmap.banner1, R.mipmap.banner2, R.mipmap.no_net};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        for (int i = 0; i < resouce.length; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(resouce[i]);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            banner.addView(iv);
        }
    }

    @OnClick({R.id.album, R.id.pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.album:
                AvatarMananger.newInstance(TestActivity.this).openAlbum();
                break;
            case R.id.pic:
                AvatarMananger.newInstance(TestActivity.this).takePic();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case AvatarMananger.AVATER_ALBUM:
//                    ivShow.setImageURI(data.getData());
                    AvatarMananger.newInstance(TestActivity.this).cropePic(data.getData(), 200, 200);
                    break;
                case AvatarMananger.AVATER_PIC:
//                    ivShow.setImageURI(AvatarMananger.newInstance(TestActivity.this).getLastUri());
                    AvatarMananger.newInstance(TestActivity.this).cropePic(AvatarMananger.newInstance(TestActivity.this).getLastUri(), 200, 200);
                    break;
                case AvatarMananger.AVATER_CROPE:
                    ivShow.setImageURI(AvatarMananger.newInstance(TestActivity.this).getLastUri());
                    break;
            }
    }
}
