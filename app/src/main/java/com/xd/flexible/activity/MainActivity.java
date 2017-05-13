package com.xd.flexible.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.xd.flexible.R;
import com.xd.flexible.widget.LoadingDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadingDialog loadingDialog = new LoadingDialog(this, -1, -1);
        //zzzzzzzzz
        loadingDialog.show();
        loadingDialog.dismiss();
    }
}
