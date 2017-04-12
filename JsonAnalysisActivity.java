package com.jonny.myexamplepac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jonny.myexamplepac.bean.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonAnalysisActivity extends AppCompatActivity {
    private TextView tv;
    private String info = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_analysis);
        tv = (TextView) findViewById(R.id.tv);
    }

    public void jsonReaderClick(View view) {
        ArrayList<Person> list = jsonReader();
        for (int i = 0; i < list.size(); i++) {
            info = info + list.get(i) + "\n";
        }
        tv.setText(info);
        info="";
    }

    /**
     * 用json生成JSON数组
     *
     * @param view
     */
    public void createJSONClick(View view) {
        ArrayList<Person> list = new ArrayList<>();
        Person p1 = new Person();
        p1.firstName = "小虎";
        p1.lastName = "王";
        p1.email = "XiaoHuWang@qq.com";
        Person p2 = new Person();
        p2.firstName = "呼哈";
        p2.lastName = "郑";
        p2.email = "HuHaZheng@qq.com";
        list.add(p1);
        list.add(p2);
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            Person p = list.get(i);
            JSONObject obj = new JSONObject();
            try {
                obj.put("firstName", p.firstName);
                obj.put("lastName", p.lastName);
                obj.put("email", p.email);
                array.put(obj);
                json.put("person", array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tv.setText(json.toString());
        }
    }

    /**
     * JsonReader解析JSON
     */
    private ArrayList<Person> jsonReader() {
        ArrayList<Person> list = new ArrayList<>();
        Reader r = new InputStreamReader(getResources().openRawResource(R.raw.person));
        JsonReader jr = new JsonReader(r);
        try {
            jr.beginObject();//开始解析对象
            if ("user".equals(jr.nextName())) {
                jr.beginArray();
                while (jr.hasNext()) {
                    Person p = new Person();
                    jr.beginObject();//开始解析对象
                    while (jr.hasNext()) {
                        String name = jr.nextName();
                        if ("firstName".equals(name)) {
                            p.firstName = jr.nextString();
                        } else if ("lastName".equals(name)) {
                            p.lastName = jr.nextString();
                        } else if ("email".equals(name)) {
                            p.email = jr.nextString();
                        }
                    }
                    jr.endObject();//结束对象的解析
                    list.add(p);
                }
            }
            jr.endArray();//结束数组的解析
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 使用GSON去解析JSON
     * @param view
     */
    public void gsonParseJsonClick(View view){
        Gson gson = new Gson();
        String s = null;
        Reader r= new InputStreamReader(getResources().openRawResource(R.raw.person2));
        Type type2 = new TypeToken<ArrayList<Person>>(){}.getType();
        ArrayList<Person> list = gson.fromJson(r,type2);
        for (int i = 0; i < list.size(); i++) {
            info = info + list.get(i);
        }
        tv.setText(info);
        info="";
    }

    /**
     * 使用GSON把JSON转换成GSON
     * @param view
     */
    public void jsonToGsonClick(View view){
        ArrayList<Person> list = new ArrayList<>();
        Person p1 = new Person();
        p1.firstName = "小虎";
        p1.lastName = "王";
        p1.email = "XiaoHuWang@qq.com";
        Person p2 = new Person();
        p2.firstName = "呼哈";
        p2.lastName = "郑";
        p2.email = "HuHaZheng@qq.com";
        list.add(p1);
        list.add(p2);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Person>>(){}.getType();
        String json = gson.toJson(list,type);
        tv.setText(json);
    }
}
