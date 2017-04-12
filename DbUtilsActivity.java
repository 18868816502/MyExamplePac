package com.jonny.myexamplepac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jonny.myexamplepac.bean.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.SqlInfo;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;

import org.w3c.dom.Text;

import java.util.List;

public class DbUtilsActivity extends AppCompatActivity {
    private EditText et_name, et_sex, et_age;
    private TextView tv;
    private String name, sex;
    private String age;
    private DbUtils dbUtils;
    private String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_utils);
        init();
    }

    public void init() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_sex = (EditText) findViewById(R.id.et_sex);
        et_age = (EditText) findViewById(R.id.et_age);
        tv = (TextView) findViewById(R.id.tv);
        dbUtils = DbUtils.create(this, "mydb");//传入上下文对象和自定义数据库的名称
    }

    /**
     * 添加数据
     *
     * @param view
     */
    public void saveClick(View view) {
        name = et_name.getText().toString();
        sex = et_sex.getText().toString();
        age = et_age.getText().toString();
        if (!name.equals("") && !sex.equals("") && !age.equals("")) {
            User user = new User(name, sex, age);
            try {
                dbUtils.save(user);
                Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show();
            } catch (DbException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "请完善你想要保存的值", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 查找所有数据
     *
     * @param view
     */
    public void findAllClick(View view) {
        info = "";
        try {
            List<User> list = dbUtils.findAll(User.class);
            for (User u : list) {
                info = info + u + "\n";
            }
            tv.setText(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void findByInfoClick(View view) {
        try {
            //按条件查询第一条数据
            User user = dbUtils.findFirst(Selector.from(User.class).where("name", "=", "小乖"));
            info = user.toString();
            //按条件查询所有数据
            List<User> list = dbUtils.findAll(Selector.from(User.class)
                    .where("id", ">", "2")
                    .and(WhereBuilder.b("age", ">", "18").or("age", "<", "80"))
                    .orderBy("id"));
            String info2 = "";
            for (User u : list) {
                info2 = info2 + u + "\n";
            }
//            String sql = "select name from mydb where sex = '男'";
//            SqlInfo sqlInfo = new SqlInfo(sql);
//            DbModel dbModel = (DbModel) dbUtils.findDbModelAll(Selector.from(User.class).groupBy("name"));
//            List<DbModel> dbMode2 = dbUtils.findDbModelAll(sqlInfo);
//            dbUtils.execNonQuery(sqlInfo);
            tv.setText(info2+info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateClick(View view) {
        User user = new User();
        user.setId(1);
        user.setName("哈哈1");
        user.setSex("女");
        user.setAge("50");
        try {
            dbUtils.update(user, "name", "sex");
            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    public void deleteClick(View view) {
        try {
            dbUtils.delete(User.class, WhereBuilder.b("name", "=", ""));
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
