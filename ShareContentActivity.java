package com.jonny.myexamplepac;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShareContentActivity extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_context);
        tv= (TextView) findViewById(R.id.tv);
    }

    /**
     * 分享简单的文本内容
     */
    public void shareSimpleContentClick(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "How are you?");
        //指定选择器名称
        startActivity(Intent.createChooser(intent,"这是自定义选择器的名称"));
    }

    /**
     * 分享图片内容
     */
    public void shareImageContentClick(View view){
        Uri uri = Uri.parse("/storage/emulated/0/DCIM/Camera/1.jpg");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        //指定选择器名称
        startActivity(Intent.createChooser(intent, "这是自定义选择器的名称"));

    }


    /**
     * 通过一个AsyncTask来实现一个异步任务
     */
    public void asyncTaskClick(View view){
        new MyAsyncTask(this).execute();
    }

    private static class MyAsyncTask extends AsyncTask<String,Integer,String>{

        //通过该实例化方法可以调用到UI线程中的组件
        private ShareContentActivity activity;
        public MyAsyncTask(ShareContentActivity activity){
            this.activity=activity;
        }
        //执行任务之前触发的事件,可以在该方法中做一些初始化工作

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.tv.setText("下载进程开始...");
        }

        //执行后台任务的方法,类似于线程,所以不能在该方法中访问UI组件
        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 11; i++) {
                try {
                    Thread.sleep(1000);
                    //给onProgressUpdate方法传送进度值
                    publishProgress(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return "下载成功";
        }

        //更新进度值

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            activity.tv.setText("当前的值为: "+values[0]);
        }

        //当doInBackground方法返回后被调用
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity.tv.setText(s);
        }
    }
}
