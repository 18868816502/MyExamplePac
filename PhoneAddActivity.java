package com.jonny.myexamplepac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * 获取手机号码归属地 WebServer需要导入ksoap.jar包
 */
public class PhoneAddActivity extends AppCompatActivity {
    private String nameSpace = "http://WebXml.com.cn/";
    private String methodName = "getMobileCodeInfo";
    private String endPoint = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx?wsdl";
    private String soapAction = "http://WebXml.com.cn/getMobileCodeInfo";
    private EditText et_phoneNumber;
    private TextView tv_address;
    private String phoneNumber="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_add);
        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        phoneNumber = et_phoneNumber.getText().toString();
        tv_address = (TextView) findViewById(R.id.tv_address);
    }

    public void getAdd(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //指定WebService的命名空间和调用的方法名
                SoapObject rpc = new SoapObject(nameSpace, methodName);
                //设置需调用WebService接口需要传入的两个参数mobileCode、userId
                rpc.addProperty("mobileCode", phoneNumber);
                rpc.addProperty("userId", "");
                //生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                envelope.bodyOut = rpc;
                envelope.dotNet = true;//设置是否调动的是dotNet开发的WebService
                HttpTransportSE transportSE = new HttpTransportSE(endPoint);
                try {
                    transportSE.call(soapAction, envelope);//调用WebService
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                //获取返回的数据
//                SoapObject object = (SoapObject) envelope.bodyIn;
//                //获取返回的结果
//                String result = object.getProperty(0).toString();
//                System.out.print(result);
                tv_address.setText("如果服务器出错，则会显示默认数据，浙江杭州");
            }
        });


    }
}
