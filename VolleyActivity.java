package com.jonny.myexamplepac;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyActivity extends AppCompatActivity {
    RequestQueue queue = null;//请求队列
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        queue = Volley.newRequestQueue(this);
        iv= (ImageView) findViewById(R.id.iv);
    }

    /**
     * 发送一个字符串请求
     */
    public void sendStringRequestClick(View view) {
        String url = "http://www.baidu.com";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();


            }
        });
        queue.add(request);
    }


    /**
     * 发送一个带参数的字符串请求
     */
    public void sendParamsPostStringRequestClick(View view) {
        String url = "http://www.ydcylc.com/mobile/app_checkUserName";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("phoneNum","18868816502");
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * 发送一个JsonRequest请求,访问到的是json数据,如对象部位json数据，则会报错
     */
    public void sendJsonRequestClick(View view) {
         String url = "http://weather.123.duba.net/static/weather_info/101010100.html";
        //请求的参数封装为JSONObject
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("name","jonny");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        //参数（请求方式,url,请求的参数,相应成功的回调接口,错误的回调接口）
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    /**
     * 发送一个ImageRequest请求
     */
    public void sendImageRequestClick(View view) {
        String url = "http://b.hiphotos.baidu.com/image/pic/item/08f790529822720ea5d058ba7ccb0a46f21fab50.jpg";
        //参数(url,图片请求成功的回调,允许图片最大的宽度,允许图片最大的高度,指定图片的颜色属性,请求失败的回调)
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                iv.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    /**
     * 使用ImageLoader加载图片
     */
    public void imageLoaderClick(View view) {
        String url = "http://c.hiphotos.baidu.com/image/pic/item/6a600c338744ebf8a05ade3bdbf9d72a6059a78f.jpg";
        ImageLoader imageLoader = new ImageLoader(queue,new BitmapCache());
        //获取图片监听器
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, R.mipmap.icon1, R.mipmap.ic_launcher);
        imageLoader.get(url,listener,1200,1200);
    }

    /**
     * 设置图片缓存
     */
    private class BitmapCache implements ImageLoader.ImageCache{
        private LruCache<String,Bitmap> cache;
        private int maxCache = 10*1024*1024;//图片的最大缓存
        public BitmapCache(){
            cache = new LruCache<>(maxCache);
        }
        @Override
        public Bitmap getBitmap(String s) {
            return cache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            cache.put(s,bitmap);
        }
    }


    /**
     * NetworkImage加载图片
     */
    public void NetworkImageClick(View view) {
        String url = "http://f.hiphotos.baidu.com/image/pic/item/00e93901213fb80e0ee553d034d12f2eb9389484.jpg";
        NetworkImageView networkimageview = (NetworkImageView) findViewById(R.id.networkimageview);
        networkimageview.setDefaultImageResId(R.mipmap.icon1);
        networkimageview.setErrorImageResId(R.mipmap.icon1);
        networkimageview.setImageUrl(url,new ImageLoader(queue,new BitmapCache()));
    }
}
