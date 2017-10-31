package com.xd.flexible.zxing;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.xd.flexible.R;
import com.xd.flexible.zxing.camera.CameraManager;
import com.xd.flexible.zxing.decoding.CaptureActivityHandler;
import com.xd.flexible.zxing.decoding.DecodeFormatManager;
import com.xd.flexible.zxing.decoding.InactivityTimer;
import com.xd.flexible.zxing.decoding.OnCaptureListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class CaptureActivity extends Activity implements Callback, OnCaptureListener {
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private int x = 0;
    private int y = 0;
    private int cropWidth = 0;
    private int cropHeight = 0;
    private RelativeLayout capture_container;
    private RelativeLayout capture_crop_layout;
    private static final long VIBRATE_DURATION = 200L;
    boolean flag = true;
    private RelativeLayout rl_zbar_light;
    private RelativeLayout rl_zbar_album;
    private Handler handlerLocal;
    private final static int IMAGE_CODE = 1;
    /**
     * 从bitmap解码
     */
    private final int DECODE_IMG = 0x138;
    /**
     * 从图片路径解码
     */
    private final int DECODE_PATH = 0x139;
    private RelativeLayout rl_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_scan);
        CameraManager.init(getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        capture_container = (RelativeLayout) findViewById(R.id.capture_container);
        capture_crop_layout = (RelativeLayout) findViewById(R.id.capture_crop_layout);
        ImageView capture_scan_line = (ImageView) findViewById(R.id.capture_scan_line);
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(3000);
        capture_scan_line.startAnimation(animation);
        rl_zbar_light = (RelativeLayout) findViewById(R.id.rl_zbar_light);
        rl_zbar_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                light();
            }
        });
        handlerLocal = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DECODE_IMG:
                        new DecodeBitmapTask().execute((Bitmap) msg.obj);
                        break;
                    case DECODE_PATH:
                        new DecodeBitmapTask().execute(BitmapFactory.decodeFile((String) msg.obj));
                        break;
                    default:
                        break;
                }
            }
        };
        rl_zbar_album = (RelativeLayout) findViewById(R.id.rl_zbar_album);
        rl_zbar_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//                intent.setType("image/*");
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // 调用android的图库
                startActivityForResult(intent, IMAGE_CODE);
            }
        });
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void light() {
        if (flag == true) {
            flag = false;
            // 开闪光灯
            CameraManager.get().openLight();
        } else {
            flag = true;
            // 关闪光灯
            CameraManager.get().offLight();
        }
    }

    private class DecodeBitmapTask extends AsyncTask<Bitmap, Void, String> {

        @Override
        protected String doInBackground(Bitmap... params) {
            Bitmap b = params[0];
            return decodeBitmap(b);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            final Intent intentImg = new Intent();
            intentImg.putExtra("RESULT", result);
            setResult(RESULT_OK, intentImg);
//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public String decodeBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return "null";
        }
        Hashtable<DecodeHintType, Object> hints = null;
        initHints(hints, null, "UTF8");//注意不要写成UTF-8，因为它里面自己包装的是UTF8
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);
        LuminanceSource source = new BitmapLuminance(bitmap);
        BinaryBitmap bit = new BinaryBitmap(new HybridBinarizer(source));
        try {
            return multiFormatReader.decodeWithState(bit).getText();
        } catch (ReaderException re) {
            // continue
        } finally {
            multiFormatReader.reset();
        }
        return null;
    }

    public void initHints(Hashtable<DecodeHintType, Object> hints, Vector<BarcodeFormat> decodeFormats, String CODE_STYLE) {
        hints = new Hashtable<DecodeHintType, Object>(2);
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            decodeFormats = new Vector<BarcodeFormat>();
            decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        if (CODE_STYLE != null) {
            // 下面这句不加编码方式更方便，让它自己查找。当然效率可能要低一些，但适用性更广
            // hints.put(DecodeHintType.CHARACTER_SET, CODE_STYLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case IMAGE_CODE:
                Uri uri = null;
                try {
                    uri = data.getData();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ContentResolver cr = this.getContentResolver();
                Bitmap bm = data.getParcelableExtra("data");
                if (bm != null) { // 如果不释放的话，不断取图片，将会内存不够
                    bm.recycle();
                }
                try {
                    bm = BitmapFactory.decodeStream(cr.openInputStream(uri));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                handlerLocal.sendMessage(handlerLocal.obtainMessage(DECODE_IMG, bm));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
        if (handlerLocal != null) {
            handlerLocal.removeCallbacksAndMessages(null);
            handlerLocal = null;
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            Point point = CameraManager.get().getCameraResolution();
            int width = point.y;
            int height = point.x;
            int x = capture_crop_layout.getLeft() * width / capture_container.getWidth();
            int y = capture_crop_layout.getTop() * height / capture_container.getHeight();
            int cropWidth = capture_crop_layout.getWidth() * width / capture_container.getWidth();
            int cropHeight = capture_crop_layout.getHeight() * height / capture_container.getHeight();
            setX(x);
            setY(y);
            setCropWidth(cropWidth);
            setCropHeight(cropHeight);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException re) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, null, null);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);
            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    private final OnCompletionListener beepListener = new OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();

        final Intent intentImg = new Intent();
        intentImg.putExtra("RESULT", result.getText());
        setResult(RESULT_OK, intentImg);
//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        finish();
        // 连续扫描，不发送此消息扫描一次结束后就不能再次扫描
//		handler.sendEmptyMessage(R.id.restart_preview);
    }

    @Override
    public void onSetResult(int resultCode, Intent data) {
        setResult(resultCode, data);
    }

    @Override
    public void onStartActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onFinish() {
        finish();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }
}