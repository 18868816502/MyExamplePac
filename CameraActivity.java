package com.jonny.myexamplepac;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import org.apache.http.Consts;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.CharsetUtils;
//import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, MediaRecorder.OnErrorListener {
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int MEDIA_TYPE_VIDEO = 2;
    private static File file = null;
    private ImageView iv;
    private TextView tv;
    private MediaRecorder mr;//录音器
    private boolean prepared = false;//记录录音器是否已经准备
    private Button bt_start, bt_stop;
    private int size=1;//图片压缩的比例
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        iv = (ImageView) findViewById(R.id.iv);
        tv = (TextView) findViewById(R.id.tv);

//        init();

    }

    private void init() {

        bt_start = (Button) findViewById(R.id.bt_start);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        bt_stop.setEnabled(false);
        mr = new MediaRecorder();
 //       mr.reset();
        mr.setAudioSource(MediaRecorder.AudioSource.MIC);//设置音源
        mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//输出源格式
        mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//音频编码
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + File.separator + System.currentTimeMillis() + ".mp3";
        System.out.println(path);
        mr.setOutputFile(path);
        try {
            mr.prepare();
            prepared=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照
     *
     * @param view
     */
    public void takePhotoClick(View view) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri(MEDIA_TYPE_IMAGE));
        startActivityForResult(intent, MEDIA_TYPE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (MEDIA_TYPE_IMAGE == requestCode) {
//                    System.out.println(file);
//                    Uri uri = Uri.fromFile(file);
//                    iv.setImageURI(uri);
                    getBitmap(size);
                } else if (MEDIA_TYPE_VIDEO == requestCode) {

                }
        }
    }

    private Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
        String rootPath = null;
        switch (type) {
            case MEDIA_TYPE_IMAGE:
                rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();

                file = new File(rootPath + File.separator + System.currentTimeMillis() + ".jpg");
                break;
            case MEDIA_TYPE_VIDEO:
                rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath();
                file = new File(rootPath + File.separator + System.currentTimeMillis() + ".mp4");
                break;
        }

        return file;
    }

    /**
     * 优化拍摄得到的图片并显示
     */
    private void getBitmap(int size) {
        FileInputStream f = null;
        try {
            f = new FileInputStream(file.getPath());
            System.out.println("rootPath:"+file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;//图片的长宽都是原来的1/8
        BufferedInputStream bis = new BufferedInputStream(f);
        bm = BitmapFactory.decodeStream(bis, null, options);
        if(bm.getByteCount()>1024*1024*3){
            size=size+1;
            System.out.println("bm.getByteCount()" + bm.getByteCount());
            System.out.println("size" + size);
            getBitmap(size);
        } else {
            System.out.println(bitmapToFile(bm, file.getPath()));
//            requestJson(file.getPath());
            iv.setImageBitmap(bm);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                if (prepared) {
                    mr.start();
                    prepared = false;
                    bt_start.setEnabled(false);
                    bt_stop.setEnabled(true);
                }
                break;
            case R.id.bt_stop:
                mr.stop();
                bt_start.setEnabled(true);
                bt_stop.setEnabled(false);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mr != null) {
            mr.release();
        }
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        mr.reset();
    }

    public boolean bitmapToFile(Bitmap bitmap, String filename){
        Bitmap.CompressFormat format= Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap.compress(format, quality, stream);
    }

//    public static void requestJson(String path) {
////		GsonBean gsonBean;
////		Gson gson = new Gson();
////		String json = "{\"account\":5000000,\"account_wait\":4998000,\"account_yes\":2000,\"addtime\":\"1456040607\",\"apr\":10,\"bid_no\":\"160505154327\"}";
////		gsonBean = (GsonBean) gson.fromJson(json.toString(), GsonBean.class);
////		System.out.println(gsonBean.toString());
//        System.out.println(path);
//        File file = new File(path);
//        String key  = "WA9zjjEjbMbKae5uP13htu";
//        String secret = "eb874500332a44959df1d487bf7875d5";
//        String typeId = "17";
//        String format = "json";
//        String url = "http://netocr.com/api/recog.do";
//        String result = doPost(url, file, key, secret, typeId, format);
//        System.out.println(result);
//    }
//
//    public static String doPost(String url, File file, String key,String secret, String typeId, String format) {
//        String result = "";
//        try {
//            CloseableHttpClient client = HttpClients.createDefault(); 										// 1.创建httpclient对象
//            HttpPost post = new HttpPost(url); 																// 2.通过url创建post方法
//
//            if("json".equalsIgnoreCase(format)){
//                post.setHeader("accept", "application/json");
//            }else if("xml".equalsIgnoreCase(format)||"".equalsIgnoreCase(format)) {
//                post.setHeader("accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            }
//
//            //***************************************<向post方法中封装实体>************************************//3.向post方法中封装实体
//				/* post方式实现文件上传则需要使用multipart/form-data类型表单，httpclient4.3以后需要使用MultipartEntityBuilder来封装
//				 * 对应的html页面表单：
//					 <form name="input" action="http://netocr.com/api/recog.do" method="post" enctype="multipart/form-data">
//				        	请选择要上传的文件<input  type="file" NAME="file"><br />
//							key:<input type="text" name="key" value="W8Nh5AU2xsTYzaduwkzEuc" />	<br />
//							secret:<input type="text" name="secret" value="9646d012210a4ba48b3ba16737d6f69f" /><br />
//							typeId:<input type="text" name="typeId" value="2"/><br />
//							format:<input type="text" name="format" value=""/><br />
//							<input type="submit" value="提交">
//					</form>
//				 */
//
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();									//实例化实体构造器
//            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);												//设置浏览器兼容模式
//
//            builder.addPart("file", new FileBody(file));														//添加"file"字段及其值；此处注意字段名称必须是"file"
//            builder.addPart("key", new StringBody(key, ContentType.create("text/plain", Consts.UTF_8)));		//添加"key"字段及其值
//            builder.addPart("secret", new StringBody(secret, ContentType.create("text/plain", Consts.UTF_8)));	//添加"secret"字段及其值
//            builder.addPart("typeId", new StringBody(typeId, ContentType.create("text/plain", Consts.UTF_8)));	//添加"typeId"字段及其值
//            builder.addPart("format", new StringBody(format, ContentType.create("text/plain", Consts.UTF_8)));	//添加"format"字段及其值
//
//            HttpEntity reqEntity = builder.setCharset(CharsetUtils.get("UTF-8")).build();						//设置请求的编码格式，并构造实体
//
//
//            post.setEntity(reqEntity);
//            //**************************************</向post方法中封装实体>************************************
//
//            CloseableHttpResponse response = client.execute(post);												 // 4.执行post方法，返回HttpResponse的对象
//            if (response.getStatusLine().getStatusCode() == 200) {		// 5.如果返回结果状态码为200，则读取响应实体response对象的实体内容，并封装成String对象返回
//                result = EntityUtils.toString(response.getEntity(), "UTF-8");
//            } else {
//                System.out.println("服务器返回异常");
//            }
//
//            try {
//                HttpEntity e = response.getEntity();					 // 6.关闭资源
//                if (e != null) {
//                    InputStream instream = e.getContent();
//                    instream.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                response.close();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }
//
//        return result;														//7.返回识别结果
//
//    }
}
