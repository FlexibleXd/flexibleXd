package com.xd.flexible.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.widget.Toast
import com.xd.flexible.R
import com.xd.flexible.application.BaseActivity
import butterknife.BindView
import butterknife.ButterKnife

class MainActivity : BaseActivity() {
//    @BindView(R.id.btn_ok)
//    internal var btnOk: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
//        btnOk!!.setOnClickListener { checkSelfPermission() }
    }


    /**
     * 检查权限
     */
    internal fun checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, PERMISSION_CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(PERMISSION_CAMERA),
                    PERMISSIONS_REQUEST_CAMERA)
        } else {
            startWallpaper()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startWallpaper()
                } else {
                    Toast.makeText(this, "zzzzzzzz", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    /**
     * 选择壁纸
     */
    internal fun startWallpaper() {
        val pickWallpaper = Intent(Intent.ACTION_SET_WALLPAPER)
        val chooser = Intent.createChooser(pickWallpaper, "zzz")
        startActivity(chooser)
    }

    /**
     * 不需要手动启动服务
     */
    internal fun setTransparentWallpaper() {
        startService(Intent(this@MainActivity, CameraLiveWallpaper::class.java))
    }

    companion object {
        private val PERMISSIONS_REQUEST_CAMERA = 454
        internal val PERMISSION_CAMERA = Manifest.permission.CAMERA
    }
}
