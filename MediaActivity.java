package com.jonny.myexamplepac;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MediaActivity extends AppCompatActivity {
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

    }

    /**
     * 播放本地资源文件
     */
    public void playFromResClick(View view){
        mp = MediaPlayer.create(this,R.raw.pure1);
        mp.start();
    }
    /**
     * 播放系统文件
     */
    public void playFromSysClick(View view){
        mp = new MediaPlayer();
//        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/xpg.mp3";
        String path = "/storage/emulated/0/tencent/QQfile_recv/xpg.mp3";
        try {
            mp.setDataSource(this, Uri.parse(path));//设置数据源
            mp.prepare();//同步执行
            mp.start();
        } catch (IOException e) {
            Toast.makeText(this,"找不到资源文件",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 播放网络资源文件
     */
    public void playFromIntClick(View view){
        mp = new MediaPlayer();
        String path = "http://rm.sina.com.cn/wm/VZ200807251252065284VK/music/MUSIC0807251312558840.mp3/music/MUSIC2%EF%BC%89.mp3";
        try {
            mp.setDataSource(this, Uri.parse(path));
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mp.prepareAsync();//异步缓冲,缓冲和播放可以同时进行
        } catch (IOException e) {
            Toast.makeText(this,"找不到资源文件",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    /**
     * 播放网络资源文件
     */
    public void stopPlayClick(View view){
        mp.stop();
    }

    /**
     * 播放网络资源文件
     */
    public void jumpClick(View view){
        Intent intent = new Intent(MediaActivity.this,DiyMusicActivity.class);
        startActivity(intent);

    }

}
