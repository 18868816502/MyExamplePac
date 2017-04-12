package com.jonny.myexamplepac;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class NotificationsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_normal, bt_big, bt_progress, bt_diy;
    private static final int NID_1 = 0x1;//普通notifications的标识符
    private static final int NID_2 = 0x2;//BigNotifications的标识符
    private static final int NID_3 = 0x3;//ProgressNotifications的标识符
    private static final int NID_5 = 0x4;//自定义notifications的标识符
    private Button bt_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        bt_normal = (Button) findViewById(R.id.bt_normal);
        bt_normal.setOnClickListener(this);
        bt_big = (Button) findViewById(R.id.bt_big);
        bt_big.setOnClickListener(this);
        bt_progress = (Button) findViewById(R.id.bt_progress);
        bt_progress.setOnClickListener(this);
        bt_diy = (Button) findViewById(R.id.bt_diy);
        bt_diy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final NotificationCompat.Builder builder;
        NotificationManager nm;
        Notification n;
        switch (v.getId()) {
            case R.id.bt_normal:
                builder = new NotificationCompat.Builder(this);
                //设置相关的属性
                builder.setSmallIcon(R.mipmap.icon1);//设置右下角小图标
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon1));//设置左边大图标(不起作用,目前还没找到原因)
                builder.setContentTitle("你有一条新消息");//设置文章标题
                builder.setContentText("尊敬的用户，您好，非常感谢您使用本公司的产品，我们会竭尽我们的全部为您提供最完善的服务！");//设置文章内容
                builder.setAutoCancel(true);//当点击内容时自动取消显示条
                //               builder.setNumber(5);//最多显示内容的行数
                builder.setTicker("新消息");//未打开抽屉栏显示的内容
                builder.setOngoing(false);//不设置为常驻通知（即不能按关闭键关闭通知内容）
                builder.setDefaults(Notification.DEFAULT_VIBRATE);//设置提示方式，Notification.DEFAULT_ALL为消息和振动全开

                //设置点击消息条跳转事件
                Intent intent = new Intent(NotificationsActivity.this, MainActivity.class);
                //参数：上下文、请求编码（没用）、意图、创建pendingIntent的方式
//                PendingIntent.FLAG_CANCEL_CURRENT; 取消当前的PI,创建新的
//                PendingIntent.FLAG_NO_CREATE;  如果有就使用,没有不创建
//                PendingIntent.FLAG_ONE_SHOT;   只使用一次
//                PendingIntent.FLAG_UPDATE_CURRENT;  如果有,更新Intent,没有就创建
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pi);
                //创建一个通知对象
                n = builder.build();

                //获取系统的通知管理器,然后发送通知
                nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(NID_1, n);
                break;
            case R.id.bt_big:
                builder = new NotificationCompat.Builder(this);
                builder.setSmallIcon(R.mipmap.beauty7);//设置小图标
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.beauty7));//设置左边大图标(不起作用,目前还没找到原因)
                builder.setContentTitle("新消息");//设置文章标题
                builder.setContentText("新消息");//设置文章内容
                //设置大视图样式
                NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
                style.setBigContentTitle("七步诗");
                style.addLine("煮豆燃豆箕,豆在釜中泣");
                style.addLine("本是同根生,相煎何太急");
                style.addLine("           作者：曹植");
                builder.setStyle(style);
                nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                n = builder.build();
                nm.notify(NID_2, n);
                break;
            case R.id.bt_progress:
                builder = new NotificationCompat.Builder(this);
                builder.setSmallIcon(R.mipmap.beauty7);//设置小图标
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.beauty7));//设置左边大图标(不起作用,目前还没找到原因)
                builder.setContentTitle("更新中...");//设置文章标题
                builder.setContentText("正在下载你所需要的文件，请稍等...");//设置文章内容
                builder.setProgress(100, 5, false);
                final NotificationManager nm1 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                n = builder.build();
                nm1.notify(NID_3, n);

                //模拟更新的线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int progress = 0; progress <= 100; progress += 5) {
                            builder.setProgress(100, progress, false);
                            nm1.notify(NID_3, builder.build());
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        builder.setProgress(0, 0, true);
                        builder.setContentText("更新完成，正在进行安装...");
                        nm1.notify(NID_3, builder.build());
                    }
                }).start();
                break;
            case R.id.bt_diy:

                builder = new NotificationCompat.Builder(this);
                builder.setSmallIcon(R.mipmap.beauty7);//设置小图标
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.diy_style_notifications);
                intent = new Intent(NotificationsActivity.this, MainActivity.class);
                views.setOnClickPendingIntent(R.id.bt_play,PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));//设置按钮的点击事件
                builder.setContent(views);
                builder.setTicker("播放器");
                nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                n = builder.build();
                nm.notify(NID_5, n);

//                View view = getLayoutInflater().inflate(R.layout.diy_style_notifications, null);
//                bt_ok = (Button) view.findViewById(R.id.bt_ok);
//                bt_ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        bt_ok.setText("暂停");
//                        if (bt_ok.getText().equals("播放")) {
//                            bt_ok.setText("暂停");
//                        } else {
//                            bt_ok.setText("播放");
//                        }
//                    }
//                });
                break;
        }
    }

}
