package com.jonny.myexamplepac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jonny.myexamplepac.view.CurveChartView;

import java.util.ArrayList;
import java.util.List;

public class DiyCurveGraphActivity extends AppCompatActivity {
    private com.jonny.myexamplepac.view.CurveChartView curve;
    private List<Integer> pointY1=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_curve_graph);
        init();
        pointY1= addData();
        curve.setPointsY(pointY1);
    }
    private void init(){
        curve= (CurveChartView) findViewById(R.id.curve);

    }

    /**
     * 添加数据
     * @return
     */
    private List<Integer> addData(){
        pointY1.add(80);
        pointY1.add(20);
        pointY1.add(50);
        pointY1.add(10);
        pointY1.add(60);
        pointY1.add(30);
        pointY1.add(70);
        return pointY1;
    }
}
