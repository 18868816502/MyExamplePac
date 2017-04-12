package com.jonny.myexamplepac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class ImageSwitcherActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory, View.OnTouchListener {
    private ImageSwitcher imageView;
    private int[] images = {R.mipmap.beauty1, R.mipmap.beauty2, R.mipmap.beauty3, R.mipmap.beauty4, R.mipmap.beauty5, R.mipmap.beauty6, R.mipmap.beauty7};
    private int index = 0;//图片下标
    float startX = 0f;//触摸初始X坐标
    float endX = 0f;//触摸结束X坐标


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switcher);
        imageView = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageView.setFactory(this);
        imageView.setOnTouchListener(this);
    }

    @Override
    public View makeView() {
        ImageView iv = new ImageView(this);
        iv.setImageResource(images[0]);
        return iv;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();//获取当前事件的动作
        if (action == MotionEvent.ACTION_DOWN) {
            startX = event.getX();
            return true;
        }
        if (action == MotionEvent.ACTION_UP) {
            endX = event.getX();
            if (startX - endX >= 100) {
                index = index - 1 > 0 ? --index : images.length - 1;
                imageView.setInAnimation(this, R.anim.slide_in_right);
                imageView.setOutAnimation(this, R.anim.slide_out_left);
            }
            if (endX - startX >= 100) {
                index = index + 1 < images.length ? ++index : 0;
                imageView.setInAnimation(this,android.R.anim.slide_in_left);
                imageView.setOutAnimation(this,android.R.anim.slide_out_right);
            }

                imageView.setImageResource(images[index]);

        }

        return true;
    }
}
