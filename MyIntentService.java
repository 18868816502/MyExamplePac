package com.jonny.myexamplepac;

import android.app.IntentService;
import android.content.Intent;

/**
 * IntentService
 * 1.内部有一个工作线程来完成耗时操作,只需要实现onHandleIntent方法即可
 * 2.完成工作后会自动停止服务
 * 3.如果同时执行多个任务时,会以工作队列的方式,依次执行
 * 4.通过使用该类来完成APP中的耗时工作
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println(intent.getStringExtra("info"));
        for (int i=0;i<50;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("onHandleIntent-"+i+Thread.currentThread().getName());
        }
    }

}
