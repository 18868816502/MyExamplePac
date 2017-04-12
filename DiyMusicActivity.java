package com.jonny.myexamplepac;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;

public class DiyMusicActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private MediaPlayer mp;
    private int index = 0;//表示当前要播放音乐的索引
    private ArrayList<String> musicList = new ArrayList<>();
    private Button bt_play, bt_pause, bt_next, bt_prev;
    private Boolean isPause = false;//是否是暂停状态  true表示暂停

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_music);
        initView();
        initMusic();
        mp = new MediaPlayer();
        mp.setOnPreparedListener(this);
        mp.setOnErrorListener(this);
        mp.setOnCompletionListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        bt_play = (Button) findViewById(R.id.bt_play);
        bt_play.setOnClickListener(this);
        bt_pause = (Button) findViewById(R.id.bt_pause);
        bt_pause.setOnClickListener(this);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_next.setOnClickListener(this);
        bt_prev = (Button) findViewById(R.id.bt_prev);
        bt_prev.setOnClickListener(this);

    }

    /**
     * 添加音乐文件
     */
    private void initMusic() {
        String root = "/storage/emulated/0/MIUI/music/mp3_hd/";
        musicList.add(root + "cfjy.mp3");
        musicList.add(root + "jhym.mp3");
        musicList.add(root + "ljf.mp3");
        musicList.add(root + "pm.mp3");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
//                System.out.println("bt_play");
                play();
                break;
            case R.id.bt_pause:
//                System.out.println("bt_pause");
                pause();
                break;
            case R.id.bt_next:
//                System.out.println("bt_next");
                next();
                break;
            case R.id.bt_prev:
//                System.out.println("bt_next");
                prev();
                break;
            default:
                break;
        }
    }

    private void prev() {
        if (index - 1 > 0) {
            index--;
        } else {
            index = musicList.size() - 1;
        }
        start();
    }

    private void next() {
        if (index + 1 < musicList.size()) {
            index++;
        } else {
            index = 0;
        }
        start();
    }

    private void pause() {
        if (mp.isPlaying()) {
            mp.pause();
        }
            isPause = true;
    }

    private void play() {
        if (isPause) {
            mp.start();
        } else {
            start();
        }
        isPause = false;
    }

    /**
     * 重头开始播放音乐
     */
    private void start() {
        if (index < musicList.size()) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.reset();//重置
            String musicPath = musicList.get(index);
            try {
                mp.setDataSource(musicPath);
                mp.prepareAsync();
                isPause = false;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 准备完成后就开始播放
     *
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        next();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.release();//释放资源
        }
    }
}
