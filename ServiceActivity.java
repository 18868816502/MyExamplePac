package com.jonny.myexamplepac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ServiceActivity extends AppCompatActivity {
   Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void startServiceOnclick(View view){
        intent = new Intent(this,MyService.class);
        intent.putExtra("info","一个Service服务马上就要启动");
        startService(intent);
    }

    public void stopServiceOnclick(View view){
        intent = new Intent(this,MyService.class);
        if(intent!=null) {
            stopService(intent);
        }
    }

    public void startIntentServiceOnclick(View view){
        intent = new Intent(this,MyIntentService.class);
        intent.putExtra("info","一个IntentService服务马上就要启动");
        startService(intent);
    }

    public void stopIntentServiceOnclick(View view){
        intent = new Intent(this,MyIntentService.class);
        stopService(intent);
    }
}
