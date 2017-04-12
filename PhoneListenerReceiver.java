package com.jonny.myexamplepac;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.TextView;

public class PhoneListenerReceiver extends BroadcastReceiver {
    public PhoneListenerReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取电话管理器对象,并注册监听
        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(new MyPhoneStateListener(context), PhoneStateListener.LISTEN_CALL_STATE);
        System.out.println("PhoneListenerReceiver started");
    }

    static WindowManager wm = null;

    private class MyPhoneStateListener extends PhoneStateListener {
        private Context context;
        private TextView textView = null;

        public MyPhoneStateListener(Context context) {
            this.context = context;
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            //响铃状态
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                //设置为一个浮动层
                params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
                //设置为不能触摸和没有焦点
                params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                textView = new TextView(context);
                textView.setText("当前来电号码为: " + incomingNumber);
                //添加浮动视图
                wm.addView(textView, params);
            } else if (wm != null) {//挂机状态
                wm.removeView(textView);
                wm = null;
            }
        }
    }
}
