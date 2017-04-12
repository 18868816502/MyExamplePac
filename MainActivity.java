package com.jonny.myexamplepac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jonny.myexamplepac.Adapter.BezierViewPager;
//import com.crashlytics.android.Crashlytics;
//import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_listViewPaging, bt_imageSwitcher, bt_ExpandableListView, bt_contextMenus, bt_popupWindow, bt_notifications, bt_anim, bt_phoneAdd, bt_webView, bt_intPic, bt_service,
            bt_fragment, bt_volley, bt_telephonyManager, bt_shareContent, bt_jsonAnalysis, bt_gamePlayerManager, bt_bitmapSample, bt_media, bt_utils, bt_wave, bt_camera, bt_share, bt_diyCurveGraph,bt_bezierViewPager;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Fabric.with(this, new Crashlytics());
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        bt_listViewPaging = (Button) findViewById(R.id.bt_listViewPaging);
        bt_listViewPaging.setOnClickListener(this);
        bt_imageSwitcher = (Button) findViewById(R.id.bt_imageSwitcher);
        bt_imageSwitcher.setOnClickListener(this);
        bt_ExpandableListView = (Button) findViewById(R.id.bt_expandableListView);
        bt_ExpandableListView.setOnClickListener(this);
        bt_contextMenus = (Button) findViewById(R.id.bt_contextMenus);
        bt_contextMenus.setOnClickListener(this);
        bt_popupWindow = (Button) findViewById(R.id.bt_popupWindow);
        bt_popupWindow.setOnClickListener(this);
        bt_notifications = (Button) findViewById(R.id.bt_notifications);
        bt_notifications.setOnClickListener(this);
        bt_anim = (Button) findViewById(R.id.bt_anim);
        bt_anim.setOnClickListener(this);
        bt_phoneAdd = (Button) findViewById(R.id.bt_phoneAdd);
        bt_phoneAdd.setOnClickListener(this);
        bt_webView = (Button) findViewById(R.id.bt_webView);
        bt_webView.setOnClickListener(this);
        bt_intPic = (Button) findViewById(R.id.bt_intPic);
        bt_intPic.setOnClickListener(this);
        bt_service = (Button) findViewById(R.id.bt_service);
        bt_service.setOnClickListener(this);
        bt_fragment = (Button) findViewById(R.id.bt_fragment);
        bt_fragment.setOnClickListener(this);
        bt_volley = (Button) findViewById(R.id.bt_volley);
        bt_volley.setOnClickListener(this);
        bt_telephonyManager = (Button) findViewById(R.id.bt_telephonyManager);
        bt_telephonyManager.setOnClickListener(this);
        bt_shareContent = (Button) findViewById(R.id.bt_shareContent);
        bt_shareContent.setOnClickListener(this);
        bt_jsonAnalysis = (Button) findViewById(R.id.bt_jsonAnalysis);
        bt_jsonAnalysis.setOnClickListener(this);
        bt_gamePlayerManager = (Button) findViewById(R.id.bt_gamePlayerManager);
        bt_gamePlayerManager.setOnClickListener(this);
        bt_bitmapSample = (Button) findViewById(R.id.bt_bitmapSample);
        bt_bitmapSample.setOnClickListener(this);
        bt_media = (Button) findViewById(R.id.bt_media);
        bt_media.setOnClickListener(this);
        bt_utils = (Button) findViewById(R.id.bt_utils);
        bt_utils.setOnClickListener(this);
        bt_wave = (Button) findViewById(R.id.bt_wave);
        bt_wave.setOnClickListener(this);
        bt_wave.setVisibility(View.GONE);
        bt_camera = (Button) findViewById(R.id.bt_camera);
        bt_camera.setOnClickListener(this);
        bt_share = (Button) findViewById(R.id.bt_share);
        bt_share.setOnClickListener(this);
        bt_diyCurveGraph = (Button) findViewById(R.id.bt_diyCurveGraph);
        bt_diyCurveGraph.setOnClickListener(this);
        bt_bezierViewPager = (Button) findViewById(R.id.bt_bezierViewPager);
        bt_bezierViewPager.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_listViewPaging:
                intent = new Intent(this, ListViewPagingActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_imageSwitcher:
                intent = new Intent(this, ImageSwitcherActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_expandableListView:
                intent = new Intent(this, ExpandableListViewActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_contextMenus:
                intent = new Intent(this, ContextMenusActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_popupWindow:
                View view = getLayoutInflater().inflate(R.layout.popupwindow_layout, null);
                //创建PopupWindow(窗体的视图,宽度,高度)
                final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.zoom_plate));//设置背景
                popupWindow.getBackground().setAlpha(200);//设置透明度
                popupWindow.setOutsideTouchable(true);//点击视图外部隐藏视图
                popupWindow.setFocusable(true);//获得焦点
                popupWindow.setTouchable(true);//可点击
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);//软键盘弹出时不覆盖
                popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);//设置动画效果
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);//设置显示的位置
                Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
                bt_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "您点击了OK", Toast.LENGTH_SHORT).show();
                        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);//设置动画效果
                    }
                });
                Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
                bt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.bt_notifications:
                intent = new Intent(this, NotificationsActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_anim:
                intent = new Intent(this, AnimActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_phoneAdd:
                intent = new Intent(this, PhoneAddActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_webView:
                intent = new Intent(this, WebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_intPic:
                intent = new Intent(this, IntPicActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_service:
                intent = new Intent(this, ServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_fragment:
                intent = new Intent(this, FragmentActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_volley:
                intent = new Intent(this, VolleyActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_telephonyManager:
                intent = new Intent(this, TelephonyManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_shareContent:
                intent = new Intent(this, ShareContentActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_jsonAnalysis:
                intent = new Intent(this, JsonAnalysisActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_gamePlayerManager:
                intent = new Intent(this, GamePlayersManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_bitmapSample:
                intent = new Intent(this, BitmapSampleActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_media:
                intent = new Intent(this, MediaActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_utils:
                intent = new Intent(this, UtilsActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_wave:
                intent = new Intent(this, WaveActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_camera:
                intent = new Intent(this, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_share:
                intent = new Intent(this, ShareSdkActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_diyCurveGraph:
                intent = new Intent(this, DiyCurveGraphActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_bezierViewPager:
                intent = new Intent(this, BezierViewPagerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
