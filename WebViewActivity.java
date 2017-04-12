package com.jonny.myexamplepac;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
    private WebView webView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_view);
        webView = (WebView) findViewById(R.id.webView);

        //加载自定义页面
//        webView.loadData("<html><title></title><body>hello world!</body></html>", "text/html", "utf-8");


        handler = new Handler();
        //获取设置对象
        WebSettings settings = webView.getSettings();
        //保存密码
        settings.setSavePassword(false);
        //保存表单数据
        settings.setSaveFormData(false);
        //是否支持JavaScript 默认为不支持
        settings.setJavaScriptEnabled(true);
        //是否支持缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        //获取焦点,如果不设置的话,会出现不能弹出软键盘等问题
        webView.requestFocus();
        //在组件内部显示滚动条内容
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //设置点击链接在当前webView中显示
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //处理标题,图标,弹窗等
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        webView.addJavascriptInterface(new MyObject(),"demo");
        //加载本地的文件
        webView.loadUrl("file:///android_asset/index.html");
        //加载指定网页
//        webView.loadUrl("http://baidu.com");
    }

    public class MyObject{
        @JavascriptInterface
        public void clickOnAndroid(){
            handler.post(new Runnable() {
                @Override
                public void run() {
                   webView.loadUrl("javascript:myfun()");
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
