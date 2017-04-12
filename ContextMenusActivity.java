package com.jonny.myexamplepac;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ContextMenusActivity extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menus);
        tv= (TextView) findViewById(R.id.textView);
        //注册上下文菜单到tv组件上
        registerForContextMenu(tv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);

    }

    //上下文菜单的单击事件

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.red:
                tv.setBackgroundColor(Color.RED);
                break;
        case R.id.blue:
            tv.setBackgroundColor(Color.BLUE);
            break;
        case R.id.green:
            tv.setBackgroundColor(Color.GREEN);
            break;
        }
        return super.onContextItemSelected(item);
    }
}
