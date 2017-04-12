package com.jonny.myexamplepac;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

public class IntPicActivity extends Activity {
    private final Handler handler=new MyHandler(this);
    private static ImageView iv;
    public static final int LOAD_SUCESS =0x1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_int_pic);
        iv= (ImageView) findViewById(R.id.imageView);
        showNetImage();
    }

    private static class MyHandler extends Handler{

        private final WeakReference<IntPicActivity> weakReference;
        public MyHandler(IntPicActivity intPicActivity){
            weakReference=new WeakReference<IntPicActivity>(intPicActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            IntPicActivity intPicActivity = weakReference.get();
            if(intPicActivity!=null){
                switch (msg.what){
                    case LOAD_SUCESS:
                        iv.setImageBitmap((Bitmap) msg.obj);
                        break;
                }
            }
        }
    }

    public void showNetImage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url =new URL("http://img5.duitang.com/uploads/item/201305/20/20130520193254_P4BFa.thumb.224_0.jpeg");
                    InputStream in = url.openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    Message msg = handler.obtainMessage(LOAD_SUCESS,bitmap);
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
