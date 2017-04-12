package com.jonny.myexamplepac;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelephonyManagerActivity extends AppCompatActivity {
    private TextView tv_info;
    private Button bt_telephone_info;
    private String telInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephony_manager);
        sendBroadcast(new Intent(this,PhoneListenerReceiver.class));
        tv_info = (TextView) findViewById(R.id.tv_info);
        bt_telephone_info = (Button) findViewById(R.id.bt_telephone_info);
        bt_telephone_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tv_info.setText();
                tv_info.setText(testTelephonyManager());
            }
        });
    }

    /**
     * 电话服务管理器的API方法
     */
    private String testTelephonyManager() {
        ;
        ;
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telInfo = "电话状态 : " + tm.getCallState() + "\n唯一的设备ID ： " + tm.getDeviceId()
                + "\n设备的软件版本号 : " + tm.getDeviceSoftwareVersion() + "\n手机号 :　" + tm.getLine1Number()+"\n手机型号:"+android.os.Build.MODEL
                + "\n获取ISO彼岸准的国家码,即国家长途区号 :　" + tm.getNetworkCountryIso() + "\n当前使用的网络类型 : "
                + tm.getNetworkType() + "\n手机类型 : " + tm.getPhoneType() + "\nSIM的状态信息 : " + tm.getSimState()
                + "\n唯一的用户ID ： " + tm.getSubscriberId() + "\nSIM卡的序列号 : " + tm.getSimSerialNumber()
                + "\n服务器名称 : " + tm.getSimOperatorName();
        return telInfo;
    }
}
