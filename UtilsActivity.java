package com.jonny.myexamplepac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UtilsActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt_dbUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils);
        init();
    }

    public void init(){
        bt_dbUtils = (Button) findViewById(R.id.bt_dbUtils);
        bt_dbUtils.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_dbUtils:
                intent = new Intent(this, DbUtilsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
