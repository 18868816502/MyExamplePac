package com.jonny.myexamplepac;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("my service create");
    }

    //在该方法中实现服务的核心业务
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println(intent.getStringExtra("info"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<50;i++){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("onStartCommand-"+i);
                    if(i==30){
                        MyService.this.stopSelf();
                        break;
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("my service destroy");
    }
}
