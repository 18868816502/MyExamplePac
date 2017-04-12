package com.jonny.myexamplepac;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.jonny.myexamplepac.tools.LruCacheUtils;

import java.io.InputStream;

public class BitmapSampleActivity extends AppCompatActivity {
    private ImageView iv;
    private LruCache<String, Bitmap> lruCache;
    private LruCacheUtils lruCacheUtils;
    private static final String DISK_CACHE_SUBDIR = "temp";
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10;//10MB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_sample);
        iv = (ImageView) findViewById(R.id.iv);
        //获取当前activity内存大小
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        //设置缓存空间大小  一般为1/8的内存大小
        final int cacheSize = memoryClass / 8 * 1024 * 1024;
        lruCache = new LruCache<>(cacheSize);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lruCacheUtils = LruCacheUtils.getInstance();
        lruCacheUtils.open(this, DISK_CACHE_SUBDIR, DISK_CACHE_SIZE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lruCacheUtils.flush();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lruCacheUtils.close();
    }

    /**
     * 添加bitmap到缓存中
     *
     * @param key
     * @param bitmap
     */
    public void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            lruCache.put(key, bitmap);
        }
    }

    /**
     * 从缓存中获取bitmap
     */
    public Bitmap getBitmapFromCache(String key) {
        return lruCache.get(key);
    }

    public void showClick(View view) {
        String key = String.valueOf(R.mipmap.energy1);
        Bitmap bitmap = getBitmapFromCache(key);
        //缓存中有图则从缓存中去取
        if (bitmap == null) {
            bitmap = decodeSampleBitmapFromResource(getResources(), R.mipmap.energy1, 200, 200);
            addBitmapToCache(key, bitmap);
        } else {
            System.out.println("lruCache中有位图");
        }
        iv.setImageBitmap(bitmap);
    }

    public void show2Click(View view) {
        String url = "http://pic1.nipic.com/2008-10-14/2008101410204561_2.jpg";
        String url1 = "http://img7.ph.126.net/qIsiDP3eztmLe5355_pV1A==/41939771546958267.jpg";
        loadBitmap(url1,iv);
    }

    /**
     * 位图重新采样
     *
     * @param res       位图资源
     * @param resId     位图ID
     * @param reqWidth  希望得到的宽度
     * @param reqHeight 希望得到的高度
     * @return
     */
    public Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析边界，不加载到内存
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//        System.out.println( calculateInSampleSize(options,reqWidth,reqHeight));
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    /**
     * 计算位图的采样比例
     *
     * @return 位图的采样比例值
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //获取位图的原宽高
        int w = options.outWidth;
        int h = options.outHeight;
        int inSampleSize = 1;
        if (w > reqWidth || h > reqHeight) {
            if (w > h) {
                inSampleSize = Math.round((float) h / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) w / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    /**
     * 加载图片
     *
     * @param url
     * @param imageView
     */
    public void loadBitmap(String url, final ImageView imageView) {
        Bitmap bitmap = lruCacheUtils.getBitmapFromCache(url);
        if (bitmap == null) {
            InputStream in = lruCacheUtils.getDiskCache(url);
            if (in == null) {
                lruCacheUtils.putCache(url, new LruCacheUtils.CallBack<Bitmap>() {
                    @Override
                    public void response(Bitmap entity) {
                        System.out.println("http load");
                        imageView.setImageBitmap(entity);
                    }
                });
            } else {
                System.out.println("disk cache");
                bitmap = BitmapFactory.decodeStream(in);
                lruCacheUtils.addBitmapToCache(url, bitmap);
                imageView.setImageBitmap(bitmap);
            }
        } else {
            System.out.println("memory cache");
            imageView.setImageBitmap(bitmap);
        }
    }
}
